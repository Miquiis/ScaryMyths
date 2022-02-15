package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.HandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class XPWarhammerProjectileEntity extends ThrowableEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    protected boolean isReturning;

    public XPWarhammerProjectileEntity(EntityType<? extends ThrowableEntity> p_i50161_1_, World p_i50161_2_) {
        super(p_i50161_1_, p_i50161_2_);
        this.isReturning = false;
    }

    public XPWarhammerProjectileEntity(World p_i50161_2_) {
        super(EntityRegister.XP_WARHAMMER_PROJECTILE.get(), p_i50161_2_);
        this.isReturning = false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("flying",true));
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {

        if (isReturning && getShooter() != null)
        {
            if (getDistance(getShooter()) <= 1)
            {
                PlayerEntity player = (PlayerEntity) getShooter();
                player.addItemStackToInventory(new ItemStack(ItemRegister.XP_WARHAMMER.get(), 1));
                this.remove();
            }
            this.setMotion(getShooter().getPositionVec().subtract(getPositionVec()).normalize().mul(2,2,2));
        } else
        {
            if (getShooter() != null && getDistance(getShooter()) > 64)
            {
                isReturning = true;
            }
        }

        super.tick();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        isReturning = true;
        super.onImpact(result);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<XPWarhammerProjectileEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected float getGravityVelocity() {
        return 0f;
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
