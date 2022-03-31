package me.miquiis.onlyblock.common.capability;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.storages.OnlyMoneyBlockStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OnlyMoneyBlockCapability implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(IOnlyMoneyBlock.class)
    public static final Capability<IOnlyMoneyBlock> CURRENT_CAPABILITY = null;
    private LazyOptional<IOnlyMoneyBlock> instance = LazyOptional.of(CURRENT_CAPABILITY::getDefaultInstance);

    public OnlyMoneyBlockCapability(PlayerEntity playerEntity)
    {
        if (playerEntity instanceof ServerPlayerEntity)
        instance.ifPresent(iOnlyBlock -> iOnlyBlock.setServerPlayer((ServerPlayerEntity) playerEntity));
    }

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IOnlyMoneyBlock.class, new OnlyMoneyBlockStorage(), OnlyMoneyBlock::new);
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
