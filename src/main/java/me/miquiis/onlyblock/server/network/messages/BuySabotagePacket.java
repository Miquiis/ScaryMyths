package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.PlayerSelectorScreen;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BuySabotagePacket implements IPlayerSelectablePacket {

    private PlayerSelectorScreen.PlayerSelectorInfo playerSelectorInfo;

    public BuySabotagePacket()
    {

    }

    public BuySabotagePacket(PlayerSelectorScreen.PlayerSelectorInfo playerSelectorInfo)
    {
        this.playerSelectorInfo = playerSelectorInfo;
    }

    public static void encode(BuySabotagePacket msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.playerSelectorInfo.serializeNBT());
    }

    public static BuySabotagePacket decode(PacketBuffer buf) {
        return new BuySabotagePacket(PlayerSelectorScreen.PlayerSelectorInfo.deserializeNBT(buf.readCompoundTag()));
    }

    public static void handle(final BuySabotagePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            ServerPlayerEntity target = (ServerPlayerEntity) player.getServerWorld().getPlayerByUuid(msg.playerSelectorInfo.getPlayerUUID());
            if (target == null)
            {
                System.out.println("No target");
                return;
            }
            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(target);
            onlyMoneyBlock.sumDays(-20);
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void setPlayerInfo(PlayerSelectorScreen.PlayerSelectorInfo playerInfo) {
        this.playerSelectorInfo = playerInfo;
    }
}
