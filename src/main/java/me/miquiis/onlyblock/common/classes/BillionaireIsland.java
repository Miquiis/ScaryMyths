package me.miquiis.onlyblock.common.classes;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import me.miquiis.onlyblock.common.entities.AsteroidEntity;
import me.miquiis.onlyblock.common.entities.ElonMuskEntity;
import me.miquiis.onlyblock.common.entities.JeffBezosEntity;
import me.miquiis.onlyblock.common.utils.MathUtils;
import me.miquiis.onlyblock.common.utils.TitleUtils;
import me.miquiis.onlyblock.common.utils.WorldEditUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BillionaireIsland implements IUnlockable {

    private final Vector3d EARTH_CENTER = new Vector3d(-1000, 220, -1000);
    private boolean isLocked;

    private boolean hasMinigameStarted;
    private int currentWave;
    private int earthHeath;
    private long currentTime;
    private UUID elonSpawned;
    private List<Entity> spawnedMeteors;

    public BillionaireIsland()
    {
        this.isLocked = true;
        this.currentTime = 0;
        this.currentWave = 0;
        this.earthHeath = 100;
        this.hasMinigameStarted = false;
        spawnedMeteors = new ArrayList<>();
    }

    public void startMinigame(PlayerEntity player)
    {
        this.currentWave = 0;
        this.currentTime = 0;
        this.earthHeath = 100;
        this.isLocked = true;
        this.hasMinigameStarted = true;
    }

    public void tickTime(ServerPlayerEntity player) {
        currentTime++;
        if (currentTime % (20 * 15) == 0 && currentWave < 2 && getAliveMeteors() == 0)
        {
            System.out.println("Wave Incoming");
            TitleUtils.sendTitleToPlayer(player, "&c&lWAVE INCOMING", "&fMore meteors are on the way", 20, 60, 20);
            spawnWave(player);
        } else if (currentTime == 20)
        {
            System.out.println("Wave Starting");
            TitleUtils.sendTitleToPlayer(player, "&c&lWAVE STARTING", "&fMeteors are coming towards Earth", 20, 60, 20);
            spawnWave(player);
        }

        if (currentTime % (20 * 15) == 0 && currentWave >= 2 && getAliveMeteors() == 0) {
            player.stopRiding();
            player.setPositionAndUpdate(13, 66, 137);
            hasMinigameStarted = false;
            return;
        }
    }

    public float getEarthPercentage() {
        return earthHeath / 100f;
    }

    public void spawnWave(PlayerEntity player)
    {
        spawnedMeteors.forEach(entity -> {
            System.out.println(entity.getPositionVec());
        });
        spawnedMeteors.removeIf(entity -> entity == null || !entity.isAlive() || entity.removed || entity.getPositionVec().distanceTo(EARTH_CENTER) > 200);
        if (currentWave == 3) return;
        int randomAmount = currentWave == 0 ? 3 : currentWave == 1 ? 5 : currentWave == 2 ? 10 : 3;
        for (int i = 0; i < randomAmount; i++)
        {
            final double angle = MathUtils.getRandomMinMax(0, 360 * 5);
            final double radius = 300;
            final float pitch = MathUtils.getRandomMinMax(-45, 10);

            Vector3d lookVec = getVectorForRotation(pitch, 0, (float)angle);
            double x = radius * lookVec.getX();
            double z = radius * lookVec.getZ();
            double y = radius * lookVec.getY();

            Vector3d vec = new Vector3d(EARTH_CENTER.getX() + x, (EARTH_CENTER.getY() + 2f) + y, EARTH_CENTER.getZ() + z);;

            AsteroidEntity asteroidEntity = new AsteroidEntity(player.world, EARTH_CENTER);
            asteroidEntity.setPosition(vec.getX(), vec.getY(), vec.getZ());
            asteroidEntity.setOwner(player.getUniqueID());
            asteroidEntity.setGlowing(true);
            asteroidEntity.enablePersistence();

            spawnedMeteors.add(asteroidEntity);

            player.world.addEntity(asteroidEntity);
        }
        currentWave++;
    }

    public void deserializeNBT(CompoundNBT compoundNBT)
    {
        if (compoundNBT.contains("IsLocked"))
        {
            isLocked = compoundNBT.getBoolean("IsLocked");
        }
        if (compoundNBT.contains("CurrentWave"))
        {
            currentWave = compoundNBT.getInt("CurrentWave");
        } else currentWave = 0;
        if (compoundNBT.contains("CurrentTime"))
        {
            currentTime = compoundNBT.getLong("CurrentTime");
        } else currentTime = 0;
        if (compoundNBT.contains("MinigameStarted"))
        {
            hasMinigameStarted = compoundNBT.getBoolean("MinigameStarted");
        } else hasMinigameStarted = false;
        if (compoundNBT.contains("EarthHealth"))
        {
            earthHeath = compoundNBT.getInt("EarthHealth");
        } else {
            System.out.println("Earth Reset");
            earthHeath = 100;
        }
        if (compoundNBT.contains("ElonSpawned"))
        elonSpawned = compoundNBT.getUniqueId("ElonSpawned");
    }

    public CompoundNBT serializeNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putBoolean("IsLocked", isLocked);
        compoundNBT.putInt("CurrentWave", currentWave);
        compoundNBT.putLong("CurrentTime", currentTime);
        compoundNBT.putBoolean("MinigameStarted", hasMinigameStarted);
        compoundNBT.putInt("EarthHealth", earthHeath);
        if (elonSpawned != null)
        compoundNBT.putUniqueId("ElonSpawned", elonSpawned);
        return compoundNBT;
    }

    public int getAliveMeteors()
    {
        return (int)spawnedMeteors.stream().filter(entity -> entity != null && entity.isAlive() && !entity.removed).peek(System.out::println).count();
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    public boolean hasMinigameStarted() {
        return hasMinigameStarted;
    }

    @Override
    public void unlock(PlayerEntity player) {
        isLocked = false;
        WorldEditUtils.pasteSchematic("rocket_no_entity", player.world, 1.51, 65.00, 130.47);
        spawnElon(player.world);
    }

    @Override
    public void lock(PlayerEntity player) {
        isLocked = true;
        WorldEditUtils.pasteSchematic("b_rocket_no_entity", player.world, 1.51, 65.00, 130.47);
        if (elonSpawned != null)
        ((ServerWorld)player.world).getEntityByUuid(elonSpawned).remove();
    }

    private void spawnElon(World world) {
        ElonMuskEntity entity = new ElonMuskEntity(world);
        entity.setPositionAndRotation(11.5, 66, 139.5, 180, 0);
        entity.enablePersistence();
        elonSpawned = entity.getUniqueID();
        world.addEntity(entity);
    }

    private final Vector3d getVectorForRotation(float pitch, float yaw, float angle) {
        float f = (pitch) * ((float)Math.PI / 180F);
        float f1 = (-yaw + angle) * ((float)Math.PI / 180F);
        float f2 = MathHelper.cos(f1);
        float f3 = MathHelper.sin(f1);
        float f4 = MathHelper.cos(f);
        float f5 = MathHelper.sin(f);
        return new Vector3d((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }

    public void damageEarth() {
        earthHeath -= 10;
    }

    public float getMeteorsPercentage() {
        return getAliveMeteors() / (float)spawnedMeteors.size();
    }
}
