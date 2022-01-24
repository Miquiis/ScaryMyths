package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.AshBookScreen;
import me.miquiis.onlyblock.client.gui.CobblestoneBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenAshBookMessage {

    public OpenAshBookMessage() {
    }

    public static void encode(OpenAshBookMessage message, PacketBuffer buffer)
    {

    }

    public static OpenAshBookMessage decode(PacketBuffer buffer)
    {
        return new OpenAshBookMessage();
    }

    public static void handle(OpenAshBookMessage message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OpenAshBookMessage.handlePacket(message, contextSupplier));
        });
        context.setPacketHandled(true);
    }

    public static void handlePacket(OpenAshBookMessage msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().displayGuiScreen(new AshBookScreen());
    }


}
