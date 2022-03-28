package me.miquiis.onlyblock.common.blocks;

import me.miquiis.onlyblock.common.tileentities.MoneyVacuumTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoneyVacuumBlock extends Block {
    public MoneyVacuumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        MoneyVacuumTileEntity tileEntity = (MoneyVacuumTileEntity) worldIn.getTileEntity(pos);
        tileEntity.setOwner(placer.getUniqueID());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new MoneyVacuumTileEntity();
    }
}
