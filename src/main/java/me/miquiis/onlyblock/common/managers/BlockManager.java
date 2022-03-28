package me.miquiis.onlyblock.common.managers;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.classes.LootTable;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
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

        ICurrency currency = player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);

        if (player.getHeldItemMainhand().getItem() == ItemRegister.CRYPTO_PICKAXE.get()) /** Change to pickaxe **/ {
            currency.addOrSubtractAmount(2);
        } else if (player.getHeldItemMainhand().getItem() == ItemRegister.DEBIT_CARD_AXE.get()) /** Change to Axe **/ {
            currency.addOrSubtractAmount(5);
        } else {
            currency.addOrSubtractAmount(1);
        }
    }

    private LootTable.Loot getLootFromPlayer(ServerPlayerEntity player)
    {
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
                new LootTable.Loot(ItemRegister.CASH.get().getRegistryName().toString(), 1),
                new LootTable.Loot(ItemRegister.CASH.get().getRegistryName().toString(), 2),
                new LootTable.Loot(ItemRegister.CASH.get().getRegistryName().toString(), 5)
        )));

        LootTable level2 = new LootTable("level_2", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(Items.OAK_LOG.getRegistryName().toString(), 1)
        )), level1);

        folderReference.saveObject("level_1", level1);
        folderReference.saveObject("level_2", level2);
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
