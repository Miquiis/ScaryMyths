package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.MinazonScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

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
                Minecraft.getInstance().displayGuiScreen(new MinazonScreen());
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
