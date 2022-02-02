package me.miquiis.onlyblock.client.gui;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.stream.Stream;

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

      public ItemStack[] getAllRows() {
         return Stream.of(firstRow, secondRow, thirdRow).flatMap(Stream::of).toArray(ItemStack[]::new);
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

      public boolean isEmpty() {
         return this.equals(NO_RECIPE);
      }
   }