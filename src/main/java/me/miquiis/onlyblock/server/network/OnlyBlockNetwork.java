package me.miquiis.onlyblock.server.network;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.models.Currency;
import me.miquiis.onlyblock.server.network.messages.*;
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
        CHANNEL.registerMessage(0, CurrencyPacket.class, CurrencyPacket::encode, CurrencyPacket::decode, CurrencyPacket::handle);
        CHANNEL.registerMessage(1, BuyItemFromShopPacket.class, BuyItemFromShopPacket::encode, BuyItemFromShopPacket::decode, BuyItemFromShopPacket::handle);
        CHANNEL.registerMessage(2, OpenShopPacket.class, OpenShopPacket::encode, OpenShopPacket::decode, OpenShopPacket::handle);
        CHANNEL.registerMessage(3, OnlyBlockPacket.class, OnlyBlockPacket::encode, OnlyBlockPacket::decode, OnlyBlockPacket::handle);
        CHANNEL.registerMessage(4, CloseScreenPacket.class, CloseScreenPacket::encode, CloseScreenPacket::decode, CloseScreenPacket::handle);
        CHANNEL.registerMessage(5, ShootFromSpaceshipPacket.class, ShootFromSpaceshipPacket::encode, ShootFromSpaceshipPacket::decode, ShootFromSpaceshipPacket::handle);
    }

}
