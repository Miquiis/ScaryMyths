package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;
import net.minecraft.client.gui.recipebook.RecipeTabToggleWidget;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LeaderboardGui extends AbstractGui implements IRenderable, IGuiEventListener {
    private static final ResourceLocation LEADERBOARD = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/leaderboard.png");
    private Minecraft mc;
    private int width, height;
    private boolean isVisible;

    public void init(int widthIn, int heightIn, Minecraft minecraft, boolean widthTooNarrowIn, boolean isVisible)
    {
        this.mc = minecraft;
        this.width = widthIn;
        this.height = heightIn;
        this.isVisible = !isVisible;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (isVisible())
        {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0F, 0.0F, 100.0F);
            this.mc.getTextureManager().bindTexture(LEADERBOARD);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int i = (this.width - 147) / 2 - 86;
            int j = (this.height - 166) / 2;
            this.blit(matrixStack, i, j, 1, 1, 147, 166);
            drawCenteredString(matrixStack, mc.fontRenderer, "\u00A76Leaderboard", i + 74, j - 10, -1);
            renderPlayers(matrixStack, mouseX, mouseY, partialTicks);
            RenderSystem.popMatrix();
        }
    }

    private void renderPlayers(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        RenderSystem.pushMatrix();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        int count = 0;
        List<AbstractClientPlayerEntity> sortedList = mc.world.getPlayers().stream().sorted(Comparator.comparingInt(o -> OnlyMoneyBlock.getCapability(o).getBankAccount())).collect(Collectors.toList());
        Collections.reverse(sortedList);
        for (AbstractClientPlayerEntity clientPlayerEntity : sortedList)
        {
            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(clientPlayerEntity);
            int i = (this.width - 125) / 2 - 86;
            int j = (this.height - 143) / 2 + (30 * count);
            this.mc.getTextureManager().bindTexture(LEADERBOARD);
            this.blit(matrixStack, i, j, 1, 168, 125, 26);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.5f, 0.5f, 0.5f);
            this.mc.getTextureManager().bindTexture(clientPlayerEntity.getLocationSkin());
            this.blit(matrixStack, (i + 8) * 2, (j + 5) * 2, 32, 32, 32, 32);
            RenderSystem.popMatrix();
            drawString(matrixStack, mc.fontRenderer, new StringTextComponent("\u00A7f" + clientPlayerEntity.getName().getString()), i + 30, j + 4, -1);
            mc.fontRenderer.drawString(matrixStack, "\u00A7a$" + onlyMoneyBlock.getBankAccount(), i + 30, j + 14, 0);
            mc.fontRenderer.drawString(matrixStack, "\u00A7e" + onlyMoneyBlock.getDays(), i + 100, j + 9, 0);
            count++;
        }

        RenderSystem.popMatrix();
    }

    public int updateScreenPosition(boolean widthTooNarrow, int width, int xSize) {
        int i;
        if (this.isVisible() && !widthTooNarrow) {
            i = 177 + (width - xSize - 200) / 2;
        } else {
            i = (width - xSize) / 2;
        }

        return i;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void toggleVisibility()
    {
        this.isVisible = !isVisible;
    }
}