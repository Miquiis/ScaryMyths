package me.miquiis.onlyblock.common.capability.interfaces;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public interface IWorldOnlyBlock {

    void deserializeNBT(CompoundNBT data);

    void sync();
    void sync(ServerPlayerEntity player);
    void setWorld(World world);
    void setServerWorld(ServerWorld worldOnlyBlockCapability);
    void setDaysLeft(int daysLeft);
    void skipDay();
    void reset();

    Vector3d getIronGenerator();
    Vector3d getGoldGenerator();
    Vector3d getEmeraldGenerator();
    Vector3d getDiamondGenerator();

    CompoundNBT serializeNBT();

    int getDaysLeft();
    int getCurrentDays();

    int getIronDropsPerDay();
    int getGoldDropsPerDay();
    int getDiamondDropsPerDay();
    int getEmeraldDropsPerDay();

    int getNextIronDropTime();
    int getNextGoldDropTime();
    int getNextDiamondDropTime();
    int getNextEmeraldDropTime();

    boolean hasWaveStarted();
    float getMobsPercentage();
    int getMobsLeft();

}
