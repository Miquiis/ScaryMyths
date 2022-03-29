package me.miquiis.onlyblock.common.classes;

import net.minecraft.entity.EntityType;

import java.util.List;

public class Wave {

    public static class WaveSpawner {
        private int amount;
        private EntityType entityType;

        public WaveSpawner(int amount, EntityType entityType)
        {
            this.amount = amount;
            this.entityType = entityType;
        }

        public int getAmount() {
            return amount;
        }

        public EntityType getEntityType() {
            return entityType;
        }
    }

    private int minDayNeeded;
    private List<WaveSpawner> spawners;

    public Wave(int minDayNeeded, List<WaveSpawner> spawners)
    {
        this.minDayNeeded = minDayNeeded;
        this.spawners = spawners;
    }

    public int getMinDayNeeded() {
        return minDayNeeded;
    }

    public List<WaveSpawner> getSpawners() {
        return spawners;
    }
}
