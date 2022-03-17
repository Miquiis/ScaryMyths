package me.miquiis.onlyblock.common.capability.storages;

import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.Currency;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class OnlyBlockStorage implements Capability.IStorage<IOnlyBlock>{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IOnlyBlock> capability, IOnlyBlock instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IOnlyBlock> capability, IOnlyBlock instance, Direction side, INBT nbt) {
        if (!(instance instanceof OnlyBlockModel))
            throw  new IllegalArgumentException("Can not deserialize to an intance that isn't the default implementation");

        instance.deserializeNBT((CompoundNBT)nbt);
    }
}
