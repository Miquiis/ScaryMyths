package me.miquiis.onlyblock.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;

public class BuySignBlock extends BaseHorizontalBlock {

    protected static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(-8.0D, 10.0D, 8.0D, 16.0D, 32.0D, 10.0D);

    public BuySignBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        VoxelShape TEST_SHAPE;
        final Direction currentDirection = state.get(HORIZONTAL_FACING);
        switch (currentDirection)
        {
            case SOUTH:
            {
                TEST_SHAPE = Block.makeCuboidShape(-5.0D, 0.0D, 7.0D, 21.0D, 32.0D, 10.0D);
                break;
            }
            case EAST:
            {
                TEST_SHAPE = Block.makeCuboidShape(7.0D, 0.0D, -5.0D, 10.0D, 32.0D, 21.0D);
                break;
            }
            case NORTH:
            {
                TEST_SHAPE = Block.makeCuboidShape(-5.0D, 0.0D, 6.0D, 21.0D, 32.0D, 9.0D);
                break;
            }
            case WEST:
            {
                TEST_SHAPE = Block.makeCuboidShape(6.0D, 0.0D, -5.0D, 9.0D, 32.0D, 21.0D);
                break;
            }
            default:
            {
                TEST_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
                break;
            }
        }
        return TEST_SHAPE;
    }
}
