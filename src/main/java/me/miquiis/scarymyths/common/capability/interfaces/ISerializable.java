package me.miquiis.scarymyths.common.capability.interfaces;

import net.minecraft.nbt.CompoundNBT;

public interface ISerializable {

    void deserializeNBT(CompoundNBT data);
    CompoundNBT serializeNBT();
}
