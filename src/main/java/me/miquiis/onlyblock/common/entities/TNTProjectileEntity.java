package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TNTProjectileEntity extends ThrowableEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public TNTProjectileEntity(EntityType<? extends ThrowableEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public TNTProjectileEntity(World world)
    {
        super(EntityRegister.TNT_PROJECTILE.get(), world);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 3, Explosion.Mode.NONE);
        this.remove();
        super.onEntityHit(result);
    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult result) {
        world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 10, Explosion.Mode.NONE);
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
