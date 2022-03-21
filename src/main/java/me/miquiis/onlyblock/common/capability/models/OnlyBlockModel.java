package me.miquiis.onlyblock.common.capability.models;

import me.miquiis.onlyblock.common.capability.OnlyBlockCapability;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.classes.AmazonIsland;
import me.miquiis.onlyblock.common.classes.BillionaireIsland;
import me.miquiis.onlyblock.common.classes.StockIsland;
import me.miquiis.onlyblock.common.quests.Quest;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
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
    private Quest currentQuest;

    public OnlyBlockModel()
    {
        stockIsland = new StockIsland();
        amazonIsland = new AmazonIsland();
        billionaireIsland = new BillionaireIsland();
        currentQuest = null;
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
        compoundNBT.put("StockIsland", stockIsland.serializeNBT());
        compoundNBT.put("BillionaireIsland", billionaireIsland.serializeNBT());
        if (currentQuest != null)
        compoundNBT.put("CurrentQuest", currentQuest.serializeNBT());
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
    public Quest getCurrentQuest() {
        return currentQuest;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        if (data.contains("AmazonIsland"))
        amazonIsland.deserializeNBT(data.getCompound("AmazonIsland"));
        if (data.contains("StockIsland"))
        stockIsland.deserializeNBT(data.getCompound("StockIsland"));
        if (data.contains("BillionaireIsland"))
        billionaireIsland.deserializeNBT(data.getCompound("BillionaireIsland"));
        if (data.contains("CurrentQuest"))
        currentQuest = Quest.questFromNBT(data.getCompound("CurrentQuest"));
    }

    @Override
    public void setCurrentQuest(Quest quest) {
        this.currentQuest = quest;
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
