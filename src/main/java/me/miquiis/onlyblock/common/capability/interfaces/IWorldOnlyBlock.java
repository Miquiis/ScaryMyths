package me.miquiis.onlyblock.common.capability.interfaces;

import me.miquiis.onlyblock.common.classes.AmazonIsland;
import me.miquiis.onlyblock.common.classes.BillionaireIsland;
import me.miquiis.onlyblock.common.classes.StockIsland;
import me.miquiis.onlyblock.common.quests.Quest;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

import java.util.UUID;

public interface IWorldOnlyBlock {

    void deserializeNBT(CompoundNBT data);

    void sync();
    void sync(ServerPlayerEntity player);
    void setServerWorld(ServerWorld worldOnlyBlockCapability);
    void setDaysLeft(int daysLeft);
    void skipDay();

    CompoundNBT serializeNBT();
    UUID getMcDonaldsOwner();
    UUID getStarbucksOwner();
    UUID getTargetOwner();
    UUID getAmazonOwner();

    UUID getTeslaOwner();
    UUID getMicrosoftOwner();
    UUID getFacebookOwner();

    int getDaysLeft();
    int getCurrentDays();

}
