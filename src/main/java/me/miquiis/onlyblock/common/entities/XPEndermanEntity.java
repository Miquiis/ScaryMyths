package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class XPEndermanEntity extends EndermanEntity implements IXPMob {
    public XPEndermanEntity(EntityType<? extends XPEndermanEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public XPEndermanEntity(World worldIn) {
        super(EntityRegister.XP_ENDERMAN.get(), worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
