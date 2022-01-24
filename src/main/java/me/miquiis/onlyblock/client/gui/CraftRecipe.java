package me.miquiis.onlyblock.client.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
   public class CraftRecipe {

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