package me.miquiis.onlyblock.server.network;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.server.network.messages.OpenAshBookMessage;
import me.miquiis.onlyblock.server.network.messages.OpenCobblestoneBookMessage;
import me.miquiis.onlyblock.server.network.messages.OpenFrostBookMessage;
import me.miquiis.onlyblock.server.network.messages.OpenLavaBookMessage;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class OnlyBlockNetwork {

    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(OnlyBlock.MOD_ID, "network"), () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION)
    );

    public static void init() {
        CHANNEL.registerMessage(0, OpenLavaBookMessage.class, OpenLavaBookMessage::encode, OpenLavaBookMessage::decode, OpenLavaBookMessage::handle);
        CHANNEL.registerMessage(1, OpenCobblestoneBookMessage.class, OpenCobblestoneBookMessage::encode, OpenCobblestoneBookMessage::decode, OpenCobblestoneBookMessage::handle);
        CHANNEL.registerMessage(2, OpenAshBookMessage.class, OpenAshBookMessage::encode, OpenAshBookMessage::decode, OpenAshBookMessage::handle);
        CHANNEL.registerMessage(3, OpenFrostBookMessage.class, OpenFrostBookMessage::encode, OpenFrostBookMessage::decode, OpenFrostBookMessage::handle);
    }

}
