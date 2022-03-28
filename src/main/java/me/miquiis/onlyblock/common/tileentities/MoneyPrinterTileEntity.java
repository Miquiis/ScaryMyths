package me.miquiis.onlyblock.common.tileentities;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.registries.TileEntityRegister;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.UUID;

public class MoneyPrinterTileEntity extends TileEntity implements IAnimatable, ITickableTileEntity {

    private final AnimationFactory factory = new AnimationFactory(this);

    private WeakReference<PlayerEntity> playerOwner;
    private UUID owner;
    private int lastPrintTick;

    public MoneyPrinterTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        lastPrintTick = 0;
    }

    public MoneyPrinterTileEntity()
    {
        super(TileEntityRegister.MONEY_PRINTER_TILE_ENTITY.get());
        lastPrintTick = 0;
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
        return super.write(compound);
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        if (nbt.contains("Owner")) this.owner = nbt.getUniqueId("Owner");
    }

    @Override
    public void tick() {
        if (lastPrintTick >= 20 * 60)
        {
            lastPrintTick = 0;
            if (!world.isRemote && getPlayerOwner() != null)
            {
                ICurrency currency = getPlayerOwner().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
                currency.addOrSubtractAmount(500);
            }
        }
        lastPrintTick++;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("print", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
