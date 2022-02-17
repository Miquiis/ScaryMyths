package me.miquiis.onlyblock.common.capability.models;

import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.CurrencyPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.PacketDistributor;

public class Currency implements ICurrency {

    private ServerPlayerEntity player;
    private int amount = 0;

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public void setAmount(int amount, boolean sync) {
        this.amount = amount;

        if(this.amount < 0)
            this.amount = 0;

        if (!sync) return;
        sync(player);
    }

    @Override
    public void addOrSubtractAmount(int amount) {
        setAmount(this.amount + amount, true);
    }

    @Override
    public void sync(ServerPlayerEntity player) {
        if (player == null) return;
        OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new CurrencyPacket(serializeNBT()));
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT data = new CompoundNBT();
        data.putInt("currency", getAmount());
        return data;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        this.amount = data.getInt("currency");
    }

    public void setPlayer(ServerPlayerEntity player)
    {
        this.player = player;
    }
}
