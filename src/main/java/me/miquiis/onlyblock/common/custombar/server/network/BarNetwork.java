package me.miquiis.onlyblock.common.custombar.server.network;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.custombar.server.network.messages.SendBarUpdate;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BarNetwork {

    public static final String NETWORK_VERSION = "0.1.0";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(OnlyBlock.MOD_ID, "bar_network"), () -> NETWORK_VERSION,
            version -> version.equals(NETWORK_VERSION), version -> version.equals(NETWORK_VERSION)
    );

    public static void init() {
        CHANNEL.registerMessage(0, SendBarUpdate.class, SendBarUpdate::encode, SendBarUpdate::decode, SendBarUpdate::handle);
    }

}
