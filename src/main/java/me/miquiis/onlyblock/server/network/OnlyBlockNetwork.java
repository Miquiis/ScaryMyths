package me.miquiis.onlyblock.server.network;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.server.network.messages.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class OnlyBlockNetwork {

    public static final String NETWORK_VERSION = "1.0.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(OnlyBlock.MOD_ID, "network"), () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION)
    );

    public static void init() {
        CHANNEL.registerMessage(1, WorldOnlyMoneyBlockPacket.class, WorldOnlyMoneyBlockPacket::encode, WorldOnlyMoneyBlockPacket::decode, WorldOnlyMoneyBlockPacket::handle);
        CHANNEL.registerMessage(2, OnlyMoneyBlockPacket.class, OnlyMoneyBlockPacket::encode, OnlyMoneyBlockPacket::decode, OnlyMoneyBlockPacket::handle);
        CHANNEL.registerMessage(3, ATMPacket.class, ATMPacket::encode, ATMPacket::decode, ATMPacket::handle);
        CHANNEL.registerMessage(4, BuyItemFromShopPacket.class, BuyItemFromShopPacket::encode, BuyItemFromShopPacket::decode, BuyItemFromShopPacket::handle);
        CHANNEL.registerMessage(5, BuySabotagePacket.class, BuySabotagePacket::encode, BuySabotagePacket::decode, BuySabotagePacket::handle);
        CHANNEL.registerMessage(6, OpenPlayerSelectPacket.class, OpenPlayerSelectPacket::encode, OpenPlayerSelectPacket::decode, OpenPlayerSelectPacket::handle);
    }

}
