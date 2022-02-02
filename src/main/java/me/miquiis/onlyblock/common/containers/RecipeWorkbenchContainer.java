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
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class RecipeWorkbenchContainer extends PortableWorkbenchContainer {

   protected final CraftRecipe craftRecipe;

   public RecipeWorkbenchContainer(int id, PlayerInventory playerInventory, CraftRecipe craftRecipe) {
      super(id, playerInventory);
      this.craftRecipe = craftRecipe;
   }

   public RecipeWorkbenchContainer(int id, PlayerInventory playerInventory, IWorldPosCallable p_i50090_3_, CraftRecipe craftRecipe) {
      super(id, playerInventory, p_i50090_3_);
      this.craftRecipe = craftRecipe;
      mountRecipe();
   }

   private void mountRecipe()
   {
      int currentSlot = 0;
      for (ItemStack itemStack : craftRecipe.getAllRows())
      {
         int countRemoved = itemStack.getCount();
         int nextSlot = player.inventory.getSlotFor(itemStack);
         int amountRemoved = 0;

         if (nextSlot == -1) break;

         while (countRemoved > 0 && nextSlot != -1)
         {
            ItemStack foundItemStack = player.inventory.getStackInSlot(nextSlot);
            final int prevItemCount = foundItemStack.getCount();

            if (countRemoved >= foundItemStack.getCount())
            {
               amountRemoved += foundItemStack.getCount();
               foundItemStack.setCount(0);
            } else
            {
               amountRemoved += countRemoved;
               foundItemStack.setCount(foundItemStack.getCount() - countRemoved);
            }

            countRemoved -= prevItemCount;
            nextSlot = player.inventory.getSlotFor(itemStack);
         }

         if (countRemoved > 0)
         {
            System.out.println("Error occured, giving items back to player");
            player.inventory.addItemStackToInventory(new ItemStack(itemStack.getItem(), amountRemoved));
            return;
         }

         craftMatrix.setInventorySlotContents(currentSlot, itemStack);

         currentSlot++;
      }
   }
}