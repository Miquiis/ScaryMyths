package me.miquiis.onlyblock.common.blocks;

import me.miquiis.onlyblock.common.registries.TileEntityRegister;
import me.miquiis.onlyblock.common.tileentity.MoneyPrinterTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoneyPrinterBlock extends BaseHorizontalBlock {

    protected static final VoxelShape BOTTOM_SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public MoneyPrinterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (worldIn.getTileEntity(pos) instanceof MoneyPrinterTileEntity)
        {
            MoneyPrinterTileEntity moneyPrinterTileEntity = (MoneyPrinterTileEntity) worldIn.getTileEntity(pos);
            moneyPrinterTileEntity.setOwner(placer.getUniqueID());
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityRegister.MONEY_PRINTER_TILE_ENTITY.get().create();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return BOTTOM_SHAPE;
    }
}
