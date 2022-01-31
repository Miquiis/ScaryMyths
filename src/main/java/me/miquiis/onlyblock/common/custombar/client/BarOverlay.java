package me.miquiis.onlyblock.common.custombar.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.custombar.common.BarInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class BarOverlay extends AbstractGui {

    private static final ResourceLocation BAR_TEXTURES = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/lava_bar.png");
    private final Minecraft client;
    private final BarInfo barInfo;

    public BarOverlay(Minecraft client, BarInfo barInfo)
    {
        this.client = client;
        this.barInfo = barInfo;
    }

    public void draw(MatrixStack matrixStack) {
        int i = this.client.getMainWindow().getScaledWidth();
        int j = 12;

        int k = i / 2 - 91;

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.client.getTextureManager().bindTexture(BAR_TEXTURES);
        this.drawBars(matrixStack, k, j, barInfo);
        ITextComponent itextcomponent = barInfo.getText();
        int l = this.client.fontRenderer.getStringPropertyWidth(itextcomponent);
        int i1 = i / 2 - l / 2;
        int j1 = j - 9;
        this.client.fontRenderer.drawTextWithShadow(matrixStack, itextcomponent, (float)i1, (float)j1, 16777215);
    }

    private void drawBars(MatrixStack matrixStack, int x, int y, BarInfo p_238485_4_) {
        this.blit(matrixStack, x, y, 0, 0, 182, 5);

        int i = (int)(p_238485_4_.getPercent() * 183.0F);

        if (i > 0) {
            this.blit(matrixStack, x, y, 0, 5, i, 5);
        }

    }

}
