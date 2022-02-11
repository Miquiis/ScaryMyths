package me.miquiis.onlyblock.common.blocks;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.block.Block;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class CustomBlockTags {
    public static final ITag<Block> ENCHANTED = BlockTags.getCollection().get(new ResourceLocation(OnlyBlock.MOD_ID, "enchanted"));
}
