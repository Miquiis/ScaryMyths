package me.miquiis.onlyblock.common.capability.storages;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class OnlyMoneyBlockStorage implements Capability.IStorage<IOnlyMoneyBlock>{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IOnlyMoneyBlock> capability, IOnlyMoneyBlock instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IOnlyMoneyBlock> capability, IOnlyMoneyBlock instance, Direction side, INBT nbt) {
        if (!(instance instanceof OnlyMoneyBlock))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

        instance.deserializeNBT((CompoundNBT)nbt);
    }
}
