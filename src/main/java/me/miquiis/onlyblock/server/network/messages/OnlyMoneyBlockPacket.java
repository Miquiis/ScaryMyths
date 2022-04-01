package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.OnlyMoneyBlockCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OnlyMoneyBlockPacket {

    private final CompoundNBT nbt;

    public OnlyMoneyBlockPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(OnlyMoneyBlockPacket msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.nbt);
    }

    public static OnlyMoneyBlockPacket decode(PacketBuffer buf) {
        return new OnlyMoneyBlockPacket(buf.readCompoundTag());
    }

    public static void handle(final OnlyMoneyBlockPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft mc = Minecraft.getInstance();
                mc.world.getEntityByID(msg.nbt.getInt("ClientID")).getCapability(OnlyMoneyBlockCapability.CURRENT_CAPABILITY).ifPresent(cap -> cap.deserializeNBT(msg.nbt));
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
