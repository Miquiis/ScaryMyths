package me.miquiis.scarymyths.common.utils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.text.StringTextComponent;

public class TitleUtils {

    public static void sendTitleToPlayer(ServerPlayerEntity player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        if (player == null) return;
        if (player.connection == null) return;
        STitlePacket timePacket = new STitlePacket(STitlePacket.Type.TIMES, null, fadeIn, stay, fadeOut);
        STitlePacket titlePacket = new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent(title.replace("&", "\u00A7")));
        STitlePacket subtitlePacket = new STitlePacket(STitlePacket.Type.SUBTITLE, new StringTextComponent(subtitle.replace("&", "\u00A7")));
        player.connection.sendPacket(timePacket);
        player.connection.sendPacket(titlePacket);
        player.connection.sendPacket(subtitlePacket);
    }

}
