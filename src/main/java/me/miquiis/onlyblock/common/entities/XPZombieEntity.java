package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class XPZombieEntity extends ZombieEntity implements IXPMob {
    public XPZombieEntity(EntityType<? extends XPZombieEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public XPZombieEntity(World worldIn) {
        super(EntityRegister.XP_ZOMBIE.get(), worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
