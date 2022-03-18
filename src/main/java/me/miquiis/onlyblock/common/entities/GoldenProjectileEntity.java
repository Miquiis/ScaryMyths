package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.entities.renderer.GoldenProjectileRenderer;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GoldenProjectileEntity extends ThrowableEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public GoldenProjectileEntity(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public GoldenProjectileEntity(World world)
    {
        super(EntityRegister.GOLDEN_PROJECTILE.get(), world);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote)
        {
            this.remove();
            world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 5f, Explosion.Mode.DESTROY);
        }
        super.onImpact(result);
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
    public void tick() {
        if (ticksExisted >= 30)
        {
            remove();
        }
        super.tick();
////        this.setRenderYawOffset(100);
//        this.rotationYaw = 100;
//        this.prevRotationYaw = 100;
    }

    @Override
    protected void registerData() {

    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
