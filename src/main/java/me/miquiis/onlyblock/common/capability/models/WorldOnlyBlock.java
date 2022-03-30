package me.miquiis.onlyblock.common.capability.models;

import me.miquiis.onlyblock.common.capability.WorldOnlyBlockCapability;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyBlock;
import me.miquiis.onlyblock.common.classes.Wave;
import me.miquiis.onlyblock.common.utils.MathUtils;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.WorldOnlyBlockPacket;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.*;

public class WorldOnlyBlock implements IWorldOnlyBlock {

    private ServerWorld serverWorld;
    private World worldInstance;
    private int daysLeft;
    private Vector3d ironGenerator, goldGenerator, emeraldGenerator, diamondGenerator;
    private int ironPerDay, goldPerDay, diamondPerDay, emeraldPerDay;
    private boolean hasActivatedWaves;

    private static final List<Wave> waves = new ArrayList<>(Arrays.asList(
            new Wave(1, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(5, EntityType.ZOMBIE)
            ))),
            new Wave(10, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(10, EntityType.SPIDER)
            ))),
            new Wave(20, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(10, EntityType.DROWNED),
                    new Wave.WaveSpawner(10, EntityType.SPIDER)
            ))),
            new Wave(30, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(10, EntityType.CREEPER)
            ))),
            new Wave(40, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(10, EntityType.ZOMBIFIED_PIGLIN),
                    new Wave.WaveSpawner(5, EntityType.SKELETON)
            ))),
            new Wave(50, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(10, EntityType.DROWNED),
                    new Wave.WaveSpawner(5, EntityType.CREEPER),
                    new Wave.WaveSpawner(5, EntityType.ZOMBIE)
            ))),
            new Wave(60, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(15, EntityType.PILLAGER),
                    new Wave.WaveSpawner(10, EntityType.CREEPER),
                    new Wave.WaveSpawner(5, EntityType.WITCH)
            ))),
            new Wave(70, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(15, EntityType.ENDERMAN),
                    new Wave.WaveSpawner(10, EntityType.SKELETON)
            ))),
            new Wave(80, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(10, EntityType.ENDERMAN),
                    new Wave.WaveSpawner(10, EntityType.SKELETON),
                    new Wave.WaveSpawner(5, EntityType.VINDICATOR)
            ))),
            new Wave(90, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(1, EntityType.WITHER)
            ))),
            new Wave(100, new ArrayList<>(Arrays.asList(
                    new Wave.WaveSpawner(1, EntityType.ENDER_DRAGON)
            )))
    ));
    private List<LivingEntity> spawnedMobs;
    private boolean isWaveActive;
    private int lastDayWaveSpawned;
    private int lastWaveAmount;

    public WorldOnlyBlock()
    {
        this.ironGenerator = new Vector3d(1, 40, -70);
        this.goldGenerator = new Vector3d(61, 40, 39);
        this.emeraldGenerator = new Vector3d(-15, 40, 73);
        this.diamondGenerator = new Vector3d(-94, 40, 17);

        this.ironPerDay = 35;
        this.goldPerDay = 50;
        this.diamondPerDay = 30;
        this.emeraldPerDay = 25;

        this.spawnedMobs = new ArrayList<>();
        this.isWaveActive = false;
        this.hasActivatedWaves = false;
        this.lastDayWaveSpawned = -1;

        this.daysLeft = 100;
    }

    private void resetGenerators()
    {
        this.ironGenerator = new Vector3d(1, 40, -70);
        this.goldGenerator = new Vector3d(61, 40, 39);
        this.emeraldGenerator = new Vector3d(-15, 40, 73);
        this.diamondGenerator = new Vector3d(-94, 40, 17);

        this.ironPerDay = 35;
        this.goldPerDay = 50;
        this.diamondPerDay = 30;
        this.emeraldPerDay = 25;

        sync();
    }

    private CompoundNBT vectorToNBT(Vector3d vector3d)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putDouble("X", vector3d.getX());
        compoundNBT.putDouble("Y", vector3d.getY());
        compoundNBT.putDouble("Z", vector3d.getZ());
        return compoundNBT;
    }

    private Vector3d NBTtoVector(CompoundNBT compoundNBT)
    {
        return new Vector3d(compoundNBT.getDouble("X"), compoundNBT.getDouble("Y"), compoundNBT.getDouble("Z"));
    }

    @Override
    public void deserializeNBT(CompoundNBT data) {
        if (data.contains("DaysLeft"))
            daysLeft = data.getInt("DaysLeft");
        if (data.contains("IronGenerator"))
            ironGenerator = NBTtoVector(data.getCompound("IronGenerator"));
        if (data.contains("GoldGenerator"))
            goldGenerator = NBTtoVector(data.getCompound("GoldGenerator"));
        if (data.contains("DiamondGenerator"))
            diamondGenerator = NBTtoVector(data.getCompound("DiamondGenerator"));
        if (data.contains("EmeraldGenerator"))
            emeraldGenerator = NBTtoVector(data.getCompound("EmeraldGenerator"));
        if (data.contains("LastWaveSpawned"))
            lastDayWaveSpawned = data.getInt("LastWaveSpawned");
        resetGenerators();
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
    public void setWorld(World world) {
        this.worldInstance = world;
    }

    @Override
    public void setServerWorld(ServerWorld serverWorld) {
        this.serverWorld = serverWorld;
    }

    @Override
    public void setDaysLeft(int daysLeft) {
        int prevDays = this.daysLeft;
        this.daysLeft = daysLeft;
        int difference = prevDays - this.daysLeft;
        checkSpawnWave();

        if (difference > 0)
        {
            spawnIronAtGenerator((int) ((difference * ironPerDay) * 0.25), true);
            spawnGoldAtGenerator((int) ((difference * goldPerDay) * 0.25), true);
            spawnDiamondAtGenerator((int) ((difference * diamondPerDay) * 0.25), true);
            spawnEmeraldAtGenerator((int) ((difference * emeraldPerDay) * 0.25), true);
        }

        sync();
    }

    @Override
    public void skipDay() {
        this.daysLeft--;
        checkSpawnWave();
        sync();
    }

    @Override
    public void activateWaves() {
        this.hasActivatedWaves = true;
        checkSpawnWave();
    }

    @Override
    public void reset() {
        this.spawnedMobs = new ArrayList<>();
        this.isWaveActive = false;
        this.lastDayWaveSpawned = -1;
        this.hasActivatedWaves = false;
        this.daysLeft = 100;
    }

    @Override
    public Vector3d getIronGenerator() {
        return ironGenerator;
    }

    @Override
    public Vector3d getGoldGenerator() {
        return goldGenerator;
    }

    @Override
    public Vector3d getEmeraldGenerator() {
        return emeraldGenerator;
    }

    @Override
    public Vector3d getDiamondGenerator() {
        return diamondGenerator;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("DaysLeft", daysLeft);
        nbt.putInt("LastWaveSpawned", lastDayWaveSpawned);
        nbt.put("IronGenerator", vectorToNBT(ironGenerator));
        nbt.put("GoldGenerator", vectorToNBT(goldGenerator));
        nbt.put("DiamondGenerator", vectorToNBT(diamondGenerator));
        nbt.put("EmeraldGenerator", vectorToNBT(emeraldGenerator));
        return nbt;
    }

    @Override
    public int getDaysLeft() {
        return daysLeft;
    }

    @Override
    public int getCurrentDays() {
        return 100 - daysLeft;
    }

    @Override
    public int getIronDropsPerDay() {
        return ironPerDay;
    }

    @Override
    public int getGoldDropsPerDay() {
        return goldPerDay;
    }

    @Override
    public int getDiamondDropsPerDay() {
        return diamondPerDay;
    }

    @Override
    public int getEmeraldDropsPerDay() {
        return emeraldPerDay;
    }

    @Override
    public int getNextIronDropTime() {
        return (int) ((24000 / ironPerDay) - worldInstance.getDayTime() % (24000 / ironPerDay));
    }

    @Override
    public int getNextGoldDropTime() {
        return (int) ((24000 / goldPerDay) - worldInstance.getDayTime() % (24000 / goldPerDay));
    }

    @Override
    public int getNextDiamondDropTime() {
        return (int) ((24000 / diamondPerDay) - worldInstance.getDayTime() % (24000 / diamondPerDay));
    }

    @Override
    public int getNextEmeraldDropTime() {
        return (int) ((24000 / emeraldPerDay) - worldInstance.getDayTime() % (24000 / emeraldPerDay));
    }

    @Override
    public boolean hasActivatedWaves() {
        return hasActivatedWaves;
    }

    @Override
    public boolean hasWaveStarted() {
        return isWaveActive;
    }

    @Override
    public float getMobsPercentage() {
        return getMobsLeft() / (float)lastWaveAmount;
    }

    @Override
    public int getMobsLeft() {
        spawnedMobs.removeIf(livingEntity -> livingEntity == null || !livingEntity.isAlive() || livingEntity.removed);
        if (spawnedMobs.size() == 0 && isWaveActive) isWaveActive = false;
        return spawnedMobs.size();
    }

    private void checkSpawnWave()
    {
        if (!hasActivatedWaves) return;
        if (getMobsLeft() > 0) return;
        if (lastDayWaveSpawned == -1 || getCurrentDays() / 10 > lastDayWaveSpawned)
        {
            Wave waveToSpawn = getWaveByDays(getCurrentDays());
            if (waveToSpawn == null) return;
            lastWaveAmount = 0;
            waveToSpawn.getSpawners().forEach(waveSpawner -> {
                lastWaveAmount += waveSpawner.getAmount();
                for (int i = 0; i < waveSpawner.getAmount(); i++)
                {
                    Entity entity = waveSpawner.getEntityType().create(worldInstance);

                    if (MathUtils.chance(25)) {
                        if (MathUtils.chance(25)) {
                            entity.setPosition(ironGenerator.getX(), ironGenerator.getY(), ironGenerator.getZ());
                        } else {
                            entity.setPosition(goldGenerator.getX(), goldGenerator.getY(), goldGenerator.getZ());
                        }
                    } else {
                        if (MathUtils.chance(25)) {
                            entity.setPosition(diamondGenerator.getX(), diamondGenerator.getY(), diamondGenerator.getZ());
                        } else
                        {
                            entity.setPosition(emeraldGenerator.getX(), emeraldGenerator.getY(), emeraldGenerator.getZ());
                        }
                    }

                    if (entity instanceof LivingEntity)
                    {
                        MobEntity livingEntity = (MobEntity) entity;
                        livingEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(new BlockPos(0, 100, 0)), SpawnReason.MOB_SUMMONED, null, null);
                        livingEntity.enablePersistence();
                        livingEntity.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(9999);
                        livingEntity.setAttackTarget(serverWorld.getRandomPlayer());
                        livingEntity.setAggroed(true);
                        spawnedMobs.add(livingEntity);
                    }
                    if (entity instanceof EnderDragonEntity)
                    {
                        EnderDragonEntity enderDragonEntity = (EnderDragonEntity) entity;
                        enderDragonEntity.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
                    }
                    worldInstance.addEntity(entity);
                }
            });
            lastDayWaveSpawned = getCurrentDays() / 10;
            isWaveActive = true;
        } else
        {
            if (getMobsLeft() == 0)
            {
                isWaveActive = false;
            }
        }
    }

    private void spawnIronAtGenerator(int amount, boolean forceSpawn)
    {
        if (forceSpawn || getNextIronDropTime() == 1)
        {
            ItemEntity itemEntity = new ItemEntity(worldInstance, getIronGenerator().getX() + 0.5, getIronGenerator().getY() + 2, getIronGenerator().getZ() + 0.5);
            //itemEntity.setVelocity(0, 0, 0);
            itemEntity.setMotion(0, 0, 0);
            itemEntity.setItem(new ItemStack(Items.IRON_INGOT, amount));
            itemEntity.setNoDespawn();
            worldInstance.addEntity(itemEntity);
        }
    }

    private void spawnGoldAtGenerator(int amount, boolean forceSpawn)
    {
        if (forceSpawn || getNextGoldDropTime() == 1)
        {
            ItemEntity itemEntity = new ItemEntity(worldInstance, getGoldGenerator().getX() + 0.5, getGoldGenerator().getY() + 2, getGoldGenerator().getZ() + 0.5);
            //itemEntity.setVelocity(0, 0, 0);
            itemEntity.setMotion(0, 0, 0);
            itemEntity.setItem(new ItemStack(Items.GOLD_INGOT, amount));
            itemEntity.setNoDespawn();
            worldInstance.addEntity(itemEntity);
        }
    }

    private void spawnDiamondAtGenerator(int amount, boolean forceSpawn)
    {
        if (forceSpawn || getNextDiamondDropTime() == 1)
        {
            ItemEntity itemEntity = new ItemEntity(worldInstance, getDiamondGenerator().getX() + 0.5, getDiamondGenerator().getY() + 2, getDiamondGenerator().getZ() + 0.5);
            //itemEntity.setVelocity(0, 0, 0);
            itemEntity.setMotion(0, 0, 0);
            itemEntity.setItem(new ItemStack(Items.DIAMOND, amount));
            itemEntity.setNoDespawn();
            worldInstance.addEntity(itemEntity);
        }
    }

    private void spawnEmeraldAtGenerator(int amount, boolean forceSpawn)
    {
        if (forceSpawn || getNextEmeraldDropTime() == 1)
        {
            ItemEntity itemEntity = new ItemEntity(worldInstance, getEmeraldGenerator().getX() + 0.5, getEmeraldGenerator().getY() + 2, getEmeraldGenerator().getZ() + 0.5);
            //itemEntity.setVelocity(0, 0, 0);
            itemEntity.setMotion(0, 0, 0);
            itemEntity.setItem(new ItemStack(Items.EMERALD, amount));
            itemEntity.setNoDespawn();
            worldInstance.addEntity(itemEntity);
        }
    }

    private Wave getWaveByDays(int days)
    {
        return waves.stream().filter(wave -> wave.getMinDayNeeded() <= days).max(Comparator.comparingInt(Wave::getMinDayNeeded)).orElse(null);
    }

    public static IWorldOnlyBlock getCapability(World world)
    {
        return world.getCapability(WorldOnlyBlockCapability.CURRENT_CAPABILITY).orElse(null);
    }
}
