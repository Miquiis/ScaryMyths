package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.CobblestoneBookScreen;
import me.miquiis.onlyblock.client.gui.FrostBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenCobblestoneBookMessage {

    public OpenCobblestoneBookMessage() {
    }

    public static void encode(OpenCobblestoneBookMessage message, PacketBuffer buffer)
    {

    }

    public static OpenCobblestoneBookMessage decode(PacketBuffer buffer)
    {
        return new OpenCobblestoneBookMessage();
    }

    public static void handle(OpenCobblestoneBookMessage message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OpenCobblestoneBookMessage.handlePacket(message, contextSupplier));
        });
        context.setPacketHandled(true);
    }

    public static void handlePacket(OpenCobblestoneBookMessage msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().displayGuiScreen(new CobblestoneBookScreen());
    }


}
