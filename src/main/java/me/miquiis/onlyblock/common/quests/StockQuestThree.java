package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class StockQuestThree extends Quest {

    private int prevAmount, currentAmount;

    public StockQuestThree(PlayerEntity player) {
        super(player, "stock_quest_three", "Reach 10k by Absorbing Good Stocks", 0f);
        System.out.println("Create instande of Quest Three");
        prevAmount = 0;
        currentAmount = 0;
    }

    @SubscribeEvent
    public void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
    {
        if (!event.player.world.isRemote && !isOver() && event.phase == TickEvent.Phase.START && event.player.world.getGameTime() % 20 == 0)
        {
            prevAmount = currentAmount;
            currentAmount = event.player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null).getAmount();
            if (prevAmount != currentAmount)
            {
                this.updateProgress((ServerPlayerEntity)event.player);
            }
        }
    }

    @Override
    public void updateProgress(ServerPlayerEntity player) {
        this.setProgress(currentAmount / 10000f);
        super.updateProgress(player);
    }

    @Override
    public void onQuestEnd(ServerPlayerEntity player) {
        super.onQuestEnd(player);
        OnlyBlockModel.getCapability(player).setCurrentQuest(new StockQuestFour(player));
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("CurrentAmount", currentAmount);
        compoundNBT.putInt("PrevAmount", prevAmount);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("CurrentAmount"))
        {
            this.currentAmount = compoundNBT.getInt("CurrentAmount");
        }
        if (compoundNBT.contains("PrevAmount"))
        {
            this.prevAmount = compoundNBT.getInt("PrevAmount");
        }
    }
}
