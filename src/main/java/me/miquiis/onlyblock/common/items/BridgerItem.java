package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.entities.CustomFallingBlockEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BridgerItem extends Item {
    public BridgerItem(Properties properties) {
        super(properties);
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote && handIn == Hand.MAIN_HAND) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)playerIn;
            serverPlayerEntity.getCooldownTracker().setCooldown(this, 20);
            BlockPos feetPos = playerIn.getPosition().down();
            Direction lookingDirection = this.getClosestForwardDirection(Direction.getFacingDirections(playerIn));

            for(int i = 0; i < 25; ++i) {
                Vector3d position = playerIn.getLookVec().add(playerIn.getPositionVec());
                Vector3d vel = playerIn.getLookVec().mul(0.5D + 0.1D * (double)i, 0.5D + 0.1D * (double)i, 0.5D + 0.1D * (double)i);
                CustomFallingBlockEntity block = new CustomFallingBlockEntity(worldIn, position.getX(), position.getY(), position.getZ(), Blocks.GOLD_BLOCK.getDefaultState());
                block.setPosition(position.getX(), position.getY() + (double)playerIn.getEyeHeight(), position.getZ());
                block.setVelocity(vel.getX(), vel.getY(), vel.getZ());
                block.fallTime = 1;
                worldIn.addEntity(block);
                BlockPos currentBlock = feetPos.offset(lookingDirection, i + 1);
                worldIn.playSound((PlayerEntity)null, currentBlock, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 0.5F, 1.0F);
                worldIn.setBlockState(currentBlock, Blocks.GOLD_BLOCK.getDefaultState());
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private Direction getClosestForwardDirection(Direction[] directions) {
        Direction[] var2 = directions;
        int var3 = directions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Direction direction = var2[var4];
            if (direction != Direction.UP && direction != Direction.DOWN) {
                return direction;
            }
        }

        return null;
    }
}
