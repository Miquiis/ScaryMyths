package me.miquiis.onlyblock.common.classes;

import me.miquiis.onlyblock.common.utils.WorldEditUtils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public class BillionaireIsland implements IUnlockable {

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
    public void unlock(World world) {
        isLocked = false;
        WorldEditUtils.pasteSchematic("rocket_no_entity", world, 1.51, 65.00, 130.47);
    }

    @Override
    public void lock(World world) {
        isLocked = true;
        WorldEditUtils.pasteSchematic("b_rocket_no_entity", world, 1.51, 65.00, 130.47);
    }

}
