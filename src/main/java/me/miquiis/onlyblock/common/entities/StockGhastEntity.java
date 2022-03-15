package me.miquiis.onlyblock.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class StockGhastEntity extends GhastEntity {

    private Vector3d startPosition;

    public StockGhastEntity(EntityType<? extends GhastEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Nullable
    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        startPosition = getPositionVec();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(5, new RandomFlyGoal(this));
        this.goalSelector.addGoal(7, new LookAroundGoal(this));
        this.goalSelector.addGoal(7, new FireballAttackGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, (p_213812_1_) -> {
            return Math.abs(p_213812_1_.getPosY() - this.getPosY()) <= 4.0D;
        }));
    }

    public Vector3d getStartPosition() {
        return startPosition;
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putDouble("StartingPosX", this.startPosition.getX());
        compound.putDouble("StartingPosY", this.startPosition.getY());
        compound.putDouble("StartingPosZ", this.startPosition.getZ());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("StartingPosX")) {
            this.startPosition = new Vector3d(compound.getDouble("StartingPosX"), compound.getDouble("StartingPosY"), compound.getDouble("StartingPosZ"));
        }
    }

    static class FireballAttackGoal extends Goal {
        private final GhastEntity parentEntity;
        public int attackTimer;

        public FireballAttackGoal(GhastEntity ghast) {
            this.parentEntity = ghast;
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return this.parentEntity.getAttackTarget() != null;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.attackTimer = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.parentEntity.setAttacking(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            LivingEntity livingentity = this.parentEntity.getAttackTarget();
            double d0 = 64.0D;
            if (livingentity.getDistanceSq(this.parentEntity) < 4096.0D && this.parentEntity.canEntityBeSeen(livingentity)) {
                World world = this.parentEntity.world;
                ++this.attackTimer;
                if (this.attackTimer == 10 && !this.parentEntity.isSilent()) {
                    world.playEvent((PlayerEntity)null, 1015, this.parentEntity.getPosition(), 0);
                }

                if (this.attackTimer == 20) {
                    double d1 = 4.0D;
                    Vector3d vector3d = this.parentEntity.getLook(1.0F);
                    double d2 = livingentity.getPosX() - (this.parentEntity.getPosX() + vector3d.x * 4.0D);
                    double d3 = livingentity.getPosYHeight(0.5D) - (0.5D + this.parentEntity.getPosYHeight(0.5D));
                    double d4 = livingentity.getPosZ() - (this.parentEntity.getPosZ() + vector3d.z * 4.0D);
                    if (!this.parentEntity.isSilent()) {
                        world.playEvent((PlayerEntity)null, 1016, this.parentEntity.getPosition(), 0);
                    }

                    FireballEntity fireballentity = new FireballEntity(world, this.parentEntity, d2, d3, d4);
                    fireballentity.explosionPower = this.parentEntity.getFireballStrength();
                    fireballentity.setPosition(this.parentEntity.getPosX() + vector3d.x * 4.0D, this.parentEntity.getPosYHeight(0.5D) + 0.5D, fireballentity.getPosZ() + vector3d.z * 4.0D);
                    world.addEntity(fireballentity);
                    this.attackTimer = -40;
                }
            } else if (this.attackTimer > 0) {
                --this.attackTimer;
            }

            this.parentEntity.setAttacking(this.attackTimer > 10);
        }
    }

    static class LookAroundGoal extends Goal {
        private final GhastEntity parentEntity;

        public LookAroundGoal(GhastEntity ghast) {
            this.parentEntity = ghast;
            this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            if (this.parentEntity.getAttackTarget() == null) {
                Vector3d vector3d = this.parentEntity.getMotion();
                this.parentEntity.rotationYaw = -((float) MathHelper.atan2(vector3d.x, vector3d.z)) * (180F / (float)Math.PI);
                this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
            } else {
                LivingEntity livingentity = this.parentEntity.getAttackTarget();
                double d0 = 64.0D;
                if (livingentity.getDistanceSq(this.parentEntity) < 4096.0D) {
                    double d1 = livingentity.getPosX() - this.parentEntity.getPosX();
                    double d2 = livingentity.getPosZ() - this.parentEntity.getPosZ();
                    this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
                    this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
                }
            }

        }
    }

    static class MoveHelperController extends MovementController {
        private final GhastEntity parentEntity;
        private int courseChangeCooldown;

        public MoveHelperController(GhastEntity ghast) {
            super(ghast);
            this.parentEntity = ghast;
        }

        public void tick() {
            if (this.action == MovementController.Action.MOVE_TO) {
                if (this.courseChangeCooldown-- <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
                    Vector3d vector3d = new Vector3d(this.posX - this.parentEntity.getPosX(), this.posY - this.parentEntity.getPosY(), this.posZ - this.parentEntity.getPosZ());
                    double d0 = vector3d.length();
                    vector3d = vector3d.normalize();
                    if (this.func_220673_a(vector3d, MathHelper.ceil(d0))) {
                        this.parentEntity.setMotion(this.parentEntity.getMotion().add(vector3d.scale(0.1D)));
                    } else {
                        this.action = MovementController.Action.WAIT;
                    }
                }

            }
        }

        private boolean func_220673_a(Vector3d p_220673_1_, int p_220673_2_) {
            AxisAlignedBB axisalignedbb = this.parentEntity.getBoundingBox();

            for(int i = 1; i < p_220673_2_; ++i) {
                axisalignedbb = axisalignedbb.offset(p_220673_1_);
                if (!this.parentEntity.world.hasNoCollisions(this.parentEntity, axisalignedbb)) {
                    return false;
                }
            }

            return true;
        }
    }

    static class RandomFlyGoal extends Goal {
        private final StockGhastEntity parentEntity;
        private Vector3d startPos;

        public RandomFlyGoal(StockGhastEntity ghast) {
            this.parentEntity = ghast;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
            this.startPos = parentEntity.getStartPosition();
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            startPos = parentEntity.getStartPosition();
            MovementController movementcontroller = this.parentEntity.getMoveHelper();
            if (!movementcontroller.isUpdating()) {
                return true;
            }else if (startPos == null) {
                return false;
            } else {
                double d0 = movementcontroller.getX() - this.parentEntity.getPosX();
                double d1 = movementcontroller.getY() - this.parentEntity.getPosY();
                double d2 = movementcontroller.getZ() - this.parentEntity.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                return d3 < 1.0D || d3 > 3600.0D;
            }
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        public boolean shouldContinueExecuting() {
            return false;
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            Random random = this.parentEntity.getRNG();
            double d0 = this.startPos.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d1 = this.startPos.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double d2 = this.startPos.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        }
    }
}
