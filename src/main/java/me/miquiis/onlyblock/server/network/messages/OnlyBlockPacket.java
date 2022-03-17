package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import net.minecraft.client.Minecraft;
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
                Minecraft mc = Minecraft.getInstance();
                OnlyBlockModel.getCapability(mc.player).deserializeNBT(msg.nbt);
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
