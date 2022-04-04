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
            System.out.println(msg.playerSelectorInfo.getPlayerName());
//            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(player);
//            if (onlyMoneyBlock.getCash() >= msg.price)
//            {
//                player.inventory.addItemStackToInventory(msg.itemStack);
//                onlyMoneyBlock.sumCash(-msg.price);
//                //player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundRegister.KATCHING.get(), SoundCategory.PLAYERS, 0.5f, 1f);
//            }
        });
        ctx.get().setPacketHandled(true);
    }

    @Override
    public void setPlayerInfo(PlayerSelectorScreen.PlayerSelectorInfo playerInfo) {
        this.playerSelectorInfo = playerInfo;
    }
}
