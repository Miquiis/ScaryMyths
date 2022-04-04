package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class BuyItemFromShopPacket {

    private final ItemStack itemStack;
    private int price;

    public BuyItemFromShopPacket(ItemStack itemStack, int price) {
        this.itemStack = itemStack;
        this.price = price;
    }

    public static void encode(BuyItemFromShopPacket msg, PacketBuffer buf) {
        buf.writeItemStack(msg.itemStack);
        buf.writeInt(msg.price);
    }

    public static BuyItemFromShopPacket decode(PacketBuffer buf) {
        return new BuyItemFromShopPacket(buf.readItemStack(), buf.readInt());
    }

    public static void handle(final BuyItemFromShopPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(player);
            if (onlyMoneyBlock.getCash() >= msg.price)
            {
                onlyMoneyBlock.sumCash(-msg.price);
                if (msg.itemStack.getItem() == ItemRegister.SABOTAGE.get())
                {
                    OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new OpenPlayerSelectPacket());
                    return;
                }
                player.inventory.addItemStackToInventory(msg.itemStack);
                //player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundRegister.KATCHING.get(), SoundCategory.PLAYERS, 0.5f, 1f);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
