package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class SpaceshipEntity extends AnimalEntity implements IAnimatable {

    final AnimationFactory factory = new AnimationFactory(this);

    private boolean leftInputDown, rightInputDown, forwardInputDown, backInputDown;

    public SpaceshipEntity(EntityType<? extends AnimalEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.preventEntitySpawning = true;
        this.ignoreFrustumCheck = true;
        setInvulnerable(true);
    }

    public SpaceshipEntity(World worldIn) {
        super(EntityRegister.GOLDEN_HELI.get(), worldIn);
        this.preventEntitySpawning = true;
        this.ignoreFrustumCheck = true;
        setInvulnerable(true);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        Vector3d vector3d = (new Vector3d((double)5.5, -1.0D, 0D)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
        passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY() + 0.80f, this.getPosZ() + vector3d.z);
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isAlive()) {
            if (this.isBeingRidden()) {
                LivingEntity livingentity = (LivingEntity) this.getControllingPassenger();
                this.rotationYaw = livingentity.rotationYaw;
                this.prevRotationYaw = livingentity.rotationYaw;
                this.rotationPitch = livingentity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.renderYawOffset;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        //this.setRotation(ticksExisted, 0);
        this.fallDistance = 0;
        if (canPassengerSteer())
        {
            if (this.world.isRemote && this.getControllingPassenger() != null)
            {
                if (this.getControllingPassenger() instanceof LivingEntity)
                {
                    LivingEntity livingEntity = (LivingEntity) this.getControllingPassenger();
                    forwardInputDown = livingEntity.moveForward > 0;
                    backInputDown = livingEntity.moveForward < 0;
                    rightInputDown = livingEntity.moveStrafing > 0;
                    leftInputDown = livingEntity.moveStrafing < 0;

                    float rotation = 0;
                    if (leftInputDown) {
                        rotation -= 2.0;
                    } else if (rightInputDown) {
                        rotation += 2.0;
                    }

                    livingEntity.rotationYaw -= rotation;
                }

                Vector3d lookVec = this.getControllingPassenger().getLookVec().mul(0.1, 0.1, 0.1);
                Vector3d velocity = new Vector3d(0, 0, 0);

                if (forwardInputDown) {
                    velocity = new Vector3d(lookVec.getX(), lookVec.getY(), lookVec.getZ());
                } else if (backInputDown) {
                    velocity = new Vector3d( (0 - this.getMotion().getX()) * 0.05, (lookVec.getY() * 0.2) - 0.0125, (0 - this.getMotion().getZ()) * 0.05);
                }

                this.setMotion(this.getMotion().getX() + velocity.getX(), this.getMotion().getY() + velocity.getY(), this.getMotion().getZ() + velocity.getZ());
            }
            this.move(MoverType.SELF, this.getMotion());
        }
        if (getControllingPassenger() == null)
        {
            if (isOnGround())
            {
                this.setMotion(0, 0, 0);
            } else
            {
                this.setMotion(0, -1.0, 0);
            }
        }
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        if (!this.isBeingRidden()) {
            playerIn.startRiding(this);
            return super.getEntityInteractionResult(playerIn, hand);
        }
        return super.getEntityInteractionResult(playerIn, hand);
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean canBeSteered() {
        return true;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 10, this::predicate));
    }

    private PlayState predicate(AnimationEvent animationEvent) {
        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("fly"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }
}
