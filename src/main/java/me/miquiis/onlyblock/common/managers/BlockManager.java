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

    //private static final UUID BAR_UUID = UUID.fromString("5c429c2f-fc87-49b8-a438-90c87cf6b676");

    private OnlyBlock mod;
    private FileManager folderReference;
    private Set<LootTable> cachedLootTables;
    private Map<UUID, Integer> clickCheck;
    private float globalMultiplier;

    public BlockManager(OnlyBlock mod)
    {
        this.mod = mod;
        this.folderReference = mod.getOnlyBlockFolder();
        this.cachedLootTables = new HashSet<>();
        this.clickCheck = new HashMap<>();
        this.globalMultiplier = 1f;
        setupFolder();
        setupBar();
    }

    public void onXPInteractEvent(PlayerInteractEvent event)
    {
        final BlockState state = event.getWorld().getBlockState(event.getPos());
        if (!clickCheck.containsKey(event.getPlayer().getUniqueID()))
        {
            clickCheck.put(event.getPlayer().getUniqueID(), 2);
            return;
        }

        clickCheck.computeIfPresent(event.getPlayer().getUniqueID(), (uuid, integer) -> {
           if (integer == 2)
           {
               dropItems((ServerPlayerEntity)event.getPlayer(), (ServerWorld)event.getWorld(), event.getPos(),  state.getBlock() == BlockRegister.ENERGY_XP_BLOCK.get());
               return 1;
           }
           else
           {
               return 2;
           }
        });
    }

    private void dropItems(ServerPlayerEntity player, ServerWorld world, BlockPos blockPos, boolean isEnergy)
    {
        LootTable.Loot loot = getLootFromLevel(player.experienceLevel);
        if (loot != null)
        {
            ItemStack toDrop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(loot.getLootResource())), loot.getLootAmount());
            ItemEntity itemEntity = new ItemEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 1.1, blockPos.getZ() + 0.5, toDrop);
            itemEntity.setDefaultPickupDelay();
            world.addEntity(itemEntity);
        }

        ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, blockPos.getX() + 0.5, blockPos.getY() + 1.1, blockPos.getZ() + 0.5, isEnergy ? 5 : 1);
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

    public void onLavaBlockBreak(BlockEvent.BreakEvent event)
    {
        if (event.getPlayer().isCreative()) return;
        event.setCanceled(true);

        double d0 = (double)(event.getPlayer().world.rand.nextFloat() * 0.5F) + 0.25D;
        double d1 = (double)(event.getPlayer().world.rand.nextFloat() * 0.5F) + 0.5D;
        double d2 = (double)(event.getPlayer().world.rand.nextFloat() * 0.5F) + 0.25D;

        ItemStack toDrop;
        if (event.getPlayer().getHeldItemMainhand().getItem() == Items.AIR)
        {
            LootTable.Loot loot = getLootTable("bare_hands").getLoot();
            toDrop = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(loot.getLootResource())), loot.getLootAmount());
        } else
        {
            return;
        }

        ItemEntity itemEntity = new ItemEntity(event.getPlayer().world, event.getPos().getX() + d0, event.getPos().getY() + d1, event.getPos().getZ() + d2, toDrop);
        itemEntity.setDefaultPickupDelay();
        event.getWorld().addEntity(itemEntity);
    }

    private void setupBar()
    {
//        BarManager.addBar(
//                UUID.randomUUID(),
//                new StringTextComponent("Lava Progress"),
//                0f,
//                new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/lava_bar.png"),
//                new int[]{240, 89, 34},
//                false
//        );
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
        LootTable level3 = new LootTable("level_3", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(BlockRegister.ENCHANTED_DIRT.get().getRegistryName().toString(), 1)
        )));

        LootTable level7 = new LootTable("level_7", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(BlockRegister.ENCHANTED_OAK_PLANKS.get().getRegistryName().toString(), 1)
        )), level3);

        LootTable level10 = new LootTable("level_10", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(ItemRegister.XP_INGOT.get().getRegistryName().toString(), 1),
                new LootTable.Loot(ItemRegister.XP_INGOT.get().getRegistryName().toString(), 2)
        )), level7);

        LootTable level12 = new LootTable("level_12", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(BlockRegister.ENCHANTED_COBBLESTONE.get().getRegistryName().toString(), 1)
        )), level10);

        LootTable level20 = new LootTable("level_20", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(ItemRegister.XP_COW_EGG.get().getRegistryName().toString(), 1),
                new LootTable.Loot(ItemRegister.XP_SHEEP_EGG.get().getRegistryName().toString(), 1),
                new LootTable.Loot(ItemRegister.XP_CHICKEN_EGG.get().getRegistryName().toString(), 1)
        )), level12);

        LootTable level25 = new LootTable("level_25", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(BlockRegister.XP_BLOCK.get().getRegistryName().toString(), 1)
        )), level20);

        LootTable level30 = new LootTable("level_30", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(Items.SAND.toString(), 1),
                new LootTable.Loot(Items.SAND.toString(), 2)
        )), level25);

        LootTable level60 = new LootTable("level_60", new ArrayList<>(Arrays.asList(
                new LootTable.Loot(BlockRegister.ENERGY_XP_BLOCK.get().getRegistryName().toString(), 1)
        )), level30);

        folderReference.saveObject("level_3", level3);
        folderReference.saveObject("level_7", level7);
        folderReference.saveObject("level_10", level10);
        folderReference.saveObject("level_12", level12);
        folderReference.saveObject("level_20", level20);
        folderReference.saveObject("level_25", level25);
        folderReference.saveObject("level_30", level30);
        folderReference.saveObject("level_60", level60);
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
