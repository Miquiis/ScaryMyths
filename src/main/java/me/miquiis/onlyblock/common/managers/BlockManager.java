package me.miquiis.onlyblock.common.managers;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.models.LootTable;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

public class BlockManager {

    //private static final UUID BAR_UUID = UUID.fromString("5c429c2f-fc87-49b8-a438-90c87cf6b676");

    private OnlyBlock mod;
    private FileManager folderReference;

    private Set<LootTable> cachedLootTables;

    public BlockManager(OnlyBlock mod)
    {
        this.mod = mod;
        this.folderReference = mod.getOnlyBlockFolder();
        this.cachedLootTables = new HashSet<>();
        setupFolder();
        setupBar();
    }

    public void onXPInteractEvent(PlayerInteractEvent event)
    {
        ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(event.getPlayer().world, event.getPos().getX() + 0.5, event.getPos().getY() + 1.1, event.getPos().getZ() + 0.5, event.getWorld().rand.nextInt(50));
        event.getWorld().addEntity(experienceOrbEntity);
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
            folderReference.saveObject("default", new LootTable("default", new ArrayList<>(Arrays.asList(new LootTable.Loot("minecraft:stone", 1)))));
            return;
        }
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

}
