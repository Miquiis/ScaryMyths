package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.classes.ExpExplosion;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.TNTEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class XPTNTEntity extends Entity {

    private static final DataParameter<Integer> FUSE = EntityDataManager.createKey(TNTEntity.class, DataSerializers.VARINT);
    @Nullable
    private LivingEntity igniter;
    private int fuse = 80;

    private final Set<ExperienceOrbEntity> ORBS;

    public XPTNTEntity(EntityType<? extends XPTNTEntity> type, World worldIn) {
        super(type, worldIn);
        this.preventEntitySpawning = true;
        this.ORBS = new HashSet<>();
    }

    public XPTNTEntity(World worldIn, double x, double y, double z, @Nullable LivingEntity igniter) {
        this(EntityRegister.XP_TNT.get(), worldIn);
        this.setPosition(x, y, z);
        double d0 = worldIn.rand.nextDouble() * (double)((float)Math.PI * 2F);
        this.setMotion(-Math.sin(d0) * 0.02D, (double)0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
        this.igniter = igniter;
    }

    protected void registerData() {
        this.dataManager.register(FUSE, 80);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        }

        this.move(MoverType.SELF, this.getMotion());
        this.setMotion(this.getMotion().scale(0.98D));
        if (this.onGround) {
            this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
        }

        --this.fuse;
        if (this.fuse <= 0) {
            this.remove();
            this.explode();

            if (!world.isRemote)
            {
                ORBS.forEach(orb -> {
                    orb.setVelocity(MathUtils.getRandomMinMax(-0.2, 0.2), 0.5, MathUtils.getRandomMinMax(-0.2, 0.2));
                });
            }

            ORBS.clear();

        } else {
            this.func_233566_aG_();
            if (!this.world.isRemote) {
                ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, this.getPosX(), this.getPosY() + 1.1, this.getPosZ(), 2);
                experienceOrbEntity.setVelocity(0, 1.3, 0);
                world.addEntity(experienceOrbEntity);
                ORBS.add(experienceOrbEntity);
                //this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 0.5D, this.getPosZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected void explode() {
        float f = 4.0F;
        ExpExplosion expExplosion = new ExpExplosion(world, null, null, null, getPosX(), getPosY() + 0.5, getPosZ(), 5f, false, Explosion.Mode.DESTROY, true);
        expExplosion.doExplosionA();
        expExplosion.doExplosionB(true);

        if (!world.isRemote)
        {
            for (int i = 0; i < 5; i++)
            {
                MiniXPTNTEntity miniXPTNTEntity = new MiniXPTNTEntity(world, getPosX(), getPosY(), getPosZ(), null);
                miniXPTNTEntity.setFuse(60);
                miniXPTNTEntity.setVelocity(MathUtils.getRandomMinMax(-0.5, 0.5), MathUtils.getRandomMinMax(0.4, 0.8), MathUtils.getRandomMinMax(-0.5, 0.5));
                world.addEntity(miniXPTNTEntity);
            }
        }
    }

    protected void writeAdditional(CompoundNBT compound) {
        compound.putShort("Fuse", (short)this.getFuse());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readAdditional(CompoundNBT compound) {
        this.setFuse(compound.getShort("Fuse"));
    }

    /**
     * Returns null or the entityliving it was ignited by
     */
    @Nullable
    public LivingEntity getIgniter() {
        return this.igniter;
    }

    protected float getEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.15F;
    }

    public void setFuse(int fuseIn) {
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }

    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
