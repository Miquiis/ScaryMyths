package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.managers.QuestManager;
import me.miquiis.onlyblock.common.utils.TitleUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

public class Quest implements QuestManager.IQuest {

    private PlayerEntity player;
    private String id, title;
    private float progress;
    private boolean isOver;

    public Quest(PlayerEntity player, String id, String title, float progress)
    {
        this.id = id;
        this.title = title;
        this.progress = progress;
        this.isOver = false;
        this.player = player;
        MinecraftForge.EVENT_BUS.register(this);
        if (player instanceof ServerPlayerEntity) onQuestStart((ServerPlayerEntity)player);
    }

    public boolean isOver() {
        return isOver;
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

    public PlayerEntity getPlayer() {
        return player;
    }

    @Override
    public void setProgress(float progress) {
        this.progress = progress;
    }

    @Override
    public void onQuestStart(ServerPlayerEntity player) {
        if (player != null)
        {
            TitleUtils.sendTitleToPlayer(player, "&e&lQuest Unlocked", title, 20, 100, 20);
        }
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
        System.out.println(player);
        this.isOver = true;
        MinecraftForge.EVENT_BUS.unregister(this);
        IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(player);
        onlyBlock.setCurrentQuest(null);
    }

    public void clear()
    {
        this.isOver = true;
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void updateProgress(ServerPlayerEntity player)
    {
        if (checkEnd(player)) return;
        IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(player);
        onlyBlock.sync(player);
    }

    public boolean checkEnd(ServerPlayerEntity player)
    {
        if (this.progress >= 1f)
        {
            System.out.println("Ending Quest");
            onQuestEnd(player);
            return true;
        }
        return false;
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

    public static Quest questFromNBT(PlayerEntity player, CompoundNBT compoundNBT)
    {
        String questId = compoundNBT.getString("QuestID");
        Quest quest = QuestManager.createQuestFromId(player, questId);
        if (quest == null) return null;
        quest.setProgress(compoundNBT.getFloat("QuestProgress"));
        quest.readAdditional(compoundNBT);
        return quest;
    }

    public void readAdditional(CompoundNBT compoundNBT)
    {

    }
}
