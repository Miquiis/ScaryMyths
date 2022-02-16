package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.classes.ExpExplosion;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
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

public class XPSwordProjectileEntity extends ProjectileItemEntity {

    protected boolean isReturning;

    public XPSwordProjectileEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
        this.isReturning = false;
    }

    public XPSwordProjectileEntity(EntityType<? extends ProjectileItemEntity> type, double x, double y, double z, World worldIn) {
        super(type, x, y, z, worldIn);
        this.isReturning = false;
    }

    public XPSwordProjectileEntity(EntityType<? extends ProjectileItemEntity> type, LivingEntity livingEntityIn, World worldIn) {
        super(type, livingEntityIn, worldIn);
        this.isReturning = false;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.PACKED_ICE;
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
            if (entityRayTraceResult.getEntity() instanceof XPSwordProjectileEntity) return;
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
        if (result.getEntity() instanceof XPSwordProjectileEntity) return;
        if (result.getEntity() instanceof PlayerEntity) return;

        ExpExplosion expExplosion = new ExpExplosion(world, null, null ,null, getPosX(), getPosY(), getPosZ(), 5f, false, Explosion.Mode.DESTROY, true);
        expExplosion.doExplosionA();
        expExplosion.doExplosionB(true);

        super.onEntityHit(result);
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
