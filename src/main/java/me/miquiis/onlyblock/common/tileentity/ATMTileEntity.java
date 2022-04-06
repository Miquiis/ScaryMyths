package me.miquiis.onlyblock.common.tileentity;

import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.TileEntityRegister;
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

import java.util.UUID;

public class ATMTileEntity extends TileEntity implements ITickableTileEntity {

    private UUID owner;
    private int lastPrintTick;

    public ATMTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        lastPrintTick = 0;
    }

    public ATMTileEntity()
    {
        super(TileEntityRegister.ATM_TILE_ENTITY.get());
        lastPrintTick = 0;
    }

    @Override
    public void tick() {
        if (lastPrintTick >= 20)
        {
            if (!world.isRemote && world.getBlockState(pos.down()).getBlock() == BlockRegister.CASH_PILE.get())
            {
                OnlyMoneyBlock.getCapability(world.getPlayerByUuid(owner)).sumBankAccount(10);
            }
            lastPrintTick = 0;
        }

        lastPrintTick++;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public UUID getOwner() {
        return owner;
    }
}
