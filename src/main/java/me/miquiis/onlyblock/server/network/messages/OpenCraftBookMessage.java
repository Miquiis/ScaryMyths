package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.AshBookScreen;
import me.miquiis.onlyblock.client.gui.CraftingRecipeBookScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenCraftBookMessage {

    public OpenCraftBookMessage() {
    }

    public static void encode(OpenCraftBookMessage message, PacketBuffer buffer)
    {

    }

    public static OpenCraftBookMessage decode(PacketBuffer buffer)
    {
        return new OpenCraftBookMessage();
    }

    public static void handle(OpenCraftBookMessage message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> OpenCraftBookMessage.handlePacket(message, contextSupplier));
        });
        context.setPacketHandled(true);
    }

    public static void handlePacket(OpenCraftBookMessage msg, Supplier<NetworkEvent.Context> ctx) {
        Minecraft.getInstance().displayGuiScreen(new CraftingRecipeBookScreen());
    }


}
