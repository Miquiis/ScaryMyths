package me.miquiis.onlyblock.common.classes;

import net.minecraft.nbt.CompoundNBT;

public class StockIsland implements IUnlockable {

    private boolean isLocked;

    public void deserializeNBT(CompoundNBT compoundNBT)
    {
        if (compoundNBT.contains("IsLocked"))
        {
            isLocked = compoundNBT.getBoolean("IsLocked");
        }
    }

    public CompoundNBT serializeNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putBoolean("IsLocked", isLocked);
        return compoundNBT;
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public void unlock() {
        isLocked = false;
    }

    @Override
    public void lock() {
        isLocked = true;
    }

}
