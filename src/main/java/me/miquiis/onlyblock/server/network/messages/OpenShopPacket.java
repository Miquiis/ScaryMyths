package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.HandlePacketUtils;
import me.miquiis.onlyblock.client.gui.PlayerSelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class OpenShopPacket {

    public static void encode(OpenShopPacket msg, PacketBuffer buf) {
    }

    public static OpenShopPacket decode(PacketBuffer buf) {
        return new OpenShopPacket();
    }

    public static void handle(final OpenShopPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                HandlePacketUtils.handleOpenShopPacket(msg, ctx);
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
