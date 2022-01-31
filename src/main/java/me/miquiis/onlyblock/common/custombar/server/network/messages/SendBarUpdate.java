package me.miquiis.onlyblock.common.custombar.server.network.messages;

import me.miquiis.onlyblock.common.custombar.common.BarInfo;
import me.miquiis.onlyblock.common.custombar.common.BarManager;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SendBarUpdate {

    private BarInfo barInfo;
    private BarUpdate barUpdate;

    public SendBarUpdate(@Nullable BarInfo barInfo, BarUpdate barUpdate) {
        this.barInfo = barInfo;
        this.barUpdate = barUpdate;
    }

    public static void encode(SendBarUpdate message, PacketBuffer buffer)
    {
        buffer.writeEnumValue(message.barUpdate);
        if (message.barUpdate == BarUpdate.UPDATE) return;
        buffer.writeUniqueId(message.barInfo.getUniqueID());
        buffer.writeTextComponent(message.barInfo.getText());
        buffer.writeFloat(message.barInfo.getPercent());
    }

    public static SendBarUpdate decode(PacketBuffer buffer)
    {
        BarUpdate barUpdate = buffer.readEnumValue(BarUpdate.class);
        BarInfo barInfo = new BarInfo(buffer.readUniqueId(), buffer.readTextComponent(), buffer.readFloat());
        return new SendBarUpdate(barInfo, barUpdate);
    }

    public static void handle(SendBarUpdate message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> BarManager.handlePacket(message, contextSupplier));
        });
        context.setPacketHandled(true);
    }

    public enum BarUpdate {
        ADD,
        REMOVE,
        UPDATE,
        CLEAR,
    }

    public BarInfo getBarInfo() {
        return barInfo;
    }

    public BarUpdate getBarUpdate() {
        return barUpdate;
    }
}
