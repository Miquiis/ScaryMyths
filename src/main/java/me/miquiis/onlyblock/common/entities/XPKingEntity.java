package me.miquiis.onlyblock.common.entities;

import me.miquiis.custombar.common.BarInfo;
import me.miquiis.custombar.common.BarManager;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FallingBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import org.w3c.dom.Attr;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.UUID;

public class XPKingEntity extends MonsterEntity implements IAnimatable {

    public enum AnimationStage {
        IDLE,
        SHEATHING,
        ATTACKING
    }

    private AnimationFactory factory = new AnimationFactory(this);
    private final NonNullList<ItemStack> inventoryArmor = NonNullList.withSize(4, ItemStack.EMPTY);
    private AnimationStage animationStage;
    private boolean hasWarhammerInHands;

    private static final DataParameter<Boolean> HAS_HAMMER = EntityDataManager.createKey(XPKingEntity.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> STAGE = EntityDataManager.createKey(XPKingEntity.class, DataSerializers.VARINT);

    public XPKingEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.ignoreFrustumCheck = true;
        this.animationStage = AnimationStage.IDLE;
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        UUID bossBar = BarManager.addBar(new BarInfo(UUID.randomUUID(), new StringTextComponent("\u00a72\u00a7lXP King"), 1f, new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/xp_bar.png"), new int[]{255,255,255}, false));
        BarManager.updateBar(bossBar, this.getUniqueID(), 1f, true);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(HAS_HAMMER, false);
        this.getDataManager().register(STAGE, 0);
    }

    public boolean hasHammerInHands()
    {
        return this.getDataManager().get(HAS_HAMMER);
    }

    public AnimationStage getAnimationStage()
    {
        return AnimationStage.values()[this.getDataManager().get(STAGE)];
    }

    public void setAnimationStage(AnimationStage stage)
    {
        this.getDataManager().set(STAGE, stage.ordinal());
    }

    public void setHammerInHands(boolean bool)
    {
        this.getDataManager().set(HAS_HAMMER, bool);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new KingAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        super.registerGoals();
    }

    public void createHammerExplosion()
    {
        final Vector3d forwardVector = this.getForward();
        final AxisAlignedBB rangeBox = this.getBoundingBox().contract(0, -4,0).offset(0, -5, 0).offset(forwardVector.mul(3, 0, 3)).grow(2, 2, 2);
        BlockPos.getAllInBox(rangeBox).forEach(blockPos -> {
            BlockState currentState = world.getBlockState(blockPos);
            if (currentState.getBlock() != Blocks.AIR)
            {
                if (MathUtils.chance(30)) return;
                world.setBlockState(blockPos, BlockRegister.XP_BLOCK.get().getDefaultState(), 2);
                CustomFallingBlockEntity xpFalling = new CustomFallingBlockEntity(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), BlockRegister.XP_BLOCK.get().getDefaultState());
                xpFalling.fallTime = 1;
                xpFalling.setVelocity(0, MathUtils.getRandomMinMax(0.2, 0.4), 0);
                world.addEntity(xpFalling);
            }
        });
        this.world.getEntitiesInAABBexcluding(this, rangeBox, null).forEach(entity -> {
            if (entity instanceof LivingEntity)
            {
                this.attackEntityAsMob(entity);
            }
        });
    }

    public void sheathWeapon()
    {
        setAnimationStage(AnimationStage.SHEATHING);
    }

    public void attack()
    {
        setAnimationStage(AnimationStage.ATTACKING);
    }

    public void stopAttack()
    {
        setAnimationStage(AnimationStage.IDLE);
    }

    public void finishSheath()
    {
        if (hasHammerInHands()) return;
        setHammerInHands(true);
        setAnimationStage(AnimationStage.IDLE);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 100.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 10).createMutableAttribute(Attributes.FOLLOW_RANGE, 35).createMutableAttribute(Attributes.ARMOR, 5).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.2F);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return this.inventoryArmor;
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    public HandSide getPrimaryHand() {
        return HandSide.RIGHT;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        final AnimationStage currentAnimation = getAnimationStage();
        final boolean hasHammer = hasHammerInHands();

        if (!hasHammer && currentAnimation == AnimationStage.SHEATHING)
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("unsheathe",false));
            return PlayState.CONTINUE;
        }

        if (currentAnimation == AnimationStage.ATTACKING)
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack",false));
            return PlayState.CONTINUE;
        }

        if (event.isMoving())
        {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk",true));
        }
        else
        {
            if (hasHammer)
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("unsheathed.idle",true));
            } else
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle",true));
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<XPKingEntity>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}

class CustomMeleeAttackGoal extends Goal {
    protected final XPKingEntity attacker;
    private final double speedTowardsTarget;
    private final boolean longMemory;
    private Path path;
    private double targetX;
    private double targetY;
    private double targetZ;
    private int delayCounter;
    private int swingCooldown;
    private final int attackInterval = 20;
    private long lastCheckTime;
    private int failedPathFindingPenalty = 0;
    private boolean canPenalize = false;
    private boolean attacking = false;
    private boolean hasDamaged = false;
    private int attackingCounter;
    private int damageCounter;

