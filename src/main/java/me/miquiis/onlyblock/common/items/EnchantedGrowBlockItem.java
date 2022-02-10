package me.miquiis.onlyblock.common.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;

public class EnchantedGrowBlockItem extends BlockItem {

    protected boolean isSphere;
    protected int radius;

    public EnchantedGrowBlockItem(Block blockIn, Item.Properties builder, int radius, boolean sphere) {
        super(blockIn, builder);
        this.radius = radius;
        this.isSphere = sphere;
    }

    public boolean hasEffect(ItemStack stack) {
        return true;
    }

    @Override
    public ActionResultType tryPlace(BlockItemUseContext context) {
        if (!context.canPlace()) {
            return ActionResultType.FAIL;
        } else {
            BlockItemUseContext blockitemusecontext = this.getBlockItemUseContext(context);
            if (blockitemusecontext == null) {
                return ActionResultType.FAIL;
            } else {
                BlockState blockstate = this.getStateForPlacement(blockitemusecontext);
                if (blockstate == null) {
                    return ActionResultType.FAIL;
                } else if (!context.getWorld().setBlockState(context.getPos(), blockstate, 11)) {
                    return ActionResultType.FAIL;
                } else {
                    final PlayerEntity playerentity = blockitemusecontext.getPlayer();
                    final BlockPos firstBlockPos = blockitemusecontext.getPos();
                    final Direction direction = context.getFace() == Direction.UP ? getClosestForwardDirection(blockitemusecontext.getNearestLookingDirections()) : context.getFace();
                    final BlockPos centerPos = new BlockPos(firstBlockPos).offset(direction, radius - 1);
                    final World world = blockitemusecontext.getWorld();
                    final BlockState firstBlockState = world.getBlockState(firstBlockPos);
                    final ItemStack itemstack = blockitemusecontext.getItem();

                    for (int x = -radius; x <= radius; x++)
                    {
                        for (int z = -radius; z <= radius; z++)
                        {
                            final BlockPos blockpos = new BlockPos(centerPos.getX() + x, firstBlockPos.getY(), centerPos.getZ() + z);
                            final BlockState currentBlockState = world.getBlockState(blockpos);
                            if (currentBlockState.getBlock() != Blocks.AIR) continue;
                            final double distance = blockpos.distanceSq(centerPos.getX(), centerPos.getY(), centerPos.getZ(), true);
                            if (isSphere && distance > radius*radius)
                            {
                                continue;
                            }
                            BlockState blockstate1 = world.getBlockState(blockpos);
                            context.getWorld().setBlockState(blockpos, blockstate, 11);
                            Block block = blockstate1.getBlock();
                            blockstate1 = this.func_219985_a(blockpos, world, itemstack, blockstate1);
                            this.onBlockPlaced(blockpos, world, playerentity, itemstack, blockstate1);
                            block.onBlockPlacedBy(world, blockpos, blockstate1, playerentity, itemstack);
                            if (playerentity instanceof ServerPlayerEntity) {
                                CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity)playerentity, blockpos, itemstack);
                            }
                        }
                    }

                    SoundType soundtype = firstBlockState.getSoundType(world, firstBlockPos, context.getPlayer());
                    world.playSound(playerentity, firstBlockPos, this.getPlaceSound(firstBlockState, world, firstBlockPos, context.getPlayer()), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    if (playerentity == null || !playerentity.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return ActionResultType.func_233537_a_(world.isRemote);
                }
            }
        }
    }

    private BlockState func_219985_a(BlockPos p_219985_1_, World p_219985_2_, ItemStack p_219985_3_, BlockState p_219985_4_) {
        BlockState blockstate = p_219985_4_;
        CompoundNBT compoundnbt = p_219985_3_.getTag();
        if (compoundnbt != null) {
            CompoundNBT compoundnbt1 = compoundnbt.getCompound("BlockStateTag");
            StateContainer<Block, BlockState> statecontainer = p_219985_4_.getBlock().getStateContainer();

            for(String s : compoundnbt1.keySet()) {
                Property<?> property = statecontainer.getProperty(s);
                if (property != null) {
                    String s1 = compoundnbt1.get(s).getString();
                    blockstate = func_219988_a(blockstate, property, s1);
                }
            }
        }

        if (blockstate != p_219985_4_) {
            p_219985_2_.setBlockState(p_219985_1_, blockstate, 2);
        }

        return blockstate;
    }

    private static <T extends Comparable<T>> BlockState func_219988_a(BlockState p_219988_0_, Property<T> p_219988_1_, String p_219988_2_) {
        return p_219988_1_.parseValue(p_219988_2_).map((p_219986_2_) -> {
            return p_219988_0_.with(p_219988_1_, p_219986_2_);
        }).orElse(p_219988_0_);
    }

    private Direction getClosestForwardDirection(Direction[] directions)
    {
        for (Direction direction : directions)
        {
            if (direction == Direction.UP || direction == Direction.DOWN) continue;
            return direction;
        }
        return null;
    }
}
