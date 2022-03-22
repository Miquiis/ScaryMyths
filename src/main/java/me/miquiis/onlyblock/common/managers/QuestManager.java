package me.miquiis.onlyblock.common.managers;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.quests.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class QuestManager {

    public interface IQuest {

        String getId();
        String getTitle();
        float getProgress();

        void setProgress(float progress);

        void onQuestStart(ServerPlayerEntity player);
        void onQuestProgress(ServerPlayerEntity player, float percent);
        void onQuestEnd(ServerPlayerEntity player);
    }

    public static void startQuest(ServerPlayerEntity player, Quest quest)
    {

    }

    public static void endQuest(ServerPlayerEntity player)
    {

    }

    public static Quest getCurrentQuest(ServerPlayerEntity player)
    {
        IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(player);
        return onlyBlock.getCurrentQuest();
    }

    public static Quest createQuestFromId(PlayerEntity player, String id)
    {
        switch (id)
        {
            case "stock_quest_one":
            {
                return new StockQuestOne(player);
            }
            case "stock_quest_two":
            {
                return new StockQuestTwo(player);
            }
            case "stock_quest_three":
            {
                return new StockQuestThree(player);
            }
            case "stock_quest_four":
            {
                return new StockQuestFour(player);
            }
        }
        return null;
    }

}
