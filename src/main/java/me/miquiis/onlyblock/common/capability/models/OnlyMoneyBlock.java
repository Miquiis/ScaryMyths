package me.miquiis.onlyblock.common.capability.models;

import me.miquiis.onlyblock.common.capability.OnlyMoneyBlockCapability;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.OnlyMoneyBlockPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.PacketDistributor;

public class OnlyMoneyBlock implements IOnlyMoneyBlock {

    private ServerPlayerEntity serverPlayer;
    private String pin;
    private int days, bankAccount, cash, frozenDay;
    private boolean isFrozen;

    public OnlyMoneyBlock()
    {
        this.days = 100;
        this.bankAccount = 0;
        this.cash = 0;
        this.frozenDay = -1;
        this.isFrozen = false;
        this.pin = "";
    }

    @Override
    public String getPin() {
        return pin;
    }

    @Override
    public int getDays() {
        return days;
    }

    @Override
    public int getBankAccount() {
        return bankAccount;
    }

    @Override
    public int getCash() {
        return cash;
    }

    @Override
    public boolean isFrozen() {
        return isFrozen;
    }

    @Override
    public int getFrozenDay() {
        return frozenDay;
    }

    @Override
    public void setPin(String pin) {
        this.pin = pin;
        sync(false);
    }

    @Override
    public void setDays(int days) {
        this.days = days;
        sync(true);
    }

    @Override
    public void sumDays(int days) {
        this.days += days;
        sync(true);
    }

    @Override
    public void setBankAccount(int amount) {
        this.bankAccount = amount;
        sync(true);
    }

    @Override
    public void setCash(int amount) {
        this.cash = amount;
        sync(true);
    }

    @Override
    public void sumBankAccount(int amount) {
        this.bankAccount += amount;
        sync(true);
    }

    @Override
    public void sumCash(int amount) {
        this.cash += amount;
        sync(true);
    }

    @Override
    public void setFrozen(int frozenDay, boolean frozen) {
        this.frozenDay = frozenDay;
        this.isFrozen = frozen;
    }

    @Override
    public void sync(boolean syncAll) {
        if (serverPlayer != null)
        {
            CompoundNBT compoundNBT = serializeNBT();
            compoundNBT.putInt("ClientID", serverPlayer.getEntityId());
            OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new OnlyMoneyBlockPacket(compoundNBT));
        }
        if (syncAll)
        syncToAll();
    }

    @Override
    public void syncToAll() {
        if (serverPlayer != null)
        {
            serverPlayer.getServer().getPlayerList().getPlayers().forEach(player -> {
                if (serverPlayer.equals(player)) return;
                CompoundNBT compoundNBT = serializeNBT();
                compoundNBT.putInt("ClientID", serverPlayer.getEntityId());
                OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new OnlyMoneyBlockPacket(compoundNBT));
            });
        }
    }

    @Override
    public void setServerPlayer(ServerPlayerEntity serverPlayer) {
        this.serverPlayer = serverPlayer;
    }

    @Override
    public ServerPlayerEntity getServerPlayer() {
        return serverPlayer;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        this.days = data.getInt("Days");
        this.bankAccount = data.getInt("BankAccount");
        this.cash = data.getInt("Cash");
        this.frozenDay = data.getInt("FrozenDay");
        this.isFrozen = data.getBoolean("IsFrozen");
        this.pin = data.getString("Pin");
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("Days", days);
        compoundNBT.putInt("BankAccount", bankAccount);
        compoundNBT.putInt("Cash", cash);
        compoundNBT.putInt("FrozenDay", frozenDay);
        compoundNBT.putBoolean("IsFrozen", isFrozen);
        compoundNBT.putString("Pin", pin);
        return compoundNBT;
    }

    public static IOnlyMoneyBlock getCapability(PlayerEntity player)
    {
        return player.getCapability(OnlyMoneyBlockCapability.CURRENT_CAPABILITY).orElse(null);
    }
}
