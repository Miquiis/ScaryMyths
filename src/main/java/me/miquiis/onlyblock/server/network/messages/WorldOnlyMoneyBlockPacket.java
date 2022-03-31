package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.WorldOnlyMoneyBlockCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class WorldOnlyMoneyBlockPacket {

    private final CompoundNBT nbt;

    public WorldOnlyMoneyBlockPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(WorldOnlyMoneyBlockPacket msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.nbt);
    }

    public static WorldOnlyMoneyBlockPacket decode(PacketBuffer buf) {
        return new WorldOnlyMoneyBlockPacket(buf.readCompoundTag());
    }

    public static void handle(final WorldOnlyMoneyBlockPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft mc = Minecraft.getInstance();
                mc.world.getCapability(WorldOnlyMoneyBlockCapability.CURRENT_CAPABILITY).ifPresent(cap -> cap.deserializeNBT(msg.nbt));
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
