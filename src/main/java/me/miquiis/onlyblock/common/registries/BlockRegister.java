package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.blocks.AmazonTNTBlock;
import me.miquiis.onlyblock.common.blocks.BaseHorizontalBlock;
import me.miquiis.onlyblock.common.blocks.BuySignBlock;
import me.miquiis.onlyblock.common.blocks.MoneyPrinterBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
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

    public static final RegistryObject<Block> BUY_SIGN = registerBlock("sign", () ->
            new BuySignBlock(AbstractBlock.Properties.create(Material.WOOD).setNeedsPostProcessing(BlockRegister::needsPostProcessing).hardnessAndResistance(0.5f).sound(SoundType.WOOD))
    );

    public static final RegistryObject<Block> AMAZON_PACKAGE = registerBlock("amazon_package", () ->
            new BaseHorizontalBlock(AbstractBlock.Properties.create(Material.WOOD).hardnessAndResistance(0.5f).sound(SoundType.PLANT))
    );

    public static final RegistryObject<Block> ROAD_DIRECTION = registerBlock("road_direction", () ->
            new BaseHorizontalBlock(AbstractBlock.Properties.create(Material.ROCK).hardnessAndResistance(0.5f).sound(SoundType.STONE))
    );

    public static final RegistryObject<Block> AMAZON_PACKAGE_TNT = registerBlock("tnt_amazon_package", () ->
            new AmazonTNTBlock(AbstractBlock.Properties.create(Material.TNT).zeroHardnessAndResistance().sound(SoundType.PLANT))
    );

    public static final RegistryObject<Block> LAPTOP = registerBlock("laptop", () ->
            new BaseHorizontalBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.5f).sound(SoundType.GLASS).notSolid().setOpaque(BlockRegister::isntSolid).setSuffocates(BlockRegister::isntSolid).setBlocksVision(BlockRegister::isntSolid))
    );

    public static final RegistryObject<Block> SERVER_BLOCK = registerBlock("server_block", () ->
            new BaseHorizontalBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.5f).sound(SoundType.GLASS).notSolid().setOpaque(BlockRegister::isntSolid).setSuffocates(BlockRegister::isntSolid).setBlocksVision(BlockRegister::isntSolid))
    );

    public static final RegistryObject<Block> TERMINAL_PANEL = registerBlock("terminal_panel", () ->
            new BaseHorizontalBlock(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.5f).sound(SoundType.GLASS).notSolid().setOpaque(BlockRegister::isntSolid).setSuffocates(BlockRegister::isntSolid).setBlocksVision(BlockRegister::isntSolid))
    );

    public static final RegistryObject<Block> MONEY_PRINTER = registerBlock("money_printer", () ->
            new MoneyPrinterBlock(AbstractBlock.Properties.create(Material.ANVIL).hardnessAndResistance(3f).sound(SoundType.ANVIL).notSolid())
    );

    public static final RegistryObject<Block> GOLD_PILE = registerBlock("gold_pile", () ->
            new Block(AbstractBlock.Properties.create(Material.IRON).hardnessAndResistance(0.5f).sound(SoundType.ANVIL).notSolid())
    );

    public static final RegistryObject<Block> BIG_CASH_PILE = registerBlock("big_cash_pile", () ->
            new Block(AbstractBlock.Properties.create(Material.CAKE).hardnessAndResistance(0.5f).sound(SoundType.PLANT).notSolid())
    );

    public static final RegistryObject<Block> SMALL_CASH_PILE = registerBlock("small_cash_pile", () ->
            new Block(AbstractBlock.Properties.create(Material.CAKE).hardnessAndResistance(0.5f).sound(SoundType.PLANT).notSolid())
    );

    public static final RegistryObject<Block> CASH_PILE = registerBlock("cash_pile", () ->
            new Block(AbstractBlock.Properties.create(Material.CAKE).hardnessAndResistance(0.5f).sound(SoundType.PLANT).notSolid())
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
