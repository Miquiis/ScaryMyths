package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.classes.ExpExplosion;
import me.miquiis.onlyblock.common.entities.renderer.GoldenProjectileRenderer;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public class GoldenProjectileEntity extends ThrowableEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public GoldenProjectileEntity(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public GoldenProjectileEntity(World world)
    {
        super(EntityRegister.GOLDEN_PROJECTILE.get(), world);
    }

//    @Override
//    protected void onImpact(RayTraceResult result) {
//        if (result.getType() == RayTraceResult.Type.ENTITY)
//        {
//            EntityRayTraceResult entityRayTraceResult = (EntityRayTraceResult) result;
//            if (entityRayTraceResult.getEntity() instanceof SpaceshipEntity)
//            {
//                return;
//            }
//        }
//
//        if (!world.isRemote)
//        {
//            if (getShooter() != null)
//            {
//                ServerPlayerEntity player = (ServerPlayerEntity) getShooter();
//                player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null).addOrSubtractAmount(5000);
//            }
//        }
//
//        ExpExplosion expExplosion = new ExpExplosion(world, null, null, null, getPosX(), getPosY(), getPosZ(), 10f, false, Explosion.Mode.DESTROY, true);
//        expExplosion.doExplosionA();
//        expExplosion.doExplosionB(true);
//        this.remove();
//        super.onImpact(result);
//    }


    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        if (!(result.getEntity() instanceof SpaceshipEntity))
        {
            if (result.getEntity() instanceof AsteroidEntity)
            {
                if (!world.isRemote)
                {
                    if (getShooter() != null)
                    {
                        ServerPlayerEntity player = (ServerPlayerEntity) getShooter();
                        player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null).addOrSubtractAmount(5000000);
                    }
                }
                result.getEntity().remove();
            }
            result.getEntity().attackEntityFrom(DamageSource.GENERIC, 9999f);
            this.remove();
        }
        super.onEntityHit(result);
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult result) {
        ExpExplosion expExplosion = new ExpExplosion(world, null, null, null, getPosX(), getPosY(), getPosZ(), 10f, false, Explosion.Mode.DESTROY, true);
        expExplosion.doExplosionA();
        expExplosion.doExplosionB(true);

        if (!world.isRemote)
        {
            if (getShooter() != null)
            {
                ServerPlayerEntity player = (ServerPlayerEntity) getShooter();
                player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null).addOrSubtractAmount(5000);
            }
        }

        this.remove();
        super.func_230299_a_(result);
    }

    @Override
    protected float getGravityVelocity() {
        return 0f;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerData() {

    }

    @Override
    public void tick() {
        if (ticksExisted >= 150)
        {
            remove();
        }

        super.tick();
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
