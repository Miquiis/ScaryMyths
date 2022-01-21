package me.miquiis.onlyblock.client.gui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.recipes.DiamondBlockLBR;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LavaBookScreen extends Screen {

   public static final LavaBookScreen.IBookInfo EMPTY_BOOK = new LavaBookScreen.IBookInfo() {
      /**
       * Returns the size of the book
       */
      public int getPageCount() {
         return 0;
      }

      @Override
      public CraftRecipe grabRecipe(int page, int position) {
         return CraftRecipe.NO_RECIPE;
      }

   };

   public static final LavaBookScreen.WrittenBookInfo RECIPE_BOOK = new WrittenBookInfo(new ArrayList<CraftRecipe>(
           Arrays.asList(
                   new DiamondBlockLBR()
           )
   ));

   public static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/lava_book.png");
   private LavaBookScreen.IBookInfo bookInfo;
   private int currPage;
   /** Holds a copy of the page text, split into page width lines */
   private List<IReorderingProcessor> cachedPageLines = Collections.emptyList();
   private int cachedPage = -1;
   private ITextComponent field_243344_s = StringTextComponent.EMPTY;
   private ChangePageButton buttonNextPage;
   private ChangePageButton buttonPreviousPage;
   /** Determines if a sound is played when the page is turned */
   private final boolean pageTurnSounds;

   public LavaBookScreen(LavaBookScreen.IBookInfo bookInfoIn) {
      this(bookInfoIn, true);
   }

   public LavaBookScreen() {
      this(RECIPE_BOOK, true);
   }

   private LavaBookScreen(LavaBookScreen.IBookInfo bookInfoIn, boolean pageTurnSoundsIn) {
      super(NarratorChatListener.EMPTY);
      this.bookInfo = bookInfoIn;
      this.pageTurnSounds = pageTurnSoundsIn;
   }

   public void func_214155_a(LavaBookScreen.IBookInfo p_214155_1_) {
      this.bookInfo = p_214155_1_;
      this.currPage = MathHelper.clamp(this.currPage, 0, p_214155_1_.getPageCount());
      this.updateButtons();
      this.cachedPage = -1;
   }

   /**
    * Moves the book to the specified page and returns true if it exists, false otherwise
    */
   public boolean showPage(int pageNum) {
      int i = MathHelper.clamp(pageNum, 0, this.bookInfo.getPageCount() - 1);
      if (i != this.currPage) {
         this.currPage = i;
         this.updateButtons();
         this.cachedPage = -1;
         return true;
      } else {
         return false;
      }
   }

   /**
    * I'm not sure why this exists. The function it calls is public and does all of the work
    */
   protected boolean showPage2(int pageNum) {
      return this.showPage(pageNum);
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

   /**
    * Moves the display back one page
    */
   protected void previousPage() {
      if (this.currPage > 0) {
         --this.currPage;
      }

      this.updateButtons();
   }

   /**
    * Moves the display forward one page
    */
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
         StringTextComponent textComponent = new StringTextComponent(craftRecipe.title);
         this.font.drawText(matrixStack, textComponent, (float)(i + 38), j + 14  + ((pos - 1) * 72), new Color(100, 100, 100).getRGB());
         int count = 0;
         for (ItemStack item : craftRecipe.firstRow)
         {
            drawItemStack(item, i + 38 + (count * 18), j + 27 + ((pos - 1) * 72));
            count++;
         }
         count = 0;
         for (ItemStack item : craftRecipe.secondRow)
         {
            drawItemStack(item, i + 38 + (count * 18), j + 45 + ((pos - 1) * 72));
            count++;
         }
         count = 0;
         for (ItemStack item : craftRecipe.thirdRow)
         {
            drawItemStack(item, i + 38 + (count * 18), j + 63 + ((pos - 1) * 72));
            count++;
         }
         drawItemStack(craftRecipe.getResult(), i + 128, j + 45 + ((pos - 1) * 72));
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

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0) {
         Style style = this.func_238805_a_(mouseX, mouseY);
         if (style != null && this.handleComponentClicked(style)) {
            return true;
         }
      }

      return super.mouseClicked(mouseX, mouseY, button);
   }

   public boolean handleComponentClicked(Style style) {
      ClickEvent clickevent = style.getClickEvent();
      if (clickevent == null) {
         return false;
      } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
         String s = clickevent.getValue();

         try {
            int i = Integer.parseInt(s) - 1;
            return this.showPage2(i);
         } catch (Exception exception) {
            return false;
         }
      } else {
         boolean flag = super.handleComponentClicked(style);
         if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
            this.minecraft.displayGuiScreen((Screen)null);
         }

         return flag;
      }
   }

   @Nullable
   public Style func_238805_a_(double p_238805_1_, double p_238805_3_) {
      if (this.cachedPageLines.isEmpty()) {
         return null;
      } else {
         int i = MathHelper.floor(p_238805_1_ - (double)((this.width - 192) / 2) - 36.0D);
         int j = MathHelper.floor(p_238805_3_ - 2.0D - 30.0D);
         if (i >= 0 && j >= 0) {
            int k = Math.min(128 / 9, this.cachedPageLines.size());
            if (i <= 114 && j < 9 * k + k) {
               int l = j / 9;
               if (l >= 0 && l < this.cachedPageLines.size()) {
                  IReorderingProcessor ireorderingprocessor = this.cachedPageLines.get(l);
                  return this.minecraft.fontRenderer.getCharacterManager().func_243239_a(ireorderingprocessor, i);
               } else {
                  return null;
               }
            } else {
               return null;
            }
         } else {
            return null;
         }
      }
   }

   public static List<String> nbtPagesToStrings(CompoundNBT p_214157_0_) {
      ListNBT listnbt = p_214157_0_.getList("pages", 8).copy();
      Builder<String> builder = ImmutableList.builder();

      for(int i = 0; i < listnbt.size(); ++i) {
         builder.add(listnbt.getString(i));
      }

      return builder.build();
   }

   @OnlyIn(Dist.CLIENT)
   public static class CraftRecipe {

      public static final CraftRecipe NO_RECIPE = new CraftRecipe(
              "",
              new ItemStack[]{new ItemStack(Items.AIR), new ItemStack(Items.AIR), new ItemStack(Items.AIR)},
              new ItemStack[]{new ItemStack(Items.AIR), new ItemStack(Items.AIR), new ItemStack(Items.AIR)},
              new ItemStack[]{new ItemStack(Items.AIR), new ItemStack(Items.AIR), new ItemStack(Items.AIR)},
              new ItemStack(Items.AIR)
      );

      private String title;
      private ItemStack[] firstRow, secondRow, thirdRow;
      private ItemStack result;

      public CraftRecipe(String title, ItemStack[] firstRow, ItemStack[] secondRow, ItemStack[] thirdRow, ItemStack result)
      {
         this.title = title;
         this.firstRow = firstRow;
         this.secondRow = secondRow;
         this.thirdRow = thirdRow;
         this.result = result;
      }

      public String getTitle() {
         return title;
      }

      public ItemStack[] getFirstRow() {
         return firstRow;
      }

      public ItemStack[] getSecondRow() {
         return secondRow;
      }

      public ItemStack[] getThirdRow() {
         return thirdRow;
      }

      public ItemStack getResult() {
         return result;
      }
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
   public static class WrittenBookInfo implements LavaBookScreen.IBookInfo {

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