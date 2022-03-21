package me.miquiis.onlyblock.common.quests;

import me.miquiis.onlyblock.common.managers.QuestManager;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.world.BlockEvent;

public class BreakHundredBlocksQuest extends Quest {

    private int blocksBroken;

    public BreakHundredBlocksQuest() {
        super("hundredblocks", "Break 100 Blocks", 0f);
        blocksBroken = 0;
    }

    public void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if (!event.isCanceled())
        {
            blocksBroken++;
            this.updateProgress((ServerPlayerEntity)event.getPlayer());
        }
    }

    @Override
    public void updateProgress(ServerPlayerEntity player) {
        this.setProgress(blocksBroken / 100f);
        super.updateProgress(player);
    }

    @Override
    public void writeAdditional(CompoundNBT compoundNBT) {
        super.writeAdditional(compoundNBT);
        compoundNBT.putInt("BlocksBroken", blocksBroken);
    }

    @Override
    public void readAdditional(CompoundNBT compoundNBT) {
        super.readAdditional(compoundNBT);
        if (compoundNBT.contains("BlocksBroken"))
        blocksBroken = compoundNBT.getInt("BlocksBroken");
    }
}
