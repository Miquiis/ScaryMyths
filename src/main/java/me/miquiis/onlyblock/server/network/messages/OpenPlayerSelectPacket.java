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


    public static void encode(OpenPlayerSelectPacket msg, PacketBuffer buf) {
    }

    public static OpenPlayerSelectPacket decode(PacketBuffer buf) {
        return new OpenPlayerSelectPacket();
    }

    public static void handle(final OpenPlayerSelectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft.getInstance().displayGuiScreen(new PlayerSelectorScreen(
                        Minecraft.getInstance().player.connection.getPlayerInfoMap().stream().map(player -> new PlayerSelectorScreen.PlayerSelectorInfo(player.getGameProfile().getId(), player.getGameProfile().getName(), player.getLocationSkin())).collect(Collectors.toList()),
                        new BuySabotagePacket()
                ));
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
