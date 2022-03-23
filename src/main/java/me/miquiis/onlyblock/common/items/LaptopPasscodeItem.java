package me.miquiis.onlyblock.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class LaptopPasscodeItem extends Item {

    public LaptopPasscodeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent(color("&aPassword: &2richestmanalive")));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    private String color(String text)
    {
        return text.replace("&", "\u00A7");
    }

}
