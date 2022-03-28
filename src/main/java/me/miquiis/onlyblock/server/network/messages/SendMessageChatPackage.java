package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.PlayerSelectorScreen;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SendMessageChatPackage implements IPlayerSelectablePacket{

    private PlayerSelectorScreen.PlayerSelectorInfo info;

    public SendMessageChatPackage()
    {

    }

    public SendMessageChatPackage(PlayerSelectorScreen.PlayerSelectorInfo info)
    {
        this.info = info;
    }

    @Override
    public void setPlayerInfo(PlayerSelectorScreen.PlayerSelectorInfo playerInfo) {
        this.info = playerInfo;
    }

    public static void encode(SendMessageChatPackage msg, PacketBuffer buf) {
        buf.writeCompoundTag(msg.info.serializeNBT());
    }

    public static SendMessageChatPackage decode(PacketBuffer buf) {
        return new SendMessageChatPackage(PlayerSelectorScreen.PlayerSelectorInfo.deserializeNBT(buf.readCompoundTag()));
    }

    public static void handle(final SendMessageChatPackage msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            final ServerPlayerEntity player = ctx.get().getSender();
            player.sendStatusMessage(new StringTextComponent(msg.info.getPlayerName()), false);
        });
        ctx.get().setPacketHandled(true);
    }
}
