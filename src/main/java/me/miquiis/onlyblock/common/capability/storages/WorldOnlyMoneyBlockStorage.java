package me.miquiis.onlyblock.common.capability.storages;

import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyMoneyBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class WorldOnlyMoneyBlockStorage implements Capability.IStorage<IWorldOnlyMoneyBlock>{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IWorldOnlyMoneyBlock> capability, IWorldOnlyMoneyBlock instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IWorldOnlyMoneyBlock> capability, IWorldOnlyMoneyBlock instance, Direction side, INBT nbt) {
        if (!(instance instanceof WorldOnlyMoneyBlock))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

        instance.deserializeNBT((CompoundNBT)nbt);
    }
}
