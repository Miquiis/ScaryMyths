package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.blocks.ATMBlock;
import me.miquiis.onlyblock.common.blocks.BaseHorizontalBlock;
import me.miquiis.onlyblock.common.blocks.MoneyPrinterBlock;
import me.miquiis.onlyblock.common.blocks.SpeedBoostBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BlockRegister {

    public static final DeferredRegister<Block> BLOCKS
            = DeferredRegister.create(ForgeRegistries.BLOCKS, OnlyBlock.MOD_ID);

    public static final RegistryObject<Block> ATM = registerBlock("atm", () ->
            new ATMBlock(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(2f).sound(SoundType.ANVIL).notSolid().setOpaque(BlockRegister::isntSolid).setSuffocates(BlockRegister::isntSolid).setBlocksVision(BlockRegister::isntSolid))
    );

    public static final RegistryObject<Block> CASH_PILE = registerBlock("cash_pile", () ->
            new Block(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5f, 0.5f).sound(SoundType.PLANT).notSolid())
    );

    public static final RegistryObject<Block> MONEY_PRINTER = registerBlock("money_printer", () ->
            new MoneyPrinterBlock(AbstractBlock.Properties.create(Material.ANVIL).hardnessAndResistance(3f).sound(SoundType.ANVIL).notSolid())
    );

    public static final RegistryObject<Block> SPEED_BOOST = registerBlock("speed_boost", () ->
            new SpeedBoostBlock(AbstractBlock.Properties.create(Material.WOOL).hardnessAndResistance(0.2f).sound(SoundType.CLOTH).notSolid().setOpaque(BlockRegister::isntSolid).setBlocksVision(BlockRegister::isntSolid))
    );

    private static boolean needsPostProcessing(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    private static boolean isOpaque(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

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
