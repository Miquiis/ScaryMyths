package me.miquiis.onlyblock.common.blocks;

import me.miquiis.onlyblock.common.entities.FakeExperienceOrbEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class XPBlock extends Block {

    protected static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public XPBlock(Properties properties) {
        super(properties);
    }


    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        System.out.println("Here");
        FakeExperienceOrbEntity expOrb = new FakeExperienceOrbEntity(worldIn, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,0);
        worldIn.addEntity(expOrb);

        if (!worldIn.isRemote)
        {
            ZombieEntity expOrba = new ZombieEntity(worldIn);
            expOrba.setPosition(pos.getX(), pos.getY(), pos.getZ());
            worldIn.addEntity(expOrba);
        }

        super.animateTick(stateIn, worldIn, pos, rand);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BOTTOM_SHAPE;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }
}
