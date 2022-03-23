package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.common.entities.BuffetEntity;
import me.miquiis.onlyblock.common.entities.JeffBezosEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AmazonQuestOne extends Quest {

    public AmazonQuestOne(PlayerEntity player) {
        super(player, "amazon_quest_one", "Find and Talk with Jeff", 0f);
    }

    @SubscribeEvent
    public void onEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getHand() != Hand.MAIN_HAND) return;
        if (event.getPlayer().world.isRemote) return;
        if (event.getTarget() instanceof JeffBezosEntity)
        {
            updateProgress((ServerPlayerEntity)getPlayer());
        }
    }

    @Override
    public void updateProgress(ServerPlayerEntity player) {
        this.setProgress(1);
        super.updateProgress(player);
    }
}
