package me.miquiis.onlyblock.common.entities;

import com.google.common.graph.Network;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractFireballEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class StockEntity extends AbstractFireballEntity {

    public int explosionPower = 1;

    public StockEntity(EntityType<? extends StockEntity> p_i50163_1_, World p_i50163_2_) {
        super(p_i50163_1_, p_i50163_2_);
    }

    public StockEntity(World worldIn) {
        super(EntityRegister.STOCK_ENTITY.get(), worldIn);
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this.getShooter());
            this.world.createExplosion((Entity)null, this.getPosX(), this.getPosY(), this.getPosZ(), (float)this.explosionPower, flag, flag ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
            this.remove();
        }

    }

    /**
     * Called when the arrow hits an entity
     */
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        if (!this.world.isRemote) {
            Entity entity = result.getEntity();
            Entity entity1 = this.getShooter();
            //entity.attackEntityFrom(DamageSource.causeOnFireDamage(this, entity1), 6.0F);
            if (entity1 instanceof LivingEntity) {
                this.applyEnchantments((LivingEntity)entity1, entity);
            }

        }
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("ExplosionPower", this.explosionPower);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        if (compound.contains("ExplosionPower", 99)) {
            this.explosionPower = compound.getInt("ExplosionPower");
        }

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
