package me.miquiis.onlyblock.common.capability.models;

import me.miquiis.onlyblock.common.capability.OnlyMoneyBlockCapability;
import me.miquiis.onlyblock.common.capability.WorldOnlyMoneyBlockCapability;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.classes.Business;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.OnlyMoneyBlockPacket;
import me.miquiis.onlyblock.server.network.messages.WorldOnlyMoneyBlockPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

public class WorldOnlyMoneyBlock implements IWorldOnlyMoneyBlock {

    private World instance;
    private ServerWorld serverWorld;
    private Business mcdonaldsBusiness, amazonBusiness, teslaBusiness;
    private boolean hasReachedHalfGoal;

    public WorldOnlyMoneyBlock()
    {
        this.mcdonaldsBusiness = new Business("mcdonalds", "McDonalds", null, new Vector3d(215.5, 89, -23.5));
        this.amazonBusiness = new Business("amazon", "Amazon", null, new Vector3d(214, 100, 7));
        this.teslaBusiness = new Business("tesla", "Tesla", null, new Vector3d(270.5, 115, -5));
        this.hasReachedHalfGoal = false;
    }

    @Override
    public World getInstance() {
        return instance;
    }

    @Override
    public void setInstance(World instance) {
        this.instance = instance;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        this.mcdonaldsBusiness.deserializeNBT(data.getCompound("McDonalds"));
        this.amazonBusiness.deserializeNBT(data.getCompound("Amazon"));
        this.teslaBusiness.deserializeNBT(data.getCompound("Tesla"));
        this.hasReachedHalfGoal = data.getBoolean("HasReachedHalfGoal");
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put("McDonalds", mcdonaldsBusiness.serializeNBT());
        compoundNBT.put("Amazon", amazonBusiness.serializeNBT());
        compoundNBT.put("Tesla", teslaBusiness.serializeNBT());
        compoundNBT.putBoolean("HasReachedHalfGoal", hasReachedHalfGoal);
        return compoundNBT;
    }

    @Override
    public void tick() {

    }

    @Override
    public Business getMcDonaldsBusiness() {
        return mcdonaldsBusiness;
    }

    @Override
    public Business getAmazonBusiness() {
        return amazonBusiness;
    }

    @Override
    public Business getTeslaBusiness() {
        return teslaBusiness;
    }

    @Override
    public boolean hasReachedHalfGoal() {
        return hasReachedHalfGoal;
    }

    @Override
    public void reachedHalfGoal() {
        this.hasReachedHalfGoal = true;
    }

    @Override
    public void reset() {
        this.mcdonaldsBusiness = new Business("mcdonalds", "McDonalds", null, new Vector3d(215.5, 89, -23.5));
        this.amazonBusiness = new Business("amazon", "Amazon", null, new Vector3d(214, 100, 7));
        this.teslaBusiness = new Business("tesla", "Tesla", null, new Vector3d(270.5, 115, -5));
        this.hasReachedHalfGoal = false;
        sync();
    }

    @Override
    public void sync() {
        if (serverWorld != null)
        {
            OnlyBlockNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new WorldOnlyMoneyBlockPacket(serializeNBT()));
        }
    }

    @Override
    public void sync(ServerPlayerEntity player) {
        if (serverWorld != null)
        {
            OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new WorldOnlyMoneyBlockPacket(serializeNBT()));
        }
    }

    @Override
    public void setServerWorld(ServerWorld serverWorld) {
        this.serverWorld = serverWorld;
    }

    @Override
    public ServerWorld getServerWorld() {
        return serverWorld;
    }

    public static IWorldOnlyMoneyBlock getCapability(World world)
    {
        return world.getCapability(WorldOnlyMoneyBlockCapability.CURRENT_CAPABILITY).orElse(null);
    }
}
