package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StockQuestOne extends Quest {

    private int stockOresFound;
    private int prevOresFound;

    public StockQuestOne(PlayerEntity player) {
        super(player, "stock_quest_one", "Find 2 Stock Ores in Chests", 0f);
        stockOresFound = 0;
        prevOresFound = 0;
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
    {
        if (!event.player.world.isRemote && event.phase == TickEvent.Phase.END && !isOver())
        {
            prevOresFound = stockOresFound;
            stockOresFound = event.player.inventory.count(BlockRegister.STOCK_ORE.get().asItem());
            if (prevOresFound != stockOresFound)
            {
                this.updateProgress((ServerPlayerEntity)event.player);
            }
        }
    }

    @Override
    public void updateProgress(ServerPlayerEntity player) {
        this.setProgress(stockOresFound / 2f);
        super.updateProgress(player);
    }

    @Override
    public void onQuestEnd(ServerPlayerEntity player) {
        super.onQuestEnd(player);
        OnlyBlockModel.getCapability(player).setCurrentQuest(new StockQuestTwo(player));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("StockOresFound", stockOresFound);
        compoundNBT.putInt("PrevStockOresFound", prevOresFound);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("StockOresFound"))
        {
            this.stockOresFound = compoundNBT.getInt("StockOresFound");
        }
        if (compoundNBT.contains("PrevStockOresFound"))
        {
            this.prevOresFound = compoundNBT.getInt("PrevStockOresFound");
        }
    }
}
