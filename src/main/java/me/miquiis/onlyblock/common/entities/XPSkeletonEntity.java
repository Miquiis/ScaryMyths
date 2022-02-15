package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class XPSkeletonEntity extends SkeletonEntity implements IXPMob {
    public XPSkeletonEntity(EntityType<? extends XPSkeletonEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public XPSkeletonEntity(World worldIn) {
        super(EntityRegister.XP_SKELETON.get(), worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
