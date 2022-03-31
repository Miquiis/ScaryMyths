package me.miquiis.onlyblock.common.capability.interfaces;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public interface IWorldSyncable {

    void sync();
    void setServerWorld(ServerWorld serverWorld);
    ServerWorld getServerWorld();


}
