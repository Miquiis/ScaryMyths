package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.entities.XPBeamProjectileEntity;
import me.miquiis.onlyblock.common.entities.XPWarhammerProjectileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Set;

public class XPLauncherItem extends Item {

    public XPLauncherItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        final ItemStack itemStack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote)
        {
            CompoundNBT tag = itemStack.getOrCreateTag();

            if (tag.contains("Activated"))
            {
                boolean current = tag.getBoolean("Activated");
                tag.putBoolean("Activated", !current);
            } else
            {
                tag.putBoolean("Activated", true);
            }

            itemStack.setTag(tag);
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
