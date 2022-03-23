package me.miquiis.onlyblock.common.classes;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import me.miquiis.onlyblock.common.entities.AsteroidEntity;
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

import java.util.ArrayList;
import java.util.List;

public class BillionaireIsland implements IUnlockable {

    private final Vector3d EARTH_CENTER = new Vector3d(-1000, 220, -1000);
    private boolean isLocked;

    private boolean hasMinigameStarted;
    private int currentWave;
    private int earthHeath;
    private long currentTime;
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
        if (currentTime % (20 * 45) == 0)
        {
            TitleUtils.sendTitleToPlayer(player, "&cWAVE INCOMING", "&fMore meteors are on the way", 20, 60, 20);
            spawnWave(player);
        } else if (currentWave == 0)
        {
            TitleUtils.sendTitleToPlayer(player, "&cWAVE STARTING", "&fMeteors are coming towards Earth", 20, 60, 20);
            spawnWave(player);
        }
    }

    public float getEarthPercentage() {
        return earthHeath / 100f;
    }

    public void spawnWave(PlayerEntity player)
    {
        int randomAmount = MathUtils.getRandomMax(10) + MathUtils.getRandomMax((3 * currentWave) + 1);
        for (int i = 0; i < randomAmount; i++)
        {
            final double angle = MathUtils.getRandomMinMax(0, 360 * 5);
            final double radius = 500;
            final float pitch = MathUtils.getRandomMinMax(-45, 10);

            Vector3d lookVec = getVectorForRotation(pitch, 0, (float)angle);
            double x = radius * lookVec.getX();
            double z = radius * lookVec.getZ();
            double y = radius * lookVec.getY();

            Vector3d vec = new Vector3d(player.getPosX() + x, (player.getPosY() + 2f) + y, player.getPosZ() + z);;

            AsteroidEntity asteroidEntity = new AsteroidEntity(player.world, EARTH_CENTER);
            asteroidEntity.setPosition(vec.getX(), vec.getY(), vec.getZ());
            asteroidEntity.setOwner(player.getUniqueID());
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
    }

    public CompoundNBT serializeNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putBoolean("IsLocked", isLocked);
        compoundNBT.putInt("CurrentWave", currentWave);
        compoundNBT.putLong("CurrentTime", currentTime);
        compoundNBT.putBoolean("MinigameStarted", hasMinigameStarted);
        compoundNBT.putInt("EarthHealth", earthHeath);
        return compoundNBT;
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
    }

    @Override
    public void lock(PlayerEntity player) {
        isLocked = true;
        WorldEditUtils.pasteSchematic("b_rocket_no_entity", player.world, 1.51, 65.00, 130.47);
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
}
