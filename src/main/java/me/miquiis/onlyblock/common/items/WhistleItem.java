package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.classes.JHTML;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WhistleItem extends Item {

    public WhistleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn != Hand.MAIN_HAND) return super.onItemRightClick(worldIn, playerIn, handIn);
        if (!worldIn.isRemote)
        {
            IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(playerIn);
            onlyBlock.getStockIsland().togglePacific();
            onlyBlock.sync((ServerPlayerEntity)playerIn);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
