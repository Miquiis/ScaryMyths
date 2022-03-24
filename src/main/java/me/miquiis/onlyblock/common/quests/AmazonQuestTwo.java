package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.entities.BuffetEntity;
import me.miquiis.onlyblock.common.entities.JeffBezosEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AmazonQuestTwo extends Quest {

    public AmazonQuestTwo(PlayerEntity player) {
        super(player, "amazon_quest_two", "Eliminate Jeff Bezos", 0f);
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        if (getPlayer() == null) return;
        if (event.getEntityLiving().world.isRemote) return;
        if (event.getEntityLiving() instanceof JeffBezosEntity)
        {
            updateProgress((ServerPlayerEntity)getPlayer());
        }
    }

    @Override
    public void updateProgress(ServerPlayerEntity player) {
        this.setProgress(1);
        super.updateProgress(player);
    }

    @Override
    public void onQuestEnd(ServerPlayerEntity player) {
        super.onQuestEnd(player);
        OnlyBlockModel.getCapability(player).getAmazonIsland().setAcquired(true);
    }
}
