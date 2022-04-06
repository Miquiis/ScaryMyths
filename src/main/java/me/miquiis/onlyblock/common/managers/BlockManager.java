package me.miquiis.onlyblock.common.managers;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.classes.LootTable;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public void onBlockBreak(BlockEvent.BreakEvent event)
    {
        dropItems((ServerPlayerEntity)event.getPlayer(), (ServerWorld)event.getWorld(), event.getPos());
    }

    private void dropItems(ServerPlayerEntity player, ServerWorld world, BlockPos blockPos)
    {
        LootTable.Loot loot = getLootFromPlayer(player);

        if (loot != null)
        {
            ItemStack toDrop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(loot.getLootResource())), loot.getLootAmount());
            ItemEntity itemEntity = new ItemEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 1.1, blockPos.getZ() + 0.5, toDrop);
            itemEntity.setDefaultPickupDelay();
            world.addEntity(itemEntity);
        }
    }

    private LootTable.Loot getLootFromPlayer(ServerPlayerEntity player)
    {
        if (player.getHeldItemMainhand().getItem() == ItemRegister.CRYPTO_MINER.get())
        {
            OnlyMoneyBlock.getCapability(player).sumCash(100);
        } else
        {
            OnlyMoneyBlock.getCapability(player).sumCash(1);
        }

        if (!OnlyMoneyBlock.getCapability(player).hasATM() && OnlyMoneyBlock.getCapability(player).getCash() >= 50)
        {
            OnlyMoneyBlock.getCapability(player).setHasATM(true);
            return getLootTable("level_1_1").getLoot();
        }

        return getLootTable("level_1").getLoot();
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
        LootTable level1 = new LootTable("level_1", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(Items.COBBLESTONE.getRegistryName().toString(), 6),
                new LootTable.Loot(Items.OAK_LOG.getRegistryName().toString(), 4),
                new LootTable.Loot(Items.OAK_PLANKS.getRegistryName().toString(), 10),
                new LootTable.Loot(Items.STONE.getRegistryName().toString(), 3),
                new LootTable.Loot(Items.BRICKS.getRegistryName().toString(), 5)
        )));

        LootTable level1_1 = new LootTable("level_1_1", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(BlockRegister.ATM.get().getRegistryName().toString(), 1)
        )));

        folderReference.saveObject("level_1", level1);
        folderReference.saveObject("level_1_1", level1_1);
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
