package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.UUID;

public class AsteroidEntity extends MobEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private double radius;
    private double startingAngle;
    private float startingPitch;
    private Vector3d gravCenter;
    private Vector3d randomGravCenter;
    private boolean isProjectile;
    private UUID owner;

    private ServerPlayerEntity nearPlayer;

    public AsteroidEntity(EntityType<? extends MobEntity> type, World worldIn) {
        super(type, worldIn);
        this.startingAngle = MathUtils.getRandomMax(360);
        this.startingPitch = MathUtils.getRandomMax(360);
        this.gravCenter = new Vector3d(0, 100, 0);
        this.radius = 10;
        this.noClip = true;
        this.isProjectile = false;
        this.ignoreFrustumCheck = true;
        enablePersistence();
        setRandomGravCenter();
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);

        compound.putDouble("GravX", gravCenter.getX());
        compound.putDouble("GravY", gravCenter.getY());
        compound.putDouble("GravZ", gravCenter.getZ());

        compound.putDouble("Radius", radius);
        compound.putDouble("StartAngle", startingAngle);
        compound.putDouble("StartPitch", startingPitch);
        compound.putBoolean("IsProjectile", isProjectile);
        compound.putUniqueId("Owner", owner);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);

        if (compound.contains("GravX") && compound.contains("GravY") && compound.contains("GravZ"))
        {
            gravCenter = new Vector3d(compound.getDouble("GravX"), compound.getDouble("GravY"), compound.getDouble("GravZ"));
            setRandomGravCenter();
        }

        if (compound.contains("Radius"))
        {
            radius = compound.getDouble("Radius");
        }

        if (compound.contains("StartAngle"))
        {
            startingAngle = compound.getDouble("StartAngle");
        }

        if (compound.contains("StartPitch"))
        {
            startingPitch = compound.getFloat("StartPitch");
        }

        if (compound.contains("IsProjectile"))
        {
            isProjectile = compound.getBoolean("IsProjectile");
        }

        if (compound.contains("Owner"))
        {
            owner = compound.getUniqueId("Owner");
        }
    }

    public PlayerEntity getOwner()
    {
        return owner == null ? null : this.world.getPlayerByUuid(owner);
    }

    public void setOwner(UUID owner) { this.owner = owner; }

    public AsteroidEntity(World worldIn, double startingAngle, float startPitch, double radius, Vector3d gravCenter) {
        super(EntityRegister.ASTEROID.get(), worldIn);
        this.startingAngle = startingAngle;
        this.startingPitch = startPitch;
        this.gravCenter = gravCenter;
        this.radius = radius;
        this.isProjectile = false;
        this.noClip = true;
        enablePersistence();
        setRandomGravCenter();
        this.ignoreFrustumCheck = true;

    }

    public AsteroidEntity(World worldIn, ServerPlayerEntity player) {
        super(EntityRegister.ASTEROID.get(), worldIn);
        this.isProjectile = true;
        this.nearPlayer = player;
        this.noClip = true;
        enablePersistence();
        this.ignoreFrustumCheck = true;

    }

    public AsteroidEntity(World worldIn, Vector3d goal) {
        super(EntityRegister.ASTEROID.get(), worldIn);
        this.isProjectile = true;
        this.noClip = true;
        this.gravCenter = goal;
        enablePersistence();
        this.ignoreFrustumCheck = true;
    }

    private void setRandomGravCenter()
    {
        randomGravCenter = new Vector3d(gravCenter.getX() + MathUtils.getRandomMinMax(-10.0, 10.0), gravCenter.getY() + MathUtils.getRandomMinMax(-10.0, 10.0), gravCenter.getZ() + MathUtils.getRandomMinMax(-10.0, 10.0));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(ForgeMod.ENTITY_GRAVITY.get(), 0f);
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
        remove();
        world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 3, false, Explosion.Mode.DESTROY);
        super.collideWithEntity(entityIn);
    }

    @Override
    public void tick() {

        if (ticksExisted % 5 == 0 && world.isRemote) {
            final double wideAngle = 360;
            final double gap = 25;

            for (double i = -wideAngle/2; i <= wideAngle/2; i+= gap)
            {
                final double angle = i + (ticksExisted % 100);
                Vector3d lookVec = getVectorForRotation(0, 0, (float)angle);
                double x = 50 * lookVec.getX();
                double z = 50 * lookVec.getZ();
                double y = 50 * lookVec.getY();

                Vector3d vec = new Vector3d(getPosX() + x, (getPosY() + 2f) + y, getPosZ() + z);
                world.addParticle(ParticleTypes.FLAME,true, vec.getX(), vec.getY(), vec.getZ(), 0, 0, 0);
                world.addParticle(ParticleTypes.LARGE_SMOKE,true, vec.getX(), vec.getY(), vec.getZ(), 0, 0, 0);
            }
        }

        if (isProjectile)
        {
            if (gravCenter.distanceTo(getPositionVec()) < 32f)
            {
                world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 3f, Explosion.Mode.NONE);
                remove();
                if (!world.isRemote && getOwner() != null)
                {
                    IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(getOwner());
                    onlyBlock.getBillionaireIsland().damageEarth();
                    onlyBlock.sync((ServerPlayerEntity)getOwner());
                }
            }
            this.setMotion(gravCenter.subtract(getPositionVec()).normalize().mul(0.8, 0.8, 0.8));
        }

        if (!world.isRemote && !isProjectile)
        {
            //attackNearbyPlayer();

            final double angle = (startingAngle + this.world.getGameTime()) % 360;
            float pitch;
            if (startingPitch < 0)
            {
                if (startingPitch < -35)
                {
                    pitch = (float) (startingPitch - this.world.getGameTime() - 45) % 360;
                } else
                {
                    pitch = (float) (startingPitch - this.world.getGameTime()) % 360;
                }
            } else {
                if (startingPitch > 35)
                {
                    pitch = (float) (startingPitch - this.world.getGameTime() + 45) % 360;
                } else
                {
                    pitch = (float) (startingPitch + this.world.getGameTime()) % 360;
                }
            }
            Vector3d lookVec = getVectorForRotation(pitch, 0, (float)angle);
            double x = radius * lookVec.getX();
            double y = radius * lookVec.getY();
            double z = radius * lookVec.getZ();

            final Vector3d gravitationalCenter = new Vector3d(randomGravCenter.getX() + x,randomGravCenter.getY() + y, randomGravCenter.getZ() + z);

            if (gravitationalCenter.distanceTo(getPositionVec()) > 10)
            {
                setPosition(gravitationalCenter.getX(), gravitationalCenter.getY(), gravitationalCenter.getZ());
            }

            Vector3d direction = gravitationalCenter.subtract(getPositionVec());

            this.setMotion(direction);
        }

        super.tick();
    }

    private void attackNearbyPlayer()
    {
        if (ticksExisted % 20 != 0) return;

        if (nearPlayer != null)
        {
            if (nearPlayer.getPositionVec().distanceTo(getPositionVec()) > 50)
            {
                nearPlayer = null;
            }
        }

        if (nearPlayer == null)
        {
            world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(50, 50, 50), entity -> entity instanceof PlayerEntity).forEach(entity -> {
                ServerPlayerEntity player = (ServerPlayerEntity)entity;
                if (player.isSpectator() || player.isCreative()) return;
                nearPlayer = (ServerPlayerEntity)entity;
            });
        }

        if (nearPlayer == null) return;

        if (MathUtils.chance(1))
        {
            remove();
            AsteroidEntity asteroidEntity = new AsteroidEntity(world, nearPlayer);
            asteroidEntity.setPosition(getPosX(), getPosY(), getPosZ());
            world.addEntity(asteroidEntity);
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<AsteroidEntity>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationEvent<AsteroidEntity> animationEvent) {
        return PlayState.STOP;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    private final Vector3d getVectorForRotation(float pitch, float yaw, float angle) {
        float f = (pitch) * ((float)Math.PI / 180F);
        float f1 = (-yaw + angle) * ((float)Math.PI / 180F);
        float f2 = MathHelper.cos(f1);
        float f3 = MathHelper.sin(f1);
        float f4 = MathHelper.cos(f);
        float f5 = MathHelper.sin(f);
        return new Vector3d((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }
}
