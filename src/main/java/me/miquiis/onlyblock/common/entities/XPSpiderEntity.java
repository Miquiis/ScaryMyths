package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class XPSpiderEntity extends SpiderEntity implements IXPMob {
    public XPSpiderEntity(EntityType<? extends XPSpiderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public XPSpiderEntity(World worldIn) {
        super(EntityRegister.XP_SPIDER.get(), worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
