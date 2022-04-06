package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.PlayerSelectorScreen;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyMoneyBlock;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class BuyDestroyPacket implements IPlayerSelectablePacket {

    private PlayerSelectorScreen.PlayerSelectorInfo playerSelectorInfo;

    public BuyDestroyPacket()
    {

    }

    public BuyDestroyPacket(PlayerSelectorScreen.PlayerSelectorInfo playerSelectorInfo)
    {
        this.playerSelectorInfo = playerSelectorInfo;
    }

    public static void encode(BuyDestroyPacket msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.playerSelectorInfo.serializeNBT());
    }

    public static BuyDestroyPacket decode(PacketBuffer buf) {
        return new BuyDestroyPacket(PlayerSelectorScreen.PlayerSelectorInfo.deserializeNBT(buf.readCompoundTag()));
    }

    public static void handle(final BuyDestroyPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            ServerPlayerEntity target = (ServerPlayerEntity) player.getServerWorld().getPlayerByUuid(msg.playerSelectorInfo.getPlayerUUID());
            if (target == null)
            {
                System.out.println("No target");
                return;
            }

            IWorldOnlyMoneyBlock worldOnlyMoneyBlock = WorldOnlyMoneyBlock.getCapability(target.world);
            if (worldOnlyMoneyBlock.getMcDonaldsBusiness().getBusinessOwner().equals(target.getUniqueID()))
            {
                worldOnlyMoneyBlock.getMcDonaldsBusiness().setBusinessOwner(null);
                worldOnlyMoneyBlock.sync();
            } else if (worldOnlyMoneyBlock.getAmazonBusiness().getBusinessOwner().equals(target.getUniqueID()))
            {
                worldOnlyMoneyBlock.getAmazonBusiness().setBusinessOwner(null);
                worldOnlyMoneyBlock.sync();
            } else if (worldOnlyMoneyBlock.getTeslaBusiness().getBusinessOwner().equals(target.getUniqueID()))
            {
                worldOnlyMoneyBlock.getTeslaBusiness().setBusinessOwner(null);
                worldOnlyMoneyBlock.sync();
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void setPlayerInfo(PlayerSelectorScreen.PlayerSelectorInfo playerInfo) {
        this.playerSelectorInfo = playerInfo;
    }
}
