package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.PlayerSelectorScreen;

public interface IPlayerSelectablePacket {

    void setPlayerInfo(PlayerSelectorScreen.PlayerSelectorInfo playerInfo);

}
