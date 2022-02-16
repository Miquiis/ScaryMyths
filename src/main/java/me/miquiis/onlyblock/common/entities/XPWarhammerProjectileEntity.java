package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.classes.ExpExplosion;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ExperienceOrbEntity;
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
import net.minecraft.world.Explosion;
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

        if (!isReturning && result.getType() == RayTraceResult.Type.BLOCK)
        {
            createExplosion();
        }

        if (!isReturning && result.getType() == RayTraceResult.Type.ENTITY)
        {
            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult)result;
            if (entityRayTraceResult.getEntity() instanceof XPWarhammerProjectileEntity) return;
            if (entityRayTraceResult.getEntity() instanceof PlayerEntity) return;

            createExplosion();
        }

        isReturning = true;
        super.onImpact(result);
    }

    private void createExplosion()
    {
        ExpExplosion expExplosion = new ExpExplosion(world, null, null ,null, getPosX(), getPosY(), getPosZ(), 3f, false, Explosion.Mode.DESTROY, true);
        expExplosion.doExplosionA();
        expExplosion.doExplosionB(true);

        for (int i = 0; i < 25; i++)
        {
            if (!world.isRemote)
            {
                ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, getPosX(), getPosY(), getPosZ(), 5);
                experienceOrbEntity.setVelocity(MathUtils.getRandomMinMax(-0.2, 0.2), MathUtils.getRandomMinMax(0.5, 0.8), MathUtils.getRandomMinMax(-0.2, 0.2));
                world.addEntity(experienceOrbEntity);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        if (result.getEntity() instanceof XPWarhammerProjectileEntity) return;
        if (result.getEntity() instanceof PlayerEntity) return;

        ExpExplosion expExplosion = new ExpExplosion(world, null, null ,null, getPosX(), getPosY(), getPosZ(), 5f, false, Explosion.Mode.DESTROY, true);
        expExplosion.doExplosionA();
        expExplosion.doExplosionB(true);

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
