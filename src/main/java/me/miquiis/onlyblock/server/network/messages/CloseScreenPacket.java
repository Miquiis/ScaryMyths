package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.LaptopScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CloseScreenPacket {

    public static void encode(CloseScreenPacket msg, PacketBuffer buf) {
    }

    public static CloseScreenPacket decode(PacketBuffer buf) {
        return new CloseScreenPacket();
    }

    public static void handle(final CloseScreenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft.getInstance().displayGuiScreen(null);
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
