package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.entities.XPWarhammerProjectileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.PacketDistributor;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Set;

public class WarhammerItem extends ToolItem implements IAnimatable {

    private static final int ANIM_OPEN = 0;
    public AnimationFactory factory = new AnimationFactory(this);

    public WarhammerItem(float attackDamageIn, float attackSpeedIn, IItemTier tier, Set<Block> effectiveBlocksIn, Properties builderIn) {
        super(attackDamageIn, attackSpeedIn, tier, effectiveBlocksIn, builderIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if (!worldIn.isRemote)
        {
            ItemStack itemStack = playerIn.getHeldItem(handIn);
            itemStack.shrink(1);

            final Vector3d spawnLocation = playerIn.getLookVec().mul(1, 1, 1).add(playerIn.getPositionVec());
            final Vector3d velocity = playerIn.getLookVec().mul(3, 3, 3);
            XPWarhammerProjectileEntity warhammerProjectileEntity = new XPWarhammerProjectileEntity(worldIn);
            warhammerProjectileEntity.setPosition(spawnLocation.getX(), spawnLocation.getY() + 1.0, spawnLocation.getZ());
            warhammerProjectileEntity.setVelocity(velocity.getX(), velocity.getY(), velocity.getZ());
            warhammerProjectileEntity.setShooter(playerIn);
            worldIn.addEntity(warhammerProjectileEntity);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    private <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
