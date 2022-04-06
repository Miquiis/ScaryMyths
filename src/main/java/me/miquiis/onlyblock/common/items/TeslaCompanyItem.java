package me.miquiis.onlyblock.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TeslaCompanyItem extends Item {
    public TeslaCompanyItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("\u00A7e- Unlocks the Tesla Shop"));
        tooltip.add(new StringTextComponent("\u00A7e- Free Flying Tesla"));
        tooltip.add(new StringTextComponent("\u00A7e- $20k per minute"));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
