package me.miquiis.onlyblock.common.capability.interfaces;

import me.miquiis.onlyblock.common.classes.AmazonIsland;
import me.miquiis.onlyblock.common.classes.BillionaireIsland;
import me.miquiis.onlyblock.common.classes.StockIsland;
import me.miquiis.onlyblock.common.managers.QuestManager;
import me.miquiis.onlyblock.common.quests.Quest;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface IOnlyBlock {

    void sync(ServerPlayerEntity player);
    void setPlayer(ServerPlayerEntity player);
    void deserializeNBT(CompoundNBT data);

    void setCurrentQuest(Quest quest);
    void reset();

    CompoundNBT serializeNBT();
    AmazonIsland getAmazonIsland();
    BillionaireIsland getBillionaireIsland();
    StockIsland getStockIsland();
    Quest getCurrentQuest();
}
