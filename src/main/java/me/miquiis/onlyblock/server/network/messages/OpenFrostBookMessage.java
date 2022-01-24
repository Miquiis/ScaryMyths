package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.FrostBookScreen;
import me.miquiis.onlyblock.client.gui.LavaBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenFrostBookMessage {

    public OpenFrostBookMessage() {
    }

    public static void encode(OpenFrostBookMessage message, PacketBuffer buffer)
    {

    }

    public static OpenFrostBookMessage decode(PacketBuffer buffer)
    {
        return new OpenFrostBookMessage();
    }

    public static void handle(OpenFrostBookMessage message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OpenFrostBookMessage.handlePacket(message, contextSupplier));
        });
        context.setPacketHandled(true);
    }

    public static void handlePacket(OpenFrostBookMessage msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().displayGuiScreen(new FrostBookScreen());
    }


}
