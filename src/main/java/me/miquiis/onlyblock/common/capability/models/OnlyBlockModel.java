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
    private long timeToBankrupt;
    private long currentTime;

    public OnlyBlockModel()
    {
        stockIsland = new StockIsland();
        amazonIsland = new AmazonIsland();
        billionaireIsland = new BillionaireIsland();
        if (currentQuest != null) currentQuest.clear();
        currentQuest = null;
        currentTime = 0;
        timeToBankrupt = 36000L;
    }

    @Override
    public void sync(ServerPlayerEntity player) {
        System.out.println("Syncing with " + player);
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
        compoundNBT.putLong("CurrentTime", currentTime);
        compoundNBT.putLong("TimeBankrupt", timeToBankrupt);
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
    public long getCurrentTime() {
        return currentTime;
    }

    @Override
    public long getBankruptTime() {
        return timeToBankrupt;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        if (data.contains("AmazonIsland"))
        amazonIsland.deserializeNBT(data.getCompound("AmazonIsland"));
        if (data.contains("StockIsland"))
        stockIsland.deserializeNBT(data.getCompound("StockIsland"));
        if (data.contains("BillionaireIsland"))
        billionaireIsland.deserializeNBT(data.getCompound("BillionaireIsland"));
        if (currentQuest != null) currentQuest.clear();
        if (data.contains("CurrentQuest"))
        currentQuest = Quest.questFromNBT(player, data.getCompound("CurrentQuest"));
        if (data.contains("CurrentTime"))
        currentTime = data.getLong("CurrentTime");
        if (data.contains("TimeBankrupt"))
        timeToBankrupt = data.getLong("TimeBankrupt");
    }

    @Override
    public void tickTime() {
        currentTime++;
        sync(player);
    }

    @Override
    public void setCurrentQuest(Quest quest) {
        if (currentQuest != null) currentQuest.clear();
        this.currentQuest = quest;
        sync(player);
    }

    @Override
    public void reset() {
        stockIsland = new StockIsland();
        amazonIsland = new AmazonIsland();
        billionaireIsland = new BillionaireIsland();
        if (currentQuest != null) currentQuest.clear();
        currentQuest = null;
        currentTime = 0;
        timeToBankrupt = 36000;
    }

    @Override
    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    @Override
    public void resetCurrentTime() {
        this.currentTime = 0;
    }

    @Override
    public void setBankruptTime(int minutes) {
        this.timeToBankrupt = 20L * 60 * minutes;
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
