package me.miquiis.onlyblock.common.classes;

import me.miquiis.onlyblock.common.entities.*;
import me.miquiis.onlyblock.common.utils.MathUtils;
import me.miquiis.onlyblock.common.utils.WorldEditUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StockIsland implements IUnlockable {

    private static final List<Vector3d> GHAST_SPAWNS = new ArrayList<>(Arrays.asList(
            new Vector3d(-6, 105, -130),
            new Vector3d(20, 102, -153),
            new Vector3d(46, 93, -181),
            new Vector3d(-6, 96, -202),
            new Vector3d(-60, 102, -196),
            new Vector3d(-61, 93, -142),
            new Vector3d(-40, 87, -111),
            new Vector3d(51, 105, -103),
            new Vector3d(68, 120, -142),
            new Vector3d(55, 69, -169)
    ));

    private boolean isLocked;
    private boolean isAcquired;
    private boolean firstHit;
    private boolean pacificToggle;
    private List<Entity> spawnedGhasts;

    public StockIsland()
    {
        this.isLocked = true;
        this.firstHit = true;
        this.isAcquired = false;
        pacificToggle = false;
    }

    public void startMinigame(World world)
    {
        spawnGhasts(world);
        spawnBuffett(world);
        firstHit = true;
        pacificToggle = false;
    }

    private void spawnBuffett(World world) {
        BuffetEntity buffetEntity = new BuffetEntity(world);
        buffetEntity.setPositionAndRotation(60, 184, -169, 90, 0);
        buffetEntity.enablePersistence();
        world.addEntity(buffetEntity);
    }

    public void setAcquired(boolean acquired) {
        isAcquired = acquired;
    }

    public boolean isAcquired() {
        return isAcquired;
    }

    private void spawnGhasts(World world)
    {
        if (spawnedGhasts != null) spawnedGhasts.forEach(Entity::remove);
        spawnedGhasts = new ArrayList<>();
        GHAST_SPAWNS.forEach(vector3d -> {
            StockGhastEntity stockGhastEntity = new StockGhastEntity(world);
            stockGhastEntity.setPosition(vector3d.getX(), vector3d.getY(), vector3d.getZ());
            stockGhastEntity.enablePersistence();
            stockGhastEntity.onInitialSpawn((ServerWorld)world, world.getDifficultyForLocation(new BlockPos(vector3d)), SpawnReason.MOB_SUMMONED, null, null);
            world.addEntity(stockGhastEntity);
            spawnedGhasts.add(stockGhastEntity);
        });
    }

    public void deserializeNBT(CompoundNBT compoundNBT)
    {
        if (compoundNBT.contains("IsLocked"))
        {
            isLocked = compoundNBT.getBoolean("IsLocked");
        } else
        {
            isLocked = true;
        }
        if (compoundNBT.contains("IsFirstHit"))
        {
            firstHit = compoundNBT.getBoolean("IsFirstHit");
        } else {
            firstHit = true;
        }
        if (compoundNBT.contains("PacificToggle"))
        {
            pacificToggle = compoundNBT.getBoolean("IsFirstHit");
        } else {
            pacificToggle = false;
        }
        if (compoundNBT.contains("IsAcquired"))
        {
            isAcquired = compoundNBT.getBoolean("IsAcquired");
        } else {
            isAcquired = false;
        }
    }

    public CompoundNBT serializeNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putBoolean("IsLocked", isLocked);
        compoundNBT.putBoolean("IsFirstHit", firstHit);
        compoundNBT.putBoolean("PacificToggle", pacificToggle);
        compoundNBT.putBoolean("IsAcquired", isAcquired);
        return compoundNBT;
    }

    public boolean isFirstHit() {
        return firstHit;
    }

    public boolean isPacificToggle() {
        return pacificToggle;
    }

    public void hit()
    {
        this.firstHit = false;
    }

    public void togglePacific() {
        pacificToggle = !pacificToggle;
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public void unlock(PlayerEntity player) {
        isLocked = false;
        WorldEditUtils.pasteSchematic("stock_no_entity", player.world, -7.51, 66, -122.44);
        startMinigame(player.world);
    }

    @Override
    public void lock(PlayerEntity player) {
        isLocked = true;
        WorldEditUtils.pasteSchematic("b_stock_no_entity", player.world, -7.51, 66, -122.44);
    }

}
