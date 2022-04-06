package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.entities.TNTProjectileEntity;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class TNTBazooka extends Item {

    public TNTBazooka(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn != Hand.MAIN_HAND) return ActionResult.resultPass(playerIn.getHeldItem(handIn));
        if (!worldIn.isRemote)
        {
            final Vector3d lookVec = playerIn.getLookVec().mul(1, 1, 1).add(playerIn.getPositionVec());
            final Vector3d velocity = playerIn.getLookVec().mul(3, 3, 3);
            TNTProjectileEntity laserProjectileEntity = new TNTProjectileEntity(worldIn);
            laserProjectileEntity.setPosition(lookVec.getX(), lookVec.getY() + playerIn.getEyeHeight(), lookVec.getZ());
            laserProjectileEntity.setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
            laserProjectileEntity.setShooter(playerIn);
            worldIn.addEntity(laserProjectileEntity);
            playerIn.getCooldownTracker().setCooldown(this, 20 * 60);
            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.PLAYERS, 0.5f, (float) MathUtils.getRandomMinMax(0.8f, 1.2f));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
