package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BadStockEntity extends ProjectileItemEntity {
    public BadStockEntity(EntityType<? extends ProjectileItemEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public BadStockEntity(World world)
    {
        super(EntityRegister.BAD_STOCK.get(), world);
    }

    @Override
    public boolean hitByEntity(Entity entityIn) {
        System.out.println("Hit by entity");
        return super.hitByEntity(entityIn);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            this.markVelocityChanged();
            Entity entity = source.getTrueSource();
            if (entity != null) {
                Vector3d vector3d = entity.getLookVec();
                this.setMotion(vector3d.mul(2, 2, 2));
                this.setShooter(entity);
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public float getBrightness() {
        return 1f;
    }

    @Override
    protected float getGravityVelocity() {
        return 0f;
    }

    @Override
    protected Item getDefaultItem() {
        return Items.COAL_BLOCK;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
