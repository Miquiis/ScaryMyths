package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.Currency;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.registries.SoundRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class CashItem extends Item {
    public CashItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (handIn != Hand.MAIN_HAND) return super.onItemRightClick(worldIn, playerIn, handIn);

        if (!worldIn.isRemote)
        {
            if (playerIn.isSneaking())
            {
                ItemStack itemStack = super.onItemRightClick(worldIn, playerIn, handIn).getResult();
                int lastCount = itemStack.getCount();
                itemStack.setCount(0);
                ICurrency currency = playerIn.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
                worldIn.playSound(null, playerIn.getPosX(), playerIn.getPosY(), playerIn.getPosZ(), SoundRegister.KATCHING.get(), SoundCategory.PLAYERS, 0.5f, 1f);
                currency.addOrSubtractAmount(lastCount);
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
