package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.recipes.DiamondBlockLBR;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CobblestoneBookScreen extends Screen {

   public static final CobblestoneBookScreen.WrittenBookInfo RECIPE_BOOK = new WrittenBookInfo(new ArrayList<CraftRecipe>(
           Arrays.asList(
                   new DiamondBlockLBR()
           )
   ));

   public static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/cobblestone_book.png");
   private CobblestoneBookScreen.IBookInfo bookInfo;
   private int currPage;
   private ITextComponent field_243344_s = StringTextComponent.EMPTY;
   private ChangePageButton buttonNextPage;
   private ChangePageButton buttonPreviousPage;
   /** Determines if a sound is played when the page is turned */
   private final boolean pageTurnSounds;

   public CobblestoneBookScreen() {
      this(RECIPE_BOOK, true);
   }

   private CobblestoneBookScreen(CobblestoneBookScreen.IBookInfo bookInfoIn, boolean pageTurnSoundsIn) {
      super(NarratorChatListener.EMPTY);
      this.bookInfo = bookInfoIn;
      this.pageTurnSounds = pageTurnSoundsIn;
   }

   protected void init() {
      this.addChangePageButtons();
   }

   protected void addChangePageButtons() {
      int i = (this.width - 192) / 2;
      int j = (this.height - 192) / 2;
      this.buttonNextPage = this.addButton(new ChangePageButton(i + 120, j + 155, true, (p_214159_1_) -> {
         this.nextPage();
      }, this.pageTurnSounds));
      this.buttonPreviousPage = this.addButton(new ChangePageButton(i + 35, j+ 155, false, (p_214158_1_) -> {
         this.previousPage();
      }, this.pageTurnSounds));
      this.updateButtons();
   }

   private int getPageCount() {
      return this.bookInfo.getPageCount();
   }

   protected void previousPage() {
      if (this.currPage > 0) {
         --this.currPage;
      }

      this.updateButtons();
   }

   protected void nextPage() {
      if (this.currPage < this.getPageCount()) {
         ++this.currPage;
      }

      this.updateButtons();
   }

   private void updateButtons() {
      this.buttonNextPage.visible = this.currPage < this.getPageCount();
      this.buttonPreviousPage.visible = this.currPage > 0;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         switch(keyCode) {
         case 266:
            this.buttonPreviousPage.onPress();
            return true;
         case 267:
            this.buttonNextPage.onPress();
            return true;
         default:
            return false;
         }
      }
   }

   public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(matrixStack);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(BOOK_TEXTURES);
      int i = (this.width - 192) / 2;
      int j = (this.height - 192) / 2;
      this.blit(matrixStack, i, j, 0, 0, 192, 192);

      this.field_243344_s = new TranslationTextComponent("book.pageIndicator", this.currPage + 1, Math.max(this.getPageCount() + 1, 1));

      int i1 = this.font.getStringPropertyWidth(this.field_243344_s) / 2;
      matrixStack.push();
      matrixStack.scale(0.5f, 0.5f, 0.5f);
      matrixStack.translate((float)(i - i1 + 192 - 44), j + 14, 0);
      this.font.drawText(matrixStack, this.field_243344_s, (float)(i - i1 + 192 - 44), j + 14, 0);
      matrixStack.pop();

      for (int pos = 1; pos <= 2; pos++)
      {
         CraftRecipe craftRecipe = bookInfo.getRecipe(this.currPage, pos);
         StringTextComponent textComponent = new StringTextComponent(craftRecipe.getTitle());
         this.font.drawText(matrixStack, textComponent, (float)(i + 38), j + 14  + ((pos - 1) * 72), new Color(100, 100, 100).getRGB());
         int count = 0;
         List<HoverItem> toDraw = new ArrayList<>();
         for (ItemStack item : craftRecipe.getFirstRow())
         {
            int x = i + 38 + (count * 18);
            int y = j + 27 + ((pos - 1) * 72);
            drawItemStack(item, x, y);
            toDraw.add(new HoverItem(item, x - 1, y - 1, 17, 17, new StringTextComponent("item")));
            count++;
         }
         count = 0;
         for (ItemStack item : craftRecipe.getSecondRow())
         {
            int x = i + 38 + (count * 18);
            int y = j + 45 + ((pos - 1) * 72);
            drawItemStack(item, x, y);
            toDraw.add(new HoverItem(item, x - 1, y - 1, 17, 17, new StringTextComponent("item")));
            count++;
         }
         count = 0;
         for (ItemStack item : craftRecipe.getThirdRow())
         {
            int x = i + 38 + (count * 18);
            int y = j + 63 + ((pos - 1) * 72);
            drawItemStack(item, x, y);
            toDraw.add(new HoverItem(item, x - 1, y - 1, 17, 17, new StringTextComponent("item")));
            count++;
         }

         drawItemStack(craftRecipe.getResult(), i + 128, j + 45 + ((pos - 1) * 72));
         toDraw.add(new HoverItem(craftRecipe.getResult(), i + 124, j + 40 + ((pos - 1) * 72), 24, 24, new StringTextComponent("item")));

         toDraw.forEach(hoverItem -> hoverItem.mouseMoved(matrixStack, Minecraft.getInstance().fontRenderer, mouseX, mouseY));
      }

      super.render(matrixStack, mouseX, mouseY, partialTicks);
   }

   private void drawItemStack(ItemStack stack, int x, int y) {
      RenderSystem.translatef(0.0F, 0.0F, 32.0F);
      this.setBlitOffset(200);
      this.itemRenderer.zLevel = 200.0F;
      net.minecraft.client.gui.FontRenderer font = stack.getItem().getFontRenderer(stack);
      if (font == null) font = this.font;
      this.itemRenderer.renderItemAndEffectIntoGUI(stack, x, y);
      this.setBlitOffset(0);
      this.itemRenderer.zLevel = 0.0F;
   }

   @OnlyIn(Dist.CLIENT)
   public interface IBookInfo {
      /**
       * Returns the size of the book
       */
      int getPageCount();

      CraftRecipe grabRecipe(int page, int position);

      default CraftRecipe getRecipe(int page, int position) {
         return (page >= 0 && page <= this.getPageCount()) && (position >= 1 && position <= 2) ? this.grabRecipe(page, position) : CraftRecipe.NO_RECIPE;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class WrittenBookInfo implements CobblestoneBookScreen.IBookInfo {

      private final List<CraftRecipe> craftRecipes;

      public WrittenBookInfo(List<CraftRecipe> craftRecipes)
      {
         this.craftRecipes = craftRecipes;
      }

      @Override
      public int getPageCount() {
         if (craftRecipes.size() == 0) return 0;
         return (craftRecipes.size() - 1) / 2;
      }

      @Override
      public CraftRecipe grabRecipe(int page, int position) {
         int currentPosition = (2 * page) + position;
         if (craftRecipes.size() < currentPosition)
         {
            return CraftRecipe.NO_RECIPE;
         }
         return craftRecipes.get(currentPosition - 1);
      }
   }
}