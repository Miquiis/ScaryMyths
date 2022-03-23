package me.miquiis.onlyblock.common.classes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface IUnlockable {

    boolean isLocked();
    void unlock(PlayerEntity player);
    void lock(PlayerEntity player);

}
