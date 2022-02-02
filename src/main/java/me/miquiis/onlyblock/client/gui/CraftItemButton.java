package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.gui.GuiUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CraftItemButton extends Button {
   private final boolean canCraft;

   public static final int DEFAULT_BACKGROUND_COLOR = 0xF0100010;
   public static final int DEFAULT_BORDER_COLOR_START = 0x505000FF;
   public static final int DEFAULT_BORDER_COLOR_END = (DEFAULT_BORDER_COLOR_START & 0xFEFEFE) >> 1 | DEFAULT_BORDER_COLOR_START & 0xFF000000;

   public static final int GREEN_BORDER_COLOR_START = new java.awt.Color(43, 255, 50).getRGB();
   public static final int GREEN_BORDER_COLOR_END = (GREEN_BORDER_COLOR_START & 0xFEFEFE) >> 1 | GREEN_BORDER_COLOR_START & 0xFF000000;

   public static final int RED_BORDER_COLOR_START = new java.awt.Color(255, 69, 69).getRGB();
   public static final int RED_BORDER_COLOR_END = (RED_BORDER_COLOR_START & 0xFEFEFE) >> 1 | RED_BORDER_COLOR_START & 0xFF000000;

   public CraftItemButton(int x, int y, boolean canCraft, IPressable onPress) {
      super(x, y, 26, 16, StringTextComponent.EMPTY, onPress);
      this.canCraft = canCraft;
   }

   public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getInstance().getTextureManager().bindTexture(CraftingRecipeBookScreen.BOOK_TEXTURES);
      int i = 53;
      int j = 183;

      if (this.isHovered()) {
         j += 18;
      }

      if (this.canCraft()) {
         i += 28;
      }

      this.blit(matrixStack, this.x, this.y, i, j, 26, 16);

      if (this.isHovered())
      {
         onHover(matrixStack, Minecraft.getInstance().fontRenderer, mouseX, mouseY);
      }
   }

   public void onHover(MatrixStack matrixStack, FontRenderer font, double mouseX, double mouseY) {
      if (!canCraft())
      {
         drawHoveringText(matrixStack, Collections.singletonList(new StringTextComponent("You don't have enough resources.")), (int)mouseX, (int)mouseY, 1920, 1080, -1, DEFAULT_BACKGROUND_COLOR, DEFAULT_BORDER_COLOR_START, DEFAULT_BORDER_COLOR_END, font);
      }
      else
      {
         drawHoveringText(matrixStack, Collections.singletonList(new StringTextComponent("Click here to craft this item.")), (int)mouseX, (int)mouseY, 1920, 1080, -1, DEFAULT_BACKGROUND_COLOR, DEFAULT_BORDER_COLOR_START, DEFAULT_BORDER_COLOR_END, font);
      }
   }

   public boolean canCraft() {
      return canCraft;
   }

   public static void drawHoveringText(MatrixStack mStack, List<? extends ITextProperties> textLines, int mouseX, int mouseY,
                                       int screenWidth, int screenHeight, int maxTextWidth, int backgroundColor, int borderColorStart, int borderColorEnd, FontRenderer font)
   {
      if (!textLines.isEmpty())
      {
         RenderSystem.disableRescaleNormal();
         RenderSystem.disableDepthTest();
         int tooltipTextWidth = 0;

         for (ITextProperties textLine : textLines)
         {
            int textLineWidth = font.getStringPropertyWidth(textLine);
            if (textLineWidth > tooltipTextWidth)
               tooltipTextWidth = textLineWidth;
         }

         boolean needsWrap = false;

         int titleLinesCount = 1;
         int tooltipX = mouseX + 12;
         if (tooltipX + tooltipTextWidth + 4 > screenWidth)
         {
            tooltipX = mouseX - 16 - tooltipTextWidth;
            if (tooltipX < 4) // if the tooltip doesn't fit on the screen
            {
               if (mouseX > screenWidth / 2)
                  tooltipTextWidth = mouseX - 12 - 8;
               else
                  tooltipTextWidth = screenWidth - 16 - mouseX;
               needsWrap = true;
            }
         }

         if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth)
         {
            tooltipTextWidth = maxTextWidth;
            needsWrap = true;
         }

         if (needsWrap)
         {
            int wrappedTooltipWidth = 0;
            List<ITextProperties> wrappedTextLines = new ArrayList<>();
            for (int i = 0; i < textLines.size(); i++)
            {
               ITextProperties textLine = textLines.get(i);
               List<ITextProperties> wrappedLine = font.getCharacterManager().func_238362_b_(textLine, tooltipTextWidth, Style.EMPTY);
               if (i == 0)
                  titleLinesCount = wrappedLine.size();

               for (ITextProperties line : wrappedLine)
               {
                  int lineWidth = font.getStringPropertyWidth(line);
                  if (lineWidth > wrappedTooltipWidth)
                     wrappedTooltipWidth = lineWidth;
                  wrappedTextLines.add(line);
               }
            }
            tooltipTextWidth = wrappedTooltipWidth;
            textLines = wrappedTextLines;

            if (mouseX > screenWidth / 2)
               tooltipX = mouseX - 16 - tooltipTextWidth;
            else
               tooltipX = mouseX + 12;
         }

         int tooltipY = mouseY - 12;
         int tooltipHeight = 8;

         if (textLines.size() > 1)
         {
            tooltipHeight += (textLines.size() - 1) * 10;
            if (textLines.size() > titleLinesCount)
               tooltipHeight += 2; // gap between title lines and next lines
         }

         if (tooltipY < 4)
            tooltipY = 4;
         else if (tooltipY + tooltipHeight + 4 > screenHeight)
            tooltipY = screenHeight - tooltipHeight - 4;

         final int zLevel = 300;

         mStack.push();
         Matrix4f mat = mStack.getLast().getMatrix();
         //TODO, lots of unnessesary GL calls here, we can buffer all these together.
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
         GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);

         IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
         mStack.translate(0.0D, 0.0D, zLevel);

         int tooltipTop = tooltipY;

         for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber)
         {
            ITextProperties line = textLines.get(lineNumber);
            if (line != null)
               font.drawEntityText(LanguageMap.getInstance().func_241870_a(line), (float)tooltipX, (float)tooltipY, -1, true, mat, renderType, false, 0, 15728880);

            if (lineNumber + 1 == titleLinesCount)
               tooltipY += 2;

            tooltipY += 10;
         }

         renderType.finish();
         mStack.pop();

         RenderSystem.enableDepthTest();
         RenderSystem.enableRescaleNormal();
      }
   }
}