package me.miquiis.onlyblock.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class SpeedBoostBlock extends BaseHorizontalBlock {


    public SpeedBoostBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof PlayerEntity)
        {
            PlayerEntity playerEntity = (PlayerEntity) entityIn;
            playerEntity.addPotionEffect(new EffectInstance(Effects.SPEED, 20, 2, false, false));
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape TEST_SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 1, 16);
        return TEST_SHAPE;
    }
}
