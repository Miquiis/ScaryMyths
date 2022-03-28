package me.miquiis.onlyblock.common.tileentities;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.TileEntityRegister;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class MoneyVacuumTileEntity extends TileEntity implements ITickableTileEntity {

    private WeakReference<PlayerEntity> playerOwner;
    private UUID owner;
    private int lastVacuumTick;

    public MoneyVacuumTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        lastVacuumTick = 0;
    }

    public MoneyVacuumTileEntity() {
        super(TileEntityRegister.MONEY_VACUUM.get());
        lastVacuumTick = 0;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public PlayerEntity getPlayerOwner() {
        if (playerOwner == null)
        {
            playerOwner = new WeakReference<>(world.getPlayerByUuid(owner));
        } else
        {
            return playerOwner.get();
        }
        return getPlayerOwner();
    }

    private boolean hasValidBlock()
    {
        BlockState blockState = world.getBlockState(getPos().down());
        return blockState.getBlock() == BlockRegister.CASH_PILE.get();
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = new CompoundNBT();
        write(compoundNBT);
        return compoundNBT;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 42, getUpdateTag());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (owner != null) compound.putUniqueId("Owner", owner);
        compound.putInt("LastVacuumTick", lastVacuumTick);
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("Owner")) this.owner = nbt.getUniqueId("Owner");
        if (nbt.contains("LastVacuumTick")) this.lastVacuumTick = nbt.getInt("LastVacuumTick");
    }

    @Override
    public void tick() {
        if (hasValidBlock())
        {
            lastVacuumTick++;
            if (lastVacuumTick > 20)
            {
                lastVacuumTick = 0;
                if (!world.isRemote && getPlayerOwner() != null)
                {
                    ICurrency currency = getPlayerOwner().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
                    currency.addOrSubtractAmount(1000);
                }
            }
        }
    }
}
