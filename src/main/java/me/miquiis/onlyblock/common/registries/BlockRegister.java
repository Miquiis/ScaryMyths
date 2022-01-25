package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.blocks.CobblestoneCraftingTableBlock;
import me.miquiis.onlyblock.common.blocks.LavaBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockRegister {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, OnlyBlock.MOD_ID);


    public static final RegistryObject<Block> LAVA_BLOCK = registerBlock("lava_block", () ->
            new LavaBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(2f).setLightLevel((state) -> 15))
    );

    public static final RegistryObject<Block> COBBLESTONE_CRAFTING_TABLE = registerBlock("cobblestone_crafting_table", () ->
            new CobblestoneCraftingTableBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).sound(SoundType.STONE))
    );

    private static<T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);

        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ItemRegister.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ItemGroup.MISC))
        );
    }

    public static void register(IEventBus bus)
    {
        BLOCKS.register(bus);
    }

}
