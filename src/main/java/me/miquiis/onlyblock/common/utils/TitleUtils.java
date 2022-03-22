package me.miquiis.onlyblock.common.utils;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.util.text.StringTextComponent;
import org.stringtemplate.v4.ST;

public class TitleUtils {

    public static void sendTitleToPlayer(ServerPlayerEntity player, String title, String subtitle, int fadeIn, int stay, int fadeOut)
    {
        STitlePacket timePacket = new STitlePacket(STitlePacket.Type.TIMES, null, fadeIn, stay, fadeOut);
        STitlePacket titlePacket = new STitlePacket(STitlePacket.Type.TITLE, new StringTextComponent(title));
        STitlePacket subtitlePacket = new STitlePacket(STitlePacket.Type.SUBTITLE, new StringTextComponent(subtitle));
        player.connection.sendPacket(timePacket);
        player.connection.sendPacket(titlePacket);
        player.connection.sendPacket(subtitlePacket);
    }

}
