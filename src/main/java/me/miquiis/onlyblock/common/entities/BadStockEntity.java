package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.quests.StockQuestOne;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.common.registries.SoundRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.EntityRayTraceResult;
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
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        result.getEntity().attackEntityFrom(DamageSource.GENERIC, 1f);
        if (!world.isRemote && result.getEntity() instanceof PlayerEntity)
        {
            ServerPlayerEntity player = (ServerPlayerEntity) result.getEntity();
            IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(player);
            if (!onlyBlock.getStockIsland().isFirstHit())
            {
                ICurrency currency = player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
                currency.addOrSubtractAmount(-500);
                player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundRegister.KATCHING.get(), SoundCategory.PLAYERS, 0.5f, 0.5f);
                return;
            }
            onlyBlock.setCurrentQuest(new StockQuestOne(player));
            onlyBlock.getStockIsland().hit();
        }
        remove();
    }

    @Override
    public void tick() {
        super.tick();
        if (ticksExisted >= 100)
        {
            remove();
        }
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
        return ItemRegister.BAD_STOCK.get();
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
