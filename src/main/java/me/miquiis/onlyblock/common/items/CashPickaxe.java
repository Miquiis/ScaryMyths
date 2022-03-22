package me.miquiis.onlyblock.common.items;

import me.miquiis.onlyblock.common.registries.BlockRegister;
import net.minecraft.block.BlockState;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;

public class CashPickaxe extends PickaxeItem {
    public CashPickaxe(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.getBlock() == BlockRegister.CASH_PILE.get()) return 50f;
        return super.getDestroySpeed(stack, state);
    }
}
