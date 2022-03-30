package me.miquiis.onlyblock.client.gui;

import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import java.util.List;

public class HandlePacketUtils {

    private static List<PlayerSelectorScreen.PlayerSelectorInfo> getOnlinePlayers()
    {
        List<PlayerSelectorScreen.PlayerSelectorInfo> onlinePlayerInfo = new ArrayList<>();
        Minecraft.getInstance().player.connection.getPlayerInfoMap().forEach(networkPlayerInfo -> {
            onlinePlayerInfo.add(new PlayerSelectorScreen.PlayerSelectorInfo(networkPlayerInfo.getGameProfile().getId(), networkPlayerInfo.getGameProfile().getName(), networkPlayerInfo.getLocationSkin()));
        });
        return onlinePlayerInfo;
    }

}
