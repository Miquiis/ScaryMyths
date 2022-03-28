package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.HandlePacketUtils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OnlyBlockPacket {

    private final CompoundNBT nbt;

    public OnlyBlockPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(OnlyBlockPacket msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.nbt);
    }

    public static OnlyBlockPacket decode(PacketBuffer buf) {
        return new OnlyBlockPacket(buf.readCompoundTag());
    }

    public static void handle(final OnlyBlockPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                HandlePacketUtils.handleOnlyBlockPacket(msg, ctx);
            });
        });
        ctx.get().setPacketHandled(true);
    }

    public CompoundNBT getNbt() {
        return nbt;
    }
}
