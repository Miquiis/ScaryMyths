package me.miquiis.onlyblock.common.capability.storages;

import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.capability.models.Currency;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class CurrencyStorage implements Capability.IStorage<ICurrency>{
    @Nullable
    @Override
    public INBT writeNBT(Capability<ICurrency> capability, ICurrency instance, Direction side) {
        return IntNBT.valueOf(instance.getAmount());
    }

    @Override
    public void readNBT(Capability<ICurrency> capability, ICurrency instance, Direction side, INBT nbt) {
        if (!(instance instanceof Currency))
            throw  new IllegalArgumentException("Can not deserialize to an intance that isn't the default implementation");

        instance.setAmount(((IntNBT)nbt).getInt(), false);
    }
}
