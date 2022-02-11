package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.entities.DamageableExperienceOrbEntity;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ExpSword extends SwordItem {
    public ExpSword(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(tier, attackDamageIn, attackSpeedIn, builderIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote)
        {
            ServerWorld serverWorld = (ServerWorld) worldIn;
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) playerIn;

            if (serverPlayerEntity.experienceLevel < 3)
            {
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }

            serverPlayerEntity.getCooldownTracker().setCooldown(this, 20 * 3);
            serverPlayerEntity.setExperienceLevel(playerIn.experienceLevel - 3);

            final double wideAngle = 20;
            final double gap = 3;

            for (double i = -wideAngle/2; i <= wideAngle/2; i+= gap)
            {
                Vector3d lookVec = getVectorForRotation(playerIn.getPitch(1.0F), playerIn.getYaw(1.0F), (float)i);
                double x = 1 * lookVec.getX();
                double z = 1 * lookVec.getZ();
                double y = 1 * lookVec.getY();

                Vector3d vec = new Vector3d(serverPlayerEntity.getPosX() + x, (serverPlayerEntity.getPosY() + playerIn.getEyeHeight()) + y, serverPlayerEntity.getPosZ() + z);
                DamageableExperienceOrbEntity orbDamage = new DamageableExperienceOrbEntity(worldIn, vec.getX(),vec.getY(),vec.getZ(),4);
                orbDamage.setVelocity(x * 2, y * 2, z * 2);
                worldIn.playSound(null, vec.getX(), vec.getY(), vec.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1f, (float)MathUtils.getRandomMinMax(0.5, 0.6));
                worldIn.addEntity(orbDamage);
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private final Vector3d getVectorForRotation(float pitch, float yaw, float angle) {
        float f = (pitch) * ((float)Math.PI / 180F);
        float f1 = (-yaw + angle) * ((float)Math.PI / 180F);
        float f2 = MathHelper.cos(f1);
        float f3 = MathHelper.sin(f1);
        float f4 = MathHelper.cos(f);
        float f5 = MathHelper.sin(f);
        return new Vector3d((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }
}
