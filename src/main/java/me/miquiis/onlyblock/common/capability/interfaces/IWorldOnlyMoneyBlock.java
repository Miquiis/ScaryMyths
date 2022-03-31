package me.miquiis.onlyblock.common.capability.interfaces;

import me.miquiis.onlyblock.common.classes.Business;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.world.World;

public interface IWorldOnlyMoneyBlock extends IInstance<World>, IWorldSyncable, ISerializable, ITickable {

    Business getMcDonaldsBusiness();
    Business getAmazonBusiness();
    Business getTeslaBusiness();
    boolean hasReachedHalfGoal();

    void reachedHalfGoal();

}
