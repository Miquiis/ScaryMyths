package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.common.utils.MathUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LootTable {

    public static class Loot {

        private String lootResource;
        private int lootAmount;

        public Loot(String lootResource, int lootAmount)
        {
            this.lootResource = lootResource;
            this.lootAmount = lootAmount;
        }

        public String getLootResource() {
            return lootResource;
        }

        public int getLootAmount() {
            return lootAmount;
        }
    }

    private String lootKey;

    private List<Loot> lootTable;

    public LootTable(String lootKey, List<Loot> lootTable)
    {
        this.lootKey = lootKey;
        this.lootTable = lootTable;
    }

    public LootTable(String lootKey, List<Loot> lootTable, LootTable... copy)
    {
        this.lootKey = lootKey;
        this.lootTable = lootTable;

        for (LootTable table : copy)
        {
            lootTable.addAll(table.getLoots());
        }

    }

    public String getLootKey() {
        return lootKey;
    }

    public List<Loot> getLootTable() {
        return lootTable;
    }

    public Loot getLoot()
    {
        return lootTable.get(MathUtils.getRandomMax(lootTable.size()));
    }

    public Collection<Loot> getLoots() { return lootTable; }

    public List<Loot> getLoot(int roll)
    {
        List<Loot> rolls = new ArrayList<>();
        for (int i = 0; i < roll; i++)
        {
            rolls.add(getLoot());
        }
        return rolls;
    }
}
