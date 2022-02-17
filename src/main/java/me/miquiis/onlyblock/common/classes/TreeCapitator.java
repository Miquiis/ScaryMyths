package me.miquiis.onlyblock.common.classes;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class TreeCapitator {

    private static BlockPos getMidBlock(ArrayList<BlockPos> list)
    {
        final BlockPos highestBlock = getHighestBlock(list);
        final BlockPos lowestBlock = getLowestBlock(list);

        return lowestBlock.add(0, (highestBlock.getY() - lowestBlock.getY()) / 2, 0);
    }

    private static BlockPos getLowestBlock(ArrayList<BlockPos> list)
    {
        BlockPos currentHighest = null;

        for (BlockPos currentPos : list)
        {
            if (currentHighest == null)
            {
                currentHighest = currentPos;
                continue;
            }

            if (currentHighest.getY() > currentPos.getY())
            {
                currentHighest = currentPos;
            }
        }

        return currentHighest;
    }

    private static BlockPos getHighestBlock(ArrayList<BlockPos> list)
    {
        BlockPos currentHighest = null;

        for (BlockPos currentPos : list)
        {
            if (currentHighest == null)
            {
                currentHighest = currentPos;
                continue;
            }

            if (currentHighest.getY() < currentPos.getY())
            {
                currentHighest = currentPos;
            }
        }

        return currentHighest;
    }

    private static void chopLogs(World world, BlockPos pos, Block block, ArrayList<BlockPos> list, boolean drop, ArrayList<BlockPos> listOfBlocks) {
        if (world.getBlockState(pos.north()).getBlock() == block) {
            list.add(pos.north());
        }
        if (world.getBlockState(pos.north().east()).getBlock() == block) {
            list.add(pos.north().east());
        }
        if (world.getBlockState(pos.north().west()).getBlock() == block) {
            list.add(pos.north().west());
        }
        if (world.getBlockState(pos.north().east().down()).getBlock() == block) {
            list.add(pos.north().east().down());
        }
        if (world.getBlockState(pos.north().west().down()).getBlock() == block) {
            list.add(pos.north().west().down());
        }
        if (world.getBlockState(pos.south()).getBlock() == block) {
            list.add(pos.south());
        }
        if (world.getBlockState(pos.south().east()).getBlock() == block) {
            list.add(pos.south().east());
        }
        if (world.getBlockState(pos.south().west()).getBlock() == block) {
            list.add(pos.south().west());
        }
        if (world.getBlockState(pos.south().east().down()).getBlock() == block) {
            list.add(pos.south().east().down());
        }
        if (world.getBlockState(pos.south().west().down()).getBlock() == block) {
            list.add(pos.south().west().down());
        }
        if (world.getBlockState(pos.east()).getBlock() == block) {
            list.add(pos.east());
        }
        if (world.getBlockState(pos.west()).getBlock() == block) {
            list.add(pos.west());
        }
        if (world.getBlockState(pos.down()).getBlock() == block) {
            list.add(pos.down());
        }
        if (world.getBlockState(pos.down().north()).getBlock() == block) {
            list.add(pos.down().north());
        }
        if (world.getBlockState(pos.down().south()).getBlock() == block) {
            list.add(pos.down().south());
        }
        if (world.getBlockState(pos.down().east()).getBlock() == block) {
            list.add(pos.down().east());
        }
        if (world.getBlockState(pos.down().west()).getBlock() == block) {
            list.add(pos.down().west());
        }
        if (world.getBlockState(pos.north().east().up()).getBlock() == block) {
            list.add(pos.north().east().up());
        }
        if (world.getBlockState(pos.north().west().up()).getBlock() == block) {
            list.add(pos.north().west().up());
        }
        if (world.getBlockState(pos.south().east().up()).getBlock() == block) {
            list.add(pos.south().east().up());
        }
        if (world.getBlockState(pos.south().west().up()).getBlock() == block) {
            list.add(pos.south().west().up());
        }
        if (world.getBlockState(pos.up()).getBlock() == block) {
            list.add(pos.up());
        }
        if (world.getBlockState(pos.up().north()).getBlock() == block) {
            list.add(pos.up().north());
        }
        if (world.getBlockState(pos.up().south()).getBlock() == block) {
            list.add(pos.up().south());
        }
        if (world.getBlockState(pos.up().east()).getBlock() == block) {
            list.add(pos.up().east());
        }
        if (world.getBlockState(pos.up().west()).getBlock() == block) {
            list.add(pos.up().west());
        }
        if (list.size() <= 0 || list == null) {
            list = null;
            return;
        }

        listOfBlocks.addAll(list);

        for (int i = 0; i < list.size(); i++) {
            BlockPos pos1 = list.get(i);
            destroyBlock(world, pos1, pos, drop);
            chopLogs(world, list.get(i), block, new ArrayList<BlockPos>(), drop, listOfBlocks);
        }
    }

    public static boolean destroyBlock(World world, BlockPos pos, BlockPos posToDropItems, boolean drop) {
        BlockState blockstate = world.getBlockState(pos);
        if (blockstate.isAir(world, pos)) {
            return false;
        } else {
            TileEntity tileentity = blockstate.hasTileEntity() ? world.getTileEntity(pos) : null;
            if (drop) {
                Block.spawnDrops(blockstate, world, posToDropItems, tileentity);
            }
            return world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }


}
