package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.LavaBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenLavaBookMessage {

    public OpenLavaBookMessage() {
    }

    public static void encode(OpenLavaBookMessage message, PacketBuffer buffer)
    {

    }

    public static OpenLavaBookMessage decode(PacketBuffer buffer)
    {
        return new OpenLavaBookMessage();
    }

    public static void handle(OpenLavaBookMessage message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OpenLavaBookMessage.handlePacket(message, contextSupplier));
        });
        context.setPacketHandled(true);
    }

    public static void handlePacket(OpenLavaBookMessage msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().displayGuiScreen(new LavaBookScreen());
    }


}
