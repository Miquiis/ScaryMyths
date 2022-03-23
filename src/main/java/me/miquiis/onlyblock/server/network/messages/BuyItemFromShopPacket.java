package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.registries.SoundRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BuyItemFromShopPacket {

    private final ItemStack itemStack;

    public BuyItemFromShopPacket(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public static void encode(BuyItemFromShopPacket msg, PacketBuffer buf) {
        buf.writeItemStack(msg.itemStack);
    }

    public static BuyItemFromShopPacket decode(PacketBuffer buf) {
        return new BuyItemFromShopPacket(buf.readItemStack());
    }

    public static void handle(final BuyItemFromShopPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            player.inventory.addItemStackToInventory(msg.itemStack);
            player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null).addOrSubtractAmount(-1000000);
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundRegister.KATCHING.get(), SoundCategory.PLAYERS, 0.5f, 1f);
        });
        ctx.get().setPacketHandled(true);
    }

}
