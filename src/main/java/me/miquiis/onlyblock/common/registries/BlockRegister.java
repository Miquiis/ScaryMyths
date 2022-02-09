package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.blocks.CobblestoneCraftingTableBlock;
import me.miquiis.onlyblock.common.blocks.EnchantedSaplingBlock;
import me.miquiis.onlyblock.common.blocks.LavaBlock;
import me.miquiis.onlyblock.common.blocks.XPBlock;
import me.miquiis.onlyblock.common.items.EnchantedBlockItem;
import me.miquiis.onlyblock.common.items.EnchantedGrowBlockItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.DarkOakTree;
import net.minecraft.block.trees.OakTree;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockRegister {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, OnlyBlock.MOD_ID);


    public static final RegistryObject<Block> LAVA_BLOCK = registerBlock("lava_block", () ->
            new LavaBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(2f).setLightLevel((state) -> 15))
    );

    public static final RegistryObject<Block> ENCHANTED_COBBLESTONE = registerEnchantedBlock("enchanted_cobblestone", () ->
            new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool().hardnessAndResistance(2.0F, 6.0F)),
            10, true
    );

    public static final RegistryObject<Block> ENCHANTED_OAK_SAPLING = registerEnchantedBlock("enchanted_oak_sapling", () ->
            new EnchantedSaplingBlock(new OakTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().zeroHardnessAndResistance().sound(SoundType.PLANT))
    );

    public static final RegistryObject<Block> ENCHANTED_OAK_PLANKS = registerEnchantedBlock("enchanted_oak_planks", () ->
            new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)),
            2, false
    );

    public static final RegistryObject<Block> XP_BLOCK = registerBlock("xp_block", () ->
            new XPBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(2f).sound(SoundType.GLASS).setLightLevel((state) -> 15))
    );

    public static final RegistryObject<Block> LAVA_PLANKS = registerBlock("lava_planks", () ->
            new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.2F, 3.2F).sound(SoundType.WOOD))
    );

    public static final RegistryObject<Block> COBBLESTONE_CRAFTING_TABLE = registerBlock("cobblestone_crafting_table", () ->
            new CobblestoneCraftingTableBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).sound(SoundType.STONE))
    );

    private static<T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static<T extends Block> RegistryObject<T> registerEnchantedBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerEnchantedBlockItem(name, toReturn);
        return toReturn;
    }

    private static<T extends Block> RegistryObject<T> registerEnchantedBlock(String name, Supplier<T> block, int radius, boolean sphere) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerEnchantedBlockItem(name, toReturn, radius, sphere);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ItemRegister.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ItemGroup.MISC))
        );
    }

    private static <T extends Block> void registerEnchantedBlockItem(String name, RegistryObject<T> block, int radius, boolean sphere) {
        ItemRegister.ITEMS.register(name, () -> new EnchantedGrowBlockItem(block.get(),
                new Item.Properties().group(ItemGroup.MISC), radius, sphere)
        );
    }

    private static <T extends Block> void registerEnchantedBlockItem(String name, RegistryObject<T> block) {
        ItemRegister.ITEMS.register(name, () -> new EnchantedBlockItem(block.get(),
                new Item.Properties().group(ItemGroup.MISC))
        );
    }

    public static void register(IEventBus bus)
    {
        BLOCKS.register(bus);
    }

}
