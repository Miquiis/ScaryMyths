package me.miquiis.onlyblock.common.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class EnchantedItem extends Item {
    public EnchantedItem(Properties properties) {
        super(properties);
    }

    public boolean hasEffect(ItemStack stack) {
        return true;
    }
}
