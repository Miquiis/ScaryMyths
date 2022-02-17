package me.miquiis.onlyblock.common.capability.interfaces;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface ICurrency {

    public int getAmount();
    public void setAmount(int amount, boolean sync);
    public void addOrSubtractAmount(int amount);
    public void sync(ServerPlayerEntity player);
    public CompoundNBT serializeNBT();
    public void deserializeNBT(CompoundNBT data);
    public void setPlayer(ServerPlayerEntity player);

}
