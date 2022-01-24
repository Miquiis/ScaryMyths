package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class HoverItem extends Widget {

    private ItemStack itemStack;

    public HoverItem(ItemStack itemStack, int x, int y, int width, int height, ITextComponent title) {
        super(x, y, width, height, title);
        this.itemStack = itemStack;
    }

    public void mouseMoved(MatrixStack matrixStack, FontRenderer font, double mouseX, double mouseY) {
        int widthPos = x + width;
        int heightPos = y + height;

        if (mouseX >= x && mouseX <= widthPos)
        {
            if (mouseY >= y && mouseY <= heightPos)
            {
                GuiUtils.drawHoveringText(matrixStack, itemStack.getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL), (int)mouseX, (int)mouseY, 1920, 1080, -1, font);
            }
        }

        super.mouseMoved(mouseX, mouseY);
    }
}
