package me.miquiis.onlyblock.common.containers;

import me.miquiis.onlyblock.client.gui.CraftRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.RecipeBookContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.*;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;

public class RecipeWorkbenchContainer extends PortableWorkbenchContainer {

   protected final CraftRecipe craftRecipe;
   protected final boolean isShiftClick;

   public RecipeWorkbenchContainer(int id, PlayerInventory playerInventory, CraftRecipe craftRecipe, boolean isShiftClick) {
      super(id, playerInventory);
      this.craftRecipe = craftRecipe;
      this.isShiftClick = isShiftClick;
   }

   public RecipeWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable p_i50090_3_, CraftRecipe craftRecipe, boolean isShiftClick) {
      super(id, playerInventory, p_i50090_3_);
      this.craftRecipe = craftRecipe;
      this.isShiftClick = isShiftClick;
      mountRecipe();
   }

   private ItemStack getItemInInventory(ItemStack item)
   {
      if (item.getCount() > 64) return null;
      if (player.inventory.count(item.getItem()) < item.getCount()) return null;

      int currentSlot = player.inventory.getSlotFor(item);
      int missingQuantity = item.getCount();
      while (currentSlot != -1 && missingQuantity > 0)
      {
         final ItemStack foundItem = player.inventory.getStackInSlot(currentSlot);

         if (foundItem.getCount() > missingQuantity)
         {
            foundItem.setCount(foundItem.getCount() - item.getCount());
            return item;
         } else
         {
            missingQuantity -= foundItem.getCount();
            foundItem.setCount(0);
         }

         if (missingQuantity < 0)
         {
            System.out.println("Item Error, please look up code");
         }

         currentSlot = player.inventory.getSlotFor(item);
      }

      return item;
   }

   private int amountCanCraft(CraftRecipe craftRecipe)
   {
      Map<Item, Integer> ingredientsFound = new HashMap<>();
      int canCraft = 0;

      for (ItemStack ingredient : craftRecipe.getAllRows())
      {
         final int foundItems = player.inventory.count(ingredient.getItem());
         Object result = ingredientsFound.computeIfPresent(ingredient.getItem(), (item, integer) -> integer + foundItems);
         if (result == null)
         {
            ingredientsFound.put(ingredient.getItem(), foundItems);
         }
      }

      int[] results = new int[9];
      int currentResult = 0;
      for (ItemStack ingredient : craftRecipe.getAllRows())
      {
         //results[currentResult] = ingredientsFound.get(ingredient.getItem()) / ingredient.
         currentResult++;
      }

      return canCraft;
   }

   private void mountRecipe()
   {
      boolean shouldBreak = false;
      int maxedCount = 0;

      while (!shouldBreak && maxedCount < 8)
      {
         int currentSlot = 0;
         for (ItemStack ingredient : craftRecipe.getAllRows())
         {
            final ItemStack currentIngredient = craftMatrix.getStackInSlot(currentSlot);
            final int currentIngredientAmountToMax = currentIngredient.getMaxStackSize() - currentIngredient.getCount();
            final boolean isCurrentIngredientMaxed = !currentIngredient.isEmpty() && currentIngredientAmountToMax == 0;

            if (isCurrentIngredientMaxed)
            {
               maxedCount++;
               currentSlot++;
               continue;
            }

            if (currentIngredient.isEmpty())
            {
               if (getItemInInventory(ingredient) != null)
               {
                  craftMatrix.setInventorySlotContents(currentSlot, ingredient.copy());
               } else {
                  shouldBreak = true;
               }

               currentSlot++;
               continue;
            }

            if (ingredient.getCount() > currentIngredientAmountToMax)
            {
               if(getItemInInventory(new ItemStack(ingredient.getItem(), currentIngredientAmountToMax)) != null)
               {
                  currentIngredient.setCount(currentIngredient.getMaxStackSize());
               } else
               {
                  shouldBreak = true;
               }
            }
            else
            {
               if(getItemInInventory(ingredient) != null)
               {
                  currentIngredient.setCount(currentIngredient.getCount() + ingredient.getCount());
               } else
               {
                  shouldBreak = true;
               }
            }

            currentSlot++;
         }
      }
   }
}