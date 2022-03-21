package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.managers.QuestManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.HashSet;
import java.util.Set;

public class Quest implements QuestManager.IQuest {

    private String id, title;
    private float progress;

    public Quest(String id, String title, float progress)
    {
        this.id = id;
        this.title = title;
        this.progress = progress;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public float getProgress() {
        return progress;
    }

    @Override
    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    public void onQuestStart(ServerPlayerEntity player) {

    }

    @Override
    public void onQuestProgress(ServerPlayerEntity player, float percent) {
        this.progress += percent;
        checkEnd(player);
        IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(player);
        onlyBlock.sync(player);
    }

    @Override
    public void onQuestEnd(ServerPlayerEntity player) {
        IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(player);
        onlyBlock.setCurrentQuest(null);
    }

    public void updateProgress(ServerPlayerEntity player)
    {
        checkEnd(player);
        IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(player);
        onlyBlock.sync(player);
    }

    public void checkEnd(ServerPlayerEntity player)
    {
        if (this.progress > 1f)
        {
            onQuestEnd(player);
        }
    }

    public CompoundNBT serializeNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("QuestID", id);
        compoundNBT.putFloat("QuestProgress", progress);
        writeAdditional(compoundNBT);
        return compoundNBT;
    }

    public void writeAdditional(CompoundNBT compoundNBT)
    {

    }

    public static Quest questFromNBT(CompoundNBT compoundNBT)
    {
        String questId = compoundNBT.getString("QuestID");
        Quest quest = QuestManager.createQuestFromId(questId);
        if (quest == null) return null;
        quest.setProgress(compoundNBT.getFloat("QuestProgress"));
        quest.readAdditional(compoundNBT);
        return quest;
    }

    public void readAdditional(CompoundNBT compoundNBT)
    {

    }
}
