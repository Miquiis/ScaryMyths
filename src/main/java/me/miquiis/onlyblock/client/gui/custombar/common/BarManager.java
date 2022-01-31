package me.miquiis.onlyblock.client.gui.custombar.common;

import com.google.common.collect.Maps;
import me.miquiis.onlyblock.client.gui.custombar.common.BarInfo;
import me.miquiis.onlyblock.client.gui.custombar.server.network.BarNetwork;
import me.miquiis.onlyblock.client.gui.custombar.server.network.messages.SendBarUpdate;
import me.miquiis.onlyblock.server.network.messages.OpenLavaBookMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class BarManager {

    private static final Map<UUID, BarInfo> currentActiveBars = Maps.newLinkedHashMap();

    public static void init()
    {
        BarNetwork.init();
    }

    public static UUID addBar(BarInfo barInfo)
    {
        return addBar(barInfo.getUniqueID(), barInfo.getText(), barInfo.getPercent());
    }

    public static UUID addBar(ITextComponent text)
    {
        return addBar(text, 1f);
    }

    public static UUID addBar(ITextComponent text, float percentage)
    {
        return addBar(UUID.randomUUID(), text, percentage);
    }

    public static UUID addBar(UUID id, ITextComponent text, float percentage)
    {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            BarInfo barInfo = new BarInfo(id, text, percentage);
            currentActiveBars.put(id, barInfo);
            BarNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new SendBarUpdate(barInfo, SendBarUpdate.BarUpdate.ADD));
        });

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            BarInfo barInfo = new BarInfo(id, text, percentage);
            currentActiveBars.put(id, barInfo);
        });

        return id;
    }

    public static void removeBar(String name)
    {
        BarInfo barInfo = getBarInfo(name);
        if (barInfo == null) return;
        removeBar(barInfo.getUniqueID());
    }

    public static void removeBar(UUID uuid)
    {
        BarInfo barInfo = currentActiveBars.remove(uuid);
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            BarNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new SendBarUpdate(barInfo, SendBarUpdate.BarUpdate.REMOVE));
        });
    }

    public static void clearBars()
    {
        currentActiveBars.clear();
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            BarNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new SendBarUpdate(null, SendBarUpdate.BarUpdate.CLEAR));
        });
    }

    public static void updateBar(String name, float percent)
    {
        BarInfo barInfo = getBarInfo(name);
        if (barInfo == null) return;
        updateBar(barInfo.getUniqueID(), percent);
    }

    public static void updateBar(UUID barId, float percent)
    {
        BarInfo barInfo = currentActiveBars.get(barId);
        barInfo.setPercent(percent);
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            BarNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new SendBarUpdate(barInfo, SendBarUpdate.BarUpdate.UPDATE));
        });
    }

    public static void handlePacket(SendBarUpdate message, Supplier<NetworkEvent.Context> contextSupplier)
    {
        switch (message.getBarUpdate())
        {
            case ADD:
            {
                addBar(message.getBarInfo());
                break;
            }
            case CLEAR:
            {
                clearBars();
                break;
            }
            case REMOVE:
            {
                removeBar(message.getBarInfo().getUniqueID());
                break;
            }
            case UPDATE:
            {
                updateBar(message.getBarInfo().getUniqueID(), message.getBarInfo().getPercent());
                break;
            }
        }
    }

    public static BarInfo getBarInfo(String name)
    {
       return currentActiveBars.values().stream().filter(b -> b.getText().getString().equals(name)).findFirst().orElse(null);
    }

    public static Map<UUID, BarInfo> getCurrentActiveBars() {
        return currentActiveBars;
    }
}
