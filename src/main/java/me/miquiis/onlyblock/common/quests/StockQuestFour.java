package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.common.entities.BuffetEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StockQuestFour extends Quest {

    public StockQuestFour(PlayerEntity player) {
        super(player, "stock_quest_four", "Find and Eliminate Buffet", 0f);
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if (event.getEntityLiving().world.isRemote) return;
        if (event.getEntityLiving() instanceof BuffetEntity)
        {
            System.out.println(getPlayer());
            updateProgress((ServerPlayerEntity)getPlayer());
        }
    }

    @Override
    public void updateProgress(ServerPlayerEntity player) {
        this.setProgress(1);
        super.updateProgress(player);
    }
}