    public CustomMeleeAttackGoal(XPKingEntity creature, double speedIn, boolean useLongMemory) {
        this.attacker = creature;
        this.speedTowardsTarget = speedIn;
        this.longMemory = useLongMemory;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean shouldExecute() {
        long i = this.attacker.world.getGameTime();
        if (i - this.lastCheckTime < 20L) {
            return false;
        } else {
            this.lastCheckTime = i;
            LivingEntity livingentity = this.attacker.getAttackTarget();
            if (livingentity == null) {
                return false;
            } else if (!livingentity.isAlive()) {
                return false;
            } else {
                if (canPenalize) {
                    if (--this.delayCounter <= 0) {
                        this.path = this.attacker.getNavigator().pathfind(livingentity, 0);
                        this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
                        return this.path != null;
                    } else {
                        return true;
                    }
                }
                this.path = this.attacker.getNavigator().pathfind(livingentity, 0);
                if (this.path != null) {
                    return true;
                } else {
                    return this.getAttackReachSqr(livingentity) >= this.attacker.getDistanceSq(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ());
                }
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting() {
        LivingEntity livingentity = this.attacker.getAttackTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else if (attacking) {
            return true;
        } else if (!this.longMemory) {
            return !this.attacker.getNavigator().noPath();
        }  else if (!this.attacker.isWithinHomeDistanceFromPosition(livingentity.getPosition())) {
            return false;
        } else {
            return !(livingentity instanceof PlayerEntity) || !livingentity.isSpectator() && !((PlayerEntity)livingentity).isCreative();
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.path, this.speedTowardsTarget);
        this.attacker.setAggroed(true);
        this.delayCounter = 0;
        this.swingCooldown = 0;
        this.attacking = false;
        this.hasDamaged = false;
        resetAttackingCounter();
        resetDamageCounter();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        LivingEntity livingentity = this.attacker.getAttackTarget();
        if (!EntityPredicates.CAN_AI_TARGET.test(livingentity)) {
            this.attacker.setAttackTarget((LivingEntity)null);
        }

        this.attacker.setAggroed(false);
        this.attacker.getNavigator().clearPath();
        this.attacker.stopAttack();
        this.attacking = false;
        this.hasDamaged = false;
        resetAttackingCounter();
        resetDamageCounter();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        LivingEntity livingentity = this.attacker.getAttackTarget();
        double d0 = this.attacker.getDistanceSq(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ());
        this.attacker.getLookController().setLookPositionWithEntity(livingentity, 30.0F, 30.0F);
        if (!attacking)
        {
            this.delayCounter = Math.max(this.delayCounter - 1, 0);
            if ((this.longMemory || this.attacker.getEntitySenses().canSee(livingentity)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || livingentity.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
                this.targetX = livingentity.getPosX();
                this.targetY = livingentity.getPosY();
                this.targetZ = livingentity.getPosZ();
                this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
                if (this.canPenalize) {
                    this.delayCounter += failedPathFindingPenalty;
                    if (this.attacker.getNavigator().getPath() != null) {
                        net.minecraft.pathfinding.PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
                        if (finalPathPoint != null && livingentity.getDistanceSq(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
                            failedPathFindingPenalty = 0;
                        else
                            failedPathFindingPenalty += 10;
                    } else {
                        failedPathFindingPenalty += 10;
                    }
                }
                if (d0 > 1024.0D) {
                    this.delayCounter += 10;
                } else if (d0 > 256.0D) {
                    this.delayCounter += 5;
                }

                if (!this.attacker.getNavigator().tryMoveToEntityLiving(livingentity, this.speedTowardsTarget)) {
                    this.delayCounter += 15;
                }
            }

        }
        this.swingCooldown = Math.max(this.swingCooldown - 1, 0);
        this.checkAndPerformAttack(livingentity, d0);
    }

    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (attacking)
        {
            if (!hasDamaged && damageCounter <= 0)
            {
                this.attacker.createHammerExplosion();
                hasDamaged = true;
            }

            if (attackingCounter <= 0)
            {
                attacking = false;
                resetAttackingCounter();
                resetDamageCounter();
                this.resetSwingCooldown();
                this.attacker.stopAttack();
            } else
            {
                this.attackingCounter = Math.max(this.attackingCounter - 1, 0);
                this.damageCounter = Math.max(this.damageCounter - 1, 0);
            }
        } else
        {
            if (distToEnemySqr <= d0 && this.swingCooldown <= 0) {
                startAttacking();
            }
        }
    }

    protected void startAttacking()
    {
        attacking = true;
        attacker.attack();
    }

    protected void resetAttackingCounter() { this.attackingCounter = 45; }

    protected void resetDamageCounter() { this.damageCounter = 30; }

    protected void resetSwingCooldown() {
        this.swingCooldown = 100;
    }

    protected boolean isSwingOnCooldown() {
        return this.swingCooldown <= 0;
    }

    protected int getSwingCooldown() {
        return this.swingCooldown;
    }

    protected int func_234042_k_() {
        return 20;
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return (double)(this.attacker.getWidth() * 5.0F * this.attacker.getWidth() * 5.0F + attackTarget.getWidth());
    }
}

class KingAttackGoal extends CustomMeleeAttackGoal {
    private final XPKingEntity king;
    private int sheathTicks;

    public KingAttackGoal(XPKingEntity kingIn, double speedIn, boolean longMemoryIn) {
        super(kingIn, speedIn, longMemoryIn);
        this.king = kingIn;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting() {
        super.startExecuting();
        if (!king.hasHammerInHands()){
            this.sheathTicks = 0;
            king.sheathWeapon();
        }
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask() {
        super.resetTask();
        this.king.setAggroed(false);
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        super.tick();
        ++this.sheathTicks;
        if (this.sheathTicks >= 40) {
            this.king.finishSheath();
            this.king.setAggroed(true);
        } else {
            this.king.setAggroed(false);
        }
    }
}