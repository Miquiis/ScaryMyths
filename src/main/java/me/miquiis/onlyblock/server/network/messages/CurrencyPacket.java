package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.models.Currency;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CurrencyPacket {

    private final CompoundNBT nbt;

    public CurrencyPacket(CompoundNBT nbt) {
        this.nbt = nbt;
    }

    public static void encode(CurrencyPacket msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.nbt);
    }

    public static CurrencyPacket decode(PacketBuffer buf) {
        return new CurrencyPacket(buf.readCompoundTag());
    }

    public static void handle(final CurrencyPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
                Minecraft mc = Minecraft.getInstance();
                mc.player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(cap -> cap.deserializeNBT(msg.nbt));
            });
        });
        ctx.get().setPacketHandled(true);
    }

}
