package me.miquiis.onlyblock.common.capability;

import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.Currency;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.capability.storages.CurrencyStorage;
import me.miquiis.onlyblock.common.capability.storages.OnlyBlockStorage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class OnlyBlockCapability implements ICapabilitySerializable<CompoundNBT> {

    @CapabilityInject(IOnlyBlock.class)
    public static final Capability<IOnlyBlock> CURRENCY_CAPABILITY = null;
    private LazyOptional<IOnlyBlock> instance = LazyOptional.of(CURRENCY_CAPABILITY::getDefaultInstance);

    public OnlyBlockCapability(ServerPlayerEntity player)
    {
        instance.ifPresent(iOnlyBlock -> iOnlyBlock.setPlayer(player));
    }

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IOnlyBlock.class, new OnlyBlockStorage(), OnlyBlockModel::new);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CURRENCY_CAPABILITY.orEmpty(cap, instance);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) CURRENCY_CAPABILITY.getStorage().writeNBT(CURRENCY_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty")), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CURRENCY_CAPABILITY.getStorage().readNBT(CURRENCY_CAPABILITY, instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional cannot be empty!")), null, nbt);
    }
}
