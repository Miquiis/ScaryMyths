package me.miquiis.onlyblock.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.trees.Tree;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class EnchantedSaplingBlock extends SaplingBlock {

    private Tree tree;

    public EnchantedSaplingBlock(Tree treeIn, Properties properties) {
        super(treeIn, properties);
        this.tree = treeIn;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
    }

    @Override
    public void placeTree(ServerWorld world, BlockPos pos, BlockState state, Random rand) {
        this.tree.attemptGrowTree(world, world.getChunkProvider().getChunkGenerator(), pos, state, rand);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isRemote)
        {
            ServerWorld serverWorld = (ServerWorld) worldIn;
            placeTree(serverWorld, pos, state, serverWorld.rand);
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
}
