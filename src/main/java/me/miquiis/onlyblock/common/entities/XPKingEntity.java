package me.miquiis.onlyblock.common.entities;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.w3c.dom.Attr;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

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
        setHammerInHands(true);
        setAnimationStage(AnimationStage.IDLE);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 100.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 10).createMutableAttribute(Attributes.FOLLOW_RANGE, 10).createMutableAttribute(Attributes.ARMOR, 5).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.2F);
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

class KingMeleeAttackGoal extends MeleeAttackGoal {

    private XPKingEntity kingEntity;
    private int attackDelay;
    private boolean attacking;

    public KingMeleeAttackGoal(XPKingEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        this.kingEntity = creature;
    }

    @Override
    public void startExecuting() {
        super.startExecuting();
        this.attackDelay = 40;
        this.attacking = false;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if ((attacking || distToEnemySqr <= d0) && this.getSwingCooldown() <= 0) {
            attack(enemy);
        }
    }

    private void attack(LivingEntity enemy)
    {
        if (!attacking)
        {
            attacking = true;
            this.kingEntity.attack();
        }

        if (attackDelay <= 0) {
            handleAttack(enemy);
            this.kingEntity.stopAttack();
        } else {
            attackDelay--;
        }
    }

    private void handleAttack(LivingEntity enemy)
    {
        this.resetSwingCooldown();
        this.attacker.attackEntityAsMob(enemy);
    }
}

class KingAttackGoal extends KingMeleeAttackGoal {
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
        this.sheathTicks = 0;
        king.sheathWeapon();
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