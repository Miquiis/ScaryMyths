package me.miquiis.onlyblock.common.blocks;

import net.minecraft.block.GrassPathBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchantedGrassPathBlock extends GrassPathBlock {
    public EnchantedGrassPathBlock(Properties builder) {
        super(builder);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (worldIn.isRemote) return;

        if (entityIn instanceof LivingEntity)
        {
            ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(worldIn, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, 2);
            worldIn.addEntity(experienceOrbEntity);
        }
        
        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
