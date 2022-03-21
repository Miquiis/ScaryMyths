package me.miquiis.onlyblock.common.managers;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.quests.BreakHundredBlocksQuest;
import me.miquiis.onlyblock.common.quests.Quest;
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

    public static Quest createQuestFromId(String id)
    {
        switch (id)
        {
            case "hundredblocks":
            {
                return new BreakHundredBlocksQuest();
            }
        }
        return null;
    }

}
