package me.miquiis.scarymyths.common.capability.interfaces;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public interface IWorldSyncable {

    void sync();
    void sync(ServerPlayerEntity player);
    void setServerWorld(ServerWorld serverWorld);
    ServerWorld getServerWorld();


}
