package me.miquiis.onlyblock.common.managers;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.classes.LootTable;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class BlockManager {

    private OnlyBlock mod;
    private FileManager folderReference;
    private Set<LootTable> cachedLootTables;
    private float globalMultiplier;

    public BlockManager(OnlyBlock mod)
    {
        this.mod = mod;
        this.folderReference = mod.getOnlyBlockFolder();
        this.cachedLootTables = new HashSet<>();
        this.globalMultiplier = 1f;
        setupFolder();
    }

    public void onBlockBreak(PlayerInteractEvent event)
    {
        final BlockState state = event.getWorld().getBlockState(event.getPos());
        dropItems((ServerPlayerEntity)event.getPlayer(), (ServerWorld)event.getWorld(), event.getPos());
    }

    private void dropItems(ServerPlayerEntity player, ServerWorld world, BlockPos blockPos)
    {
        LootTable.Loot loot = getLootFromLevel(player.experienceLevel);
        if (loot != null)
        {
            ItemStack toDrop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(loot.getLootResource())), loot.getLootAmount());
            ItemEntity itemEntity = new ItemEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 1.1, blockPos.getZ() + 0.5, toDrop);
            itemEntity.setDefaultPickupDelay();
            world.addEntity(itemEntity);
        }

        ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 1.1, blockPos.getZ() + 0.5, 1);
        world.addEntity(experienceOrbEntity);
    }

    private LootTable.Loot getLootFromLevel(int level)
    {
        if (level < 7) {
            if (MathUtils.chance(95)) return null;
            return getLootTable("level_3").getLoot();
        } else if (level < 10) {
            if (MathUtils.chance(95)) return null;
            return getLootTable("level_7").getLoot();
        } else if (level < 12) {
            if (MathUtils.chance(95)) return null;
            return getLootTable("level_10").getLoot();
        } else if (level < 20){
            if (MathUtils.chance(93)) return null;
            return getLootTable("level_12").getLoot();
        } else if (level < 25) {
            if (MathUtils.chance(91)) return null;
            return getLootTable("level_20").getLoot();
        } else if (level < 30) {
            if (MathUtils.chance(90)) return null;
            return getLootTable("level_25").getLoot();
        } else if (level < 60){
            if (MathUtils.chance(88)) return null;
            return getLootTable("level_30").getLoot();
        } else {
            if (MathUtils.chance(85)) return null;
            return getLootTable("level_60").getLoot();
        }
    }

    private void setupFolder()
    {
        if (folderReference.isFirstTime())
        {
            setupDefaultLootTable();
            folderReference.saveObject("default", new LootTable("default", new ArrayList<>(Arrays.asList(new LootTable.Loot("minecraft:stone", 1)))));
            return;
        }
    }

    private void setupDefaultLootTable()
    {
//        LootTable level3 = new LootTable("level_3", new ArrayList<>(Arrays.asList(
//                new LootTable.Loot(BlockRegister.ENCHANTED_DIRT.get().getRegistryName().toString(), 1)
//        )));
//
//        folderReference.saveObject("level_3", level3);
    }

    public LootTable getLootTable(String lootKey) {
        LootTable cachedLootTable = cachedLootTables.stream().filter(lootTable -> lootTable.getLootKey().equals(lootKey)).findFirst().orElse(null);
        if (cachedLootTable == null)
        {
            LootTable savedLootTable = folderReference.loadObject(lootKey, LootTable.class, false);
            if (savedLootTable == null) return null;
            cachedLootTables.add(savedLootTable);
            return savedLootTable;
        }
        return cachedLootTable;
    }

    public float getGlobalMultiplier() {
        return globalMultiplier;
    }

    public void setGlobalMultiplier(float mult)
    {
        this.globalMultiplier = mult;
    }
}
