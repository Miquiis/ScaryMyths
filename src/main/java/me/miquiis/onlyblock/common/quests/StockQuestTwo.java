package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StockQuestTwo extends Quest {

    private int stockSwordCount;

    public StockQuestTwo(PlayerEntity player) {
        super(player, "stock_quest_two", "Smelt Ores and Craft Stock Sword", 0f);
        stockSwordCount = 0;
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
    {
        if (!event.player.world.isRemote && event.phase == TickEvent.Phase.END)
        {
            stockSwordCount = event.player.inventory.count(ItemRegister.STOCK_SWORD.get());
            if (stockSwordCount > 0)
            {
                this.updateProgress((ServerPlayerEntity)event.player);
            }
        }
    }

    @Override
    public void updateProgress(ServerPlayerEntity player) {
        this.setProgress(stockSwordCount);
        super.updateProgress(player);
    }

    @Override
    public void onQuestEnd(ServerPlayerEntity player) {
        super.onQuestEnd(player);
        OnlyBlockModel.getCapability(player).setCurrentQuest(new StockQuestThree(player));
    }
}
