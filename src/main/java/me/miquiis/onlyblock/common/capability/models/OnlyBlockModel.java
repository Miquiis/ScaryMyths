package me.miquiis.onlyblock.common.capability.models;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.OnlyBlockCapability;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.classes.AmazonIsland;
import me.miquiis.onlyblock.common.classes.BillionaireIsland;
import me.miquiis.onlyblock.common.classes.StockIsland;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.CurrencyPacket;
import me.miquiis.onlyblock.server.network.messages.OnlyBlockPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.PacketDistributor;

public class OnlyBlockModel implements IOnlyBlock {

    private ServerPlayerEntity player;
    private StockIsland stockIsland;
    private AmazonIsland amazonIsland;
    private BillionaireIsland billionaireIsland;

    public OnlyBlockModel()
    {
        stockIsland = new StockIsland();
        amazonIsland = new AmazonIsland();
        billionaireIsland = new BillionaireIsland();
    }

    @Override
    public void sync(ServerPlayerEntity player) {
        if (player == null) return;
        OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new OnlyBlockPacket(serializeNBT()));
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("AmazonIsland", amazonIsland.serializeNBT());
        return compoundNBT;
    }

    @Override
    public AmazonIsland getAmazonIsland() {
        return amazonIsland;
    }

    @Override
    public BillionaireIsland getBillionaireIsland() {
        return billionaireIsland;
    }

    @Override
    public StockIsland getStockIsland() {
        return stockIsland;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        if (data.contains("AmazonIsland"))
        amazonIsland.deserializeNBT(data.getCompound("AmazonIsland"));
    }

    @Override
    public void setPlayer(ServerPlayerEntity player) {
        this.player = player;
    }

    public static IOnlyBlock getCapability(PlayerEntity player)
    {
        return player.getCapability(OnlyBlockCapability.CURRENCY_CAPABILITY).orElse(null);
    }
}
