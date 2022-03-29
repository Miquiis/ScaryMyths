package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.WorldOnlyBlockCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class WorldOnlyBlockPacket {

    private final CompoundNBT nbt;

    public WorldOnlyBlockPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(WorldOnlyBlockPacket msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.nbt);
    }

    public static WorldOnlyBlockPacket decode(PacketBuffer buf) {
        return new WorldOnlyBlockPacket(buf.readCompoundTag());
    }

    public static void handle(final WorldOnlyBlockPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft mc = Minecraft.getInstance();
                mc.world.getCapability(WorldOnlyBlockCapability.CURRENT_CAPABILITY).ifPresent(cap -> cap.deserializeNBT(msg.nbt));
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
