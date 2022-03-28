package me.miquiis.onlyblock.common.capability.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.WorldOnlyBlockCapability;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyBlock;
import me.miquiis.onlyblock.common.capability.storages.WorldOnlyBlockStorage;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.WorldOnlyBlockPacket;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;

public class WorldOnlyBlock implements IWorldOnlyBlock {

    private ServerWorld serverWorld;
    private int daysLeft;
    private UUID mcDonaldsOwner, starbucksOwner, targetOwner, amazonOwner, teslaOwner, microsoftOwner, facebookOwner;

    public WorldOnlyBlock()
    {
        this.daysLeft = 100;
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        if (data.contains("DaysLeft"))
            daysLeft = data.getInt("DaysLeft");
    }

    @Override
    public void sync() {
        if (serverWorld != null)
        {
            OnlyBlockNetwork.CHANNEL.send(PacketDistributor.ALL.noArg(), new WorldOnlyBlockPacket(serializeNBT()));
        }
    }

    @Override
    public void sync(ServerPlayerEntity player) {
        OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), new WorldOnlyBlockPacket(serializeNBT()));
    }

    @Override
    public void setServerWorld(ServerWorld serverWorld) {
        this.serverWorld = serverWorld;
    }

    @Override
    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
        sync();
    }

    @Override
    public void skipDay() {
        this.daysLeft--;
        sync();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("DaysLeft", daysLeft);
        return nbt;
    }

    public void setAmazonOwner(UUID amazonOwner) {
        this.amazonOwner = amazonOwner;
        sync();
    }

    public void setFacebookOwner(UUID facebookOwner) {
        this.facebookOwner = facebookOwner;
        sync();
    }

    public void setMcDonaldsOwner(UUID mcDonaldsOwner) {
        this.mcDonaldsOwner = mcDonaldsOwner;
        sync();
    }

    public void setMicrosoftOwner(UUID microsoftOwner) {
        this.microsoftOwner = microsoftOwner;
        sync();
    }

    public void setStarbucksOwner(UUID starbucksOwner) {
        this.starbucksOwner = starbucksOwner;
        sync();
    }

    public void setTargetOwner(UUID targetOwner) {
        this.targetOwner = targetOwner;
        sync();
    }

    public void setTeslaOwner(UUID teslaOwner) {
        this.teslaOwner = teslaOwner;
        sync();
    }

    @Override
    public UUID getMcDonaldsOwner() {
        return mcDonaldsOwner;
    }

    @Override
    public UUID getStarbucksOwner() {
        return starbucksOwner;
    }

    @Override
    public UUID getTargetOwner() {
        return targetOwner;
    }

    @Override
    public UUID getAmazonOwner() {
        return amazonOwner;
    }

    @Override
    public UUID getTeslaOwner() {
        return teslaOwner;
    }

    @Override
    public UUID getMicrosoftOwner() {
        return microsoftOwner;
    }

    @Override
    public UUID getFacebookOwner() {
        return facebookOwner;
    }

    @Override
    public int getDaysLeft() {
        return daysLeft;
    }

    @Override
    public int getCurrentDays() {
        return 100 - daysLeft;
    }

    public static IWorldOnlyBlock getCapability(World world)
    {
        return world.getCapability(WorldOnlyBlockCapability.CURRENT_CAPABILITY).orElse(null);
    }
}
