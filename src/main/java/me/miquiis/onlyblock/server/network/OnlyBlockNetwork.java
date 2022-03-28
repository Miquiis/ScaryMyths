package me.miquiis.onlyblock.server.network;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.models.Currency;
import me.miquiis.onlyblock.server.network.messages.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class OnlyBlockNetwork {

    public static final String NETWORK_VERSION = "2.2.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(OnlyBlock.MOD_ID, "network"), () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION)
    );

    public static void init() {
        CHANNEL.registerMessage(0, CurrencyPacket.class, CurrencyPacket::encode, CurrencyPacket::decode, CurrencyPacket::handle);
        CHANNEL.registerMessage(1, BuyItemFromShopPacket.class, BuyItemFromShopPacket::encode, BuyItemFromShopPacket::decode, BuyItemFromShopPacket::handle);
        CHANNEL.registerMessage(2, OpenShopPacket.class, OpenShopPacket::encode, OpenShopPacket::decode, OpenShopPacket::handle);
        CHANNEL.registerMessage(3, OnlyBlockPacket.class, OnlyBlockPacket::encode, OnlyBlockPacket::decode, OnlyBlockPacket::handle);
        CHANNEL.registerMessage(4, CloseScreenPacket.class, CloseScreenPacket::encode, CloseScreenPacket::decode, CloseScreenPacket::handle);
        CHANNEL.registerMessage(5, ShootMissilePacket.class, ShootMissilePacket::encode, ShootMissilePacket::decode, ShootMissilePacket::handle);
        CHANNEL.registerMessage(6, OpenAmazonPackage.class, OpenAmazonPackage::encode, OpenAmazonPackage::decode, OpenAmazonPackage::handle);
        CHANNEL.registerMessage(7, WorldOnlyBlockPacket.class, WorldOnlyBlockPacket::encode, WorldOnlyBlockPacket::decode, WorldOnlyBlockPacket::handle);
        CHANNEL.registerMessage(8, SendMessageChatPackage.class, SendMessageChatPackage::encode, SendMessageChatPackage::decode, SendMessageChatPackage::handle);
    }

}
