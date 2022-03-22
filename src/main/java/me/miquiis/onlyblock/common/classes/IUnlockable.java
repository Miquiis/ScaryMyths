package me.miquiis.onlyblock.common.classes;

import net.minecraft.world.World;

public interface IUnlockable {

    boolean isLocked();
    void unlock(World world);
    void lock(World world);

}
