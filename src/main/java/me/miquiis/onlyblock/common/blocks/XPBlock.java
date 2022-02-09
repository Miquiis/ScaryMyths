package me.miquiis.onlyblock.common.blocks;

import me.miquiis.onlyblock.common.entities.FakeExperienceOrbEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class XPBlock extends Block {

    protected static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public XPBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote)
        {
            final double gap = 0.3;
            for (double z = pos.getZ() + 1.0; z >= pos.getZ(); z -= gap)
            {
                for (double x = pos.getX() + 1.0; x >= pos.getX(); x -= gap)
                {
                    FakeExperienceOrbEntity fakeExperienceOrbEntity = new FakeExperienceOrbEntity(worldIn, x, pos.getY() + 1.0, z, 0);
                    worldIn.addEntity(fakeExperienceOrbEntity);
                }
            }

            for (double y = pos.getY() + 1.0; y >= pos.getY(); y -= gap)
            {
                for (double z = pos.getZ() + 1.0; z >= pos.getZ(); z -= gap)
                {
                    FakeExperienceOrbEntity fakeExperienceOrbEntity = new FakeExperienceOrbEntity(worldIn, pos.getX() - 0.1, y - 0.2, z, 0);
                    worldIn.addEntity(fakeExperienceOrbEntity);

                    fakeExperienceOrbEntity = new FakeExperienceOrbEntity(worldIn, pos.getX() + 1.1, y - 0.2, z, 0);
                    worldIn.addEntity(fakeExperienceOrbEntity);
                }

                for (double x = pos.getX() + 1.0; x >= pos.getX(); x -= gap)
                {
                    FakeExperienceOrbEntity fakeExperienceOrbEntity = new FakeExperienceOrbEntity(worldIn, x, y - 0.2, pos.getZ() - 0.1, 0);
                    worldIn.addEntity(fakeExperienceOrbEntity);

                    fakeExperienceOrbEntity = new FakeExperienceOrbEntity(worldIn, x, y - 0.2, pos.getZ() + 1.1, 0);
                    worldIn.addEntity(fakeExperienceOrbEntity);
                }
            }

        }

        super.onBlockHarvested(worldIn, pos, state, player);
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
