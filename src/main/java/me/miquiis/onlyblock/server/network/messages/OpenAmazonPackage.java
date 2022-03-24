package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class OpenAmazonPackage {

    private static final List<Item> POSSIBLE_DROPS = new ArrayList<>(Arrays.asList(
            Items.DIAMOND,
            Items.CLOCK,
            Items.STRING,
            Items.GREEN_BED,
            Items.END_ROD
    ));

    private final ItemStack itemToRepair;

    public OpenAmazonPackage(ItemStack itemToRepair) {
        this.itemToRepair = itemToRepair;
    }

    public static void encode(OpenAmazonPackage msg, PacketBuffer buf) {
        buf.writeItemStack(msg.itemToRepair);
    }

    public static OpenAmazonPackage decode(PacketBuffer buf) {
        return new OpenAmazonPackage(buf.readItemStack());
    }

    public static void handle(final OpenAmazonPackage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity player = ctx.get().getSender();
            CompoundNBT itemTag = msg.itemToRepair.getOrCreateTag();
            if (itemTag.contains("ItemPrice"))
            {
                int itemPrice = itemTag.getInt("ItemPrice");
                if (itemPrice >= 1000000000) {
                    player.inventory.setInventorySlotContents(player.inventory.getSlotFor(msg.itemToRepair), new ItemStack(
                            Items.DIAMOND
                    ));
                } else if (itemPrice >= 100000)
                {
                    player.inventory.setInventorySlotContents(player.inventory.getSlotFor(msg.itemToRepair), new ItemStack(
                            ItemRegister.JETPACK.get()
                    ));
                } else
                {
                    player.inventory.setInventorySlotContents(player.inventory.getSlotFor(msg.itemToRepair), new ItemStack(
                          POSSIBLE_DROPS.get(MathUtils.getRandomMax(POSSIBLE_DROPS.size()))
                    ));
                }
            }
            OnlyBlockModel.getCapability(player).getAmazonIsland().deliver(player, false);
            OnlyBlockModel.getCapability(player).sync(player);
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.PLAYERS, 0.5f, 1f);
        });
        ctx.get().setPacketHandled(true);
    }

}
