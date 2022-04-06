package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.PlayerSelectorScreen;
import me.miquiis.onlyblock.common.capability.OnlyMoneyBlockCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;
import java.util.stream.Collectors;

public class OpenPlayerSelectPacket {

    public PacketToSend packetToSend;

    public enum PacketToSend {
        SABOTAGE,
        ROBBER,
        FREEZE,
        NUKE
    }

    public OpenPlayerSelectPacket(PacketToSend packetToSend)
    {
        this.packetToSend = packetToSend;
    }

    public static void encode(OpenPlayerSelectPacket msg, PacketBuffer buf) {
        buf.writeEnumValue(msg.packetToSend);
    }

    public static OpenPlayerSelectPacket decode(PacketBuffer buf) {
        return new OpenPlayerSelectPacket(buf.readEnumValue(PacketToSend.class));
    }

    public static void handle(final OpenPlayerSelectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft.getInstance().displayGuiScreen(new PlayerSelectorScreen(
                        Minecraft.getInstance().player.connection.getPlayerInfoMap().stream().map(player -> new PlayerSelectorScreen.PlayerSelectorInfo(player.getGameProfile().getId(), player.getGameProfile().getName(), player.getLocationSkin())).collect(Collectors.toList()),
                        msg.packetToSend == PacketToSend.SABOTAGE ? new BuySabotagePacket() : msg.packetToSend == PacketToSend.ROBBER ? new BuyStealPacket() : msg.packetToSend == PacketToSend.FREEZE ? new BuyFreezePacket() : msg.packetToSend == PacketToSend.NUKE ? new BuyDestroyPacket() : null
                ));
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
