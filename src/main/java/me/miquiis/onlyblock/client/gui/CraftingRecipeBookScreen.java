package me.miquiis.onlyblock.client.gui;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.recipes.DiamondBlockLBR;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.OpenRecipeWorkbenchMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

import java.awt.*;
import java.util.*;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CraftingRecipeBookScreen extends Screen {

   public static final CraftingRecipeBookScreen.WrittenBookInfo RECIPE_BOOK = new WrittenBookInfo(new ArrayList<>(
           Arrays.asList(
                   new DiamondBlockLBR()
           )
   ));

   public static final ResourceLocation BOOK_TEXTURES = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/crafting_book.png");
   private CraftingRecipeBookScreen.IBookInfo bookInfo;
   private int currPage;
   private ITextComponent field_243344_s = StringTextComponent.EMPTY;
   private ChangePageButton buttonNextPage;
   private ChangePageButton buttonPreviousPage;
   private CraftItemButton topCraftButton;
   private CraftItemButton bottomCraftButton;
   private final boolean pageTurnSounds;

   public CraftingRecipeBookScreen() {
      this(RECIPE_BOOK, true);
   }

   private CraftingRecipeBookScreen(CraftingRecipeBookScreen.IBookInfo bookInfoIn, boolean pageTurnSoundsIn) {
      super(NarratorChatListener.EMPTY);
      this.bookInfo = bookInfoIn;
      this.pageTurnSounds = pageTurnSoundsIn;
   }

   protected void init() {
      this.addChangePageButtons();
      this.addCraftButtons();
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

   protected void addCraftButtons()
   {
      int i = (this.width - 192) / 2;
      int j = (this.height - 192) / 2;

      final CraftRecipe firstRecipe = bookInfo.getRecipe(this.currPage, 1);
      final CraftRecipe secondRecipe = bookInfo.getRecipe(this.currPage, 2);

      final ClientPlayerEntity player = Minecraft.getInstance().player;

      if (!firstRecipe.isEmpty())
         this.topCraftButton = this.addButton(new CraftItemButton(i + 97, j + 68, canCraftItem(firstRecipe, player.inventory), (button) -> {
            CraftItemButton craftItemButton = (CraftItemButton) button;
            if (craftItemButton.canCraft())
            {
               OnlyBlockNetwork.CHANNEL.sendToServer(new OpenRecipeWorkbenchMessage(firstRecipe));
            }
         }));

      if (!secondRecipe.isEmpty())
         this.bottomCraftButton = this.addButton(new CraftItemButton(i + 97, j + 140, canCraftItem(secondRecipe, player.inventory), (button) -> {
            CraftItemButton craftItemButton = (CraftItemButton) button;
            if (craftItemButton.canCraft())
            {
               OnlyBlockNetwork.CHANNEL.sendToServer(new OpenRecipeWorkbenchMessage(secondRecipe));
            }
         }));
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
      int i = (this.width - 146) / 2;
      int j = (this.height - 180) / 2;
      this.blit(matrixStack, i, j, 20, 1, 146, 180);

      this.field_243344_s = new TranslationTextComponent("book.pageIndicator", this.currPage + 1, Math.max(this.getPageCount() + 1, 1));

      int i1 = this.font.getStringPropertyWidth(this.field_243344_s) / 2;
      matrixStack.push();
      matrixStack.scale(0.5f, 0.5f, 0.5f);
      matrixStack.translate((float)(i - i1 + 152 - 44), j + 14, 0);
      this.font.drawText(matrixStack, this.field_243344_s, (float)(i - i1 + 192 - 44), j + 14, 0);
      matrixStack.pop();

      for (int pos = 1; pos <= 2; pos++)
      {
         CraftRecipe craftRecipe = bookInfo.getRecipe(this.currPage, pos);
         StringTextComponent textComponent = new StringTextComponent(craftRecipe.getTitle());
         this.font.drawText(matrixStack, textComponent, (float)(i + 17), j + 14  + ((pos - 1) * 72), new Color(100, 100, 100).getRGB());
         int count = 0;
         List<HoverItem> toDraw = new ArrayList<>();
         for (ItemStack item : craftRecipe.getFirstRow())
         {
            int x = i + 18 + (count * 18);
            int y = j + 26 + ((pos - 1) * 72);
            drawItemStack(item, x, y);
            toDraw.add(new HoverItem(item, x - 1, y - 1, 17, 17, new StringTextComponent("item")));
            count++;
         }
         count = 0;
         for (ItemStack item : craftRecipe.getSecondRow())
         {
            int x = i + 18 + (count * 18);
            int y = j + 44 + ((pos - 1) * 72);
            drawItemStack(item, x, y);
            toDraw.add(new HoverItem(item, x - 1, y - 1, 17, 17, new StringTextComponent("item")));
            count++;
         }
         count = 0;
         for (ItemStack item : craftRecipe.getThirdRow())
         {
            int x = i + 18 + (count * 18);
            int y = j + 62 + ((pos - 1) * 72);
            drawItemStack(item, x, y);
            toDraw.add(new HoverItem(item, x - 1, y - 1, 17, 17, new StringTextComponent("item")));
            count++;
         }

         drawItemStack(craftRecipe.getResult(), i + 108, j + 44 + ((pos - 1) * 72));
         toDraw.add(new HoverItem(craftRecipe.getResult(), i + 103, j + 39 + ((pos - 1) * 72), 25, 25, new StringTextComponent("item")));

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

   private boolean canCraftItem(CraftRecipe craftRecipe, PlayerInventory inventory)
   {
      final Map<Item, Integer> usingItems = new HashMap<>();

      for (ItemStack itemStack : craftRecipe.getAllRows())
      {
         Object value = usingItems.computeIfPresent(itemStack.getItem(), (item, integer) -> {
            return itemStack.getCount() + integer;
         });
         if (value == null) usingItems.put(itemStack.getItem(), itemStack.getCount());
      }

      for (Map.Entry<Item, Integer> itemIntegerEntry : usingItems.entrySet()) {
         final Item item = itemIntegerEntry.getKey();
         final Integer count = itemIntegerEntry.getValue();

         if (!hasExactItems(inventory, item, count))
         {
            return false;
         }
      }

      return true;
   }

   private boolean hasExactItems(PlayerInventory inventory, Item item, int count) {
      final List<NonNullList<ItemStack>> allInventories = ImmutableList.of(inventory.mainInventory, inventory.armorInventory, inventory.offHandInventory);
      int maxCount = 0;
      for(List<ItemStack> list : allInventories) {
         for(ItemStack itemstack : list) {
            if (!itemstack.isEmpty() && itemstack.getItem().equals(item)) {
               maxCount += itemstack.getCount();
            }
         }
      }
      return maxCount >= count;
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
   public static class WrittenBookInfo implements CraftingRecipeBookScreen.IBookInfo {

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