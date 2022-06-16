package me.miquiis.scarymyths.common.blocks;

import me.miquiis.scarymyths.ScaryMyths;
import net.minecraft.block.Block;
import net.minecraft.tags.*;
import net.minecraft.util.ResourceLocation;

public class CustomBlockTags {
    public static final ITag<Block> EXAMPLE = BlockTags.getCollection().get(new ResourceLocation(ScaryMyths.MOD_ID, "example"));
}
