package me.miquiis.onlyblock.common.capability;

import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.storages.OnlyMoneyBlockStorage;
import me.miquiis.onlyblock.common.capability.storages.WorldOnlyMoneyBlockStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WorldOnlyMoneyBlockCapability implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(IWorldOnlyMoneyBlock.class)
    public static final Capability<IWorldOnlyMoneyBlock> CURRENT_CAPABILITY = null;
    private LazyOptional<IWorldOnlyMoneyBlock> instance = LazyOptional.of(CURRENT_CAPABILITY::getDefaultInstance);

    public WorldOnlyMoneyBlockCapability(World world)
    {
        instance.ifPresent(iOnlyBlock -> iOnlyBlock.setInstance(world));
        if (world instanceof ServerWorld)
        instance.ifPresent(iOnlyBlock -> iOnlyBlock.setServerWorld((ServerWorld) world));
    }

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IWorldOnlyMoneyBlock.class, new WorldOnlyMoneyBlockStorage(), WorldOnlyMoneyBlock::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CURRENT_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) CURRENT_CAPABILITY.getStorage().writeNBT(CURRENT_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CURRENT_CAPABILITY.getStorage().readNBT(CURRENT_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}
