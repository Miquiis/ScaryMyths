package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.entities.GoldenProjectileEntity;
import me.miquiis.onlyblock.common.entities.renderer.GoldenProjectileRenderer;
import me.miquiis.onlyblock.common.registries.SoundRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class GoldenBazooka extends Item {
    public GoldenBazooka(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn != Hand.MAIN_HAND) return ActionResult.resultPass(playerIn.getHeldItem(handIn));
        if (!worldIn.isRemote)
        {
            final Vector3d lookVec = playerIn.getLookVec().mul(1, 1, 1).add(playerIn.getPositionVec());
            final Vector3d velocity = playerIn.getLookVec().mul(3, 3, 3);
            GoldenProjectileEntity laserProjectileEntity = new GoldenProjectileEntity(worldIn);
            laserProjectileEntity.setPosition(lookVec.getX(), lookVec.getY() + playerIn.getEyeHeight(), lookVec.getZ());
            laserProjectileEntity.setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
//            laserProjectileEntity.setHeadRotation(100, 100);
//            laserProjectileEntity.rotateTowards(100, 100);
//            laserProjectileEntity.rotationYaw = 100;
//            laserProjectileEntity.prevRotationYaw = 100;
            worldIn.addEntity(laserProjectileEntity);
            playerIn.getCooldownTracker().setCooldown(this, 20*3);
//            worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundRegister.ROCK_THROW.get(), SoundCategory.PLAYERS, 0.5f, (float) MathUtils.getRandomMinMax(0.8f, 1.2f));
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
