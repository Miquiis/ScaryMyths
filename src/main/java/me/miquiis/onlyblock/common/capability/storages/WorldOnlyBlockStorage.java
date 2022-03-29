package me.miquiis.onlyblock.common.capability.storages;

import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class WorldOnlyBlockStorage implements Capability.IStorage<IWorldOnlyBlock>{
    @Nullable
    @Override
    public INBT writeNBT(Capability<IWorldOnlyBlock> capability, IWorldOnlyBlock instance, Direction side) {
        return instance.serializeNBT();
    }

    @Override
    public void readNBT(Capability<IWorldOnlyBlock> capability, IWorldOnlyBlock instance, Direction side, INBT nbt) {
        if (!(instance instanceof WorldOnlyBlock))
            throw  new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");

        instance.deserializeNBT((CompoundNBT)nbt);
    }
}
