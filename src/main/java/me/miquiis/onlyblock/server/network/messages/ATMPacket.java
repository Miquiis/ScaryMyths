package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.client.gui.ATMScreen;
import me.miquiis.onlyblock.common.capability.OnlyMoneyBlockCapability;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ATMPacket {

    public enum ATMPacketType {
        SETUP_PIN,
        REQUEST_ATM,
        SEND_ATM,
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER
    }

    private final ATMPacketType packetType;
    private final CompoundNBT nbt;

    public ATMPacket(ATMPacketType packetType, CompoundNBT nbt) {
        this.packetType = packetType;
        this.nbt = nbt;
    }

    public static void encode(ATMPacket msg, PacketBuffer buf) {
        buf.writeEnumValue(msg.packetType);
        buf.writeCompoundTag(msg.nbt);
    }

    public static ATMPacket decode(PacketBuffer buf) {
        return new ATMPacket(buf.readEnumValue(ATMPacketType.class), buf.readCompoundTag());
    }

    public static void handle(final ATMPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            switch (msg.packetType)
            {
                case SETUP_PIN:
                {
                    handleSetupPIN(msg, ctx);
                    break;
                }
                case REQUEST_ATM:
                {
                    handleRequestATM(msg, ctx);
                    break;
                }
                case SEND_ATM:
                {
                    handleSendATM(msg, ctx);
                    break;
                }
                case DEPOSIT:
                {
                    handleDeposit(msg, ctx);
                    break;
                }
                case WITHDRAWAL:
                {
                    handleWithdrawal(msg, ctx);
                    break;
                }
                case TRANSFER:
                {
                    handleTransfer(msg, ctx);
                    break;
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }

    private static void handleSetupPIN(ATMPacket msg, Supplier<NetworkEvent.Context> ctx) {
        String pin = msg.nbt.getString("Pin");
        ServerPlayerEntity serverPlayer = ctx.get().getSender();
        IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(serverPlayer);
        onlyMoneyBlock.setPin(pin);
        OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new ATMPacket(ATMPacketType.SEND_ATM, createSendATMData(serverPlayer.getEntityId())));
    }

    private static void handleTransfer(ATMPacket msg, Supplier<NetworkEvent.Context> ctx) {
        int clientID = msg.nbt.getInt("ClientID");
        int amount = msg.nbt.getInt("Amount");
        Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(clientID);

        if (entity instanceof ServerPlayerEntity)
        {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)entity;
            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(serverPlayer);
        }
    }

    private static void handleWithdrawal(ATMPacket msg, Supplier<NetworkEvent.Context> ctx) {
        int clientID = msg.nbt.getInt("ClientID");
        int amount = msg.nbt.getInt("Amount");
        Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(clientID);

        if (entity instanceof ServerPlayerEntity)
        {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)entity;
            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(serverPlayer);

            if (onlyMoneyBlock.getBankAccount() >= amount)
            {
                onlyMoneyBlock.sumBankAccount(-amount);
                onlyMoneyBlock.sumCash(amount);
            }
        }
    }

    private static void handleDeposit(ATMPacket msg, Supplier<NetworkEvent.Context> ctx) {
        int clientID = msg.nbt.getInt("ClientID");
        int amount = msg.nbt.getInt("Amount");
        Entity entity = ctx.get().getSender().getServerWorld().getEntityByID(clientID);

        if (entity instanceof ServerPlayerEntity)
        {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity)entity;
            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(serverPlayer);

            if (onlyMoneyBlock.getCash() >= amount)
            {
                onlyMoneyBlock.sumBankAccount(amount);
                onlyMoneyBlock.sumCash(-amount);
            }
        }
    }

    private static void handleSendATM(ATMPacket msg, Supplier<NetworkEvent.Context> ctx) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            Minecraft.getInstance().displayGuiScreen(new ATMScreen(msg.nbt.getInt("ClientID")));
        });
    }

    private static void handleRequestATM(ATMPacket msg, Supplier<NetworkEvent.Context> ctx) {
        String pin = msg.nbt.getString("Pin");
        if (OnlyMoneyBlock.getCapability(ctx.get().getSender()).getPin().isEmpty())
        {
            System.out.println("Setting up pin");
            OnlyBlockNetwork.CHANNEL.sendToServer(new ATMPacket(ATMPacketType.SETUP_PIN, createRequestATMData(pin)));
            return;
        }
        for (ServerPlayerEntity player : ctx.get().getSender().getServer().getPlayerList().getPlayers()) {
            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(player);
            if (onlyMoneyBlock.getPin().equals(pin))
            {
                System.out.println("Correct pin");
                OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new ATMPacket(ATMPacketType.SEND_ATM, createSendATMData(player.getEntityId())));
                return;
            }
        }
        System.out.println("Sending null data");
        OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> ctx.get().getSender()), new ATMPacket(ATMPacketType.SEND_ATM, createSendATMData(-1)));
    }

    public static CompoundNBT createProcessATMData(@Nullable String playerName, int clientId, int amount)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (playerName != null) compoundNBT.putString("PlayerName", playerName);
        compoundNBT.putInt("ClientID", clientId);
        compoundNBT.putInt("Amount", amount);
        return compoundNBT;
    }

    public static CompoundNBT createRequestATMData(String pin)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Pin", pin);
        return compoundNBT;
    }

    public static CompoundNBT createSendATMData(int clientID)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("ClientID", clientID);
        return compoundNBT;
    }

}
