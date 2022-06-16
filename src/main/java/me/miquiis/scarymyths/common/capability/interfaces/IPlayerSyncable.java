package me.miquiis.scarymyths.common.capability.interfaces;

import net.minecraft.entity.player.ServerPlayerEntity;

public interface IPlayerSyncable {

    void sync(boolean syncAll);
    void syncToAll();
    void setServerPlayer(ServerPlayerEntity serverPlayer);
    ServerPlayerEntity getServerPlayer();


}
