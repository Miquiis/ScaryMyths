package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BuyItemFromShopPacket {

    private final CompoundNBT nbt;

    public BuyItemFromShopPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(BuyItemFromShopPacket msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.nbt);
    }

    public static BuyItemFromShopPacket decode(PacketBuffer buf) {
        return new BuyItemFromShopPacket(buf.readCompoundTag());
    }

    public static void handle(final BuyItemFromShopPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
    }

}
