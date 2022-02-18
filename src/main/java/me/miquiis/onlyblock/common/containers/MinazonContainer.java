package me.miquiis.onlyblock.common.containers;

import me.miquiis.onlyblock.common.registries.ContainerRegister;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class MinazonContainer extends Container {

    private final LivingEntity jeffBezos;
    private final PlayerEntity playerEntity;
    private final IItemHandler playerInventory;

    public MinazonContainer(int windowId, PlayerEntity player, PlayerInventory playerInventory) {
        super(ContainerRegister.MINAZON_CONTAINER.get(), windowId);
        this.jeffBezos = null;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
    }

    public MinazonContainer(int windowId, LivingEntity jeffBezos, PlayerEntity player, PlayerInventory playerInventory) {
        super(ContainerRegister.MINAZON_CONTAINER.get(), windowId);
        this.jeffBezos = jeffBezos;
        this.playerEntity = player;
        this.playerInventory = new InvWrapper(playerInventory);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return jeffBezos.getDistanceSq(playerIn) < 64;
    }
}
