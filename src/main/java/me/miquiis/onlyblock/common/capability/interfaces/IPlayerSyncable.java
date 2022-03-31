package me.miquiis.onlyblock.common.capability.interfaces;

import net.minecraft.entity.player.ServerPlayerEntity;

public interface IPlayerSyncable {

    void sync();
    void setServerPlayer(ServerPlayerEntity serverPlayer);
    ServerPlayerEntity getServerPlayer();


}
