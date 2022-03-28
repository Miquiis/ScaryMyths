package me.miquiis.onlyblock.client.gui;

import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.server.network.messages.OnlyBlockPacket;
import me.miquiis.onlyblock.server.network.messages.OpenShopPacket;
import me.miquiis.onlyblock.server.network.messages.SendMessageChatPackage;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class HandlePacketUtils {

    public static void handleOnlyBlockPacket(OnlyBlockPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        Minecraft mc = Minecraft.getInstance();
        OnlyBlockModel.getCapability(mc.player).deserializeNBT(msg.getNbt());
    }

    public static void handleOpenShopPacket(OpenShopPacket msg, Supplier<NetworkEvent.Context> ctx){
        Minecraft.getInstance().displayGuiScreen(new PlayerSelectorScreen(getOnlinePlayers(), new SendMessageChatPackage()));
    }

    private static List<PlayerSelectorScreen.PlayerSelectorInfo> getOnlinePlayers()
    {
        List<PlayerSelectorScreen.PlayerSelectorInfo> onlinePlayerInfo = new ArrayList<>();
        Minecraft.getInstance().player.connection.getPlayerInfoMap().forEach(networkPlayerInfo -> {
            onlinePlayerInfo.add(new PlayerSelectorScreen.PlayerSelectorInfo(networkPlayerInfo.getGameProfile().getId(), networkPlayerInfo.getGameProfile().getName(), networkPlayerInfo.getLocationSkin()));
        });
        return onlinePlayerInfo;
    }

}
