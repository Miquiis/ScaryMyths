package me.miquiis.onlyblock.client.recipes;

import me.miquiis.onlyblock.client.gui.CraftRecipe;
import me.miquiis.onlyblock.client.gui.LavaBookScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class DiamondBlockLBR extends CraftRecipe {
    public DiamondBlockLBR() {
        super("Diamond Block",
                new ItemStack[]{
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.DIAMOND)
                },
                new ItemStack[]{
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.DIAMOND)
                },
                new ItemStack[]{
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.DIAMOND)
                },
                new ItemStack(Items.DIAMOND_BLOCK, 1)
        );
    }
}
