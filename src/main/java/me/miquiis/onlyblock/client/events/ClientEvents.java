package me.miquiis.onlyblock.client.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.gui.LavaBookScreen;
import me.miquiis.onlyblock.common.entities.renderer.FakeExperienceOrbRenderer;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

//    @SubscribeEvent
//    public static void renderUI(RenderGameOverlayEvent.Text event)
//    {
//        if (Minecraft.getInstance().currentScreen instanceof LavaBookScreen)
//        {
//            return;
//        }
//
//        Minecraft.getInstance().displayGuiScreen(new LavaBookScreen(new LavaBookScreen.WrittenBookInfo(
//                new ArrayList<LavaBookScreen.CraftRecipe>(){{
//                    add(new LavaBookScreen.CraftRecipe("Test Title",
//                        new ItemStack[]{new ItemStack(Items.CHAIN), new ItemStack(Items.OAK_BOAT), new ItemStack(Items.LODESTONE)},
//                        new ItemStack[]{new ItemStack(Items.DIAMOND), new ItemStack(Items.GOLDEN_AXE), new ItemStack(Items.SWEET_BERRIES)},
//                        new ItemStack[]{new ItemStack(Items.BAKED_POTATO), new ItemStack(Items.RED_BANNER), new ItemStack(Items.REDSTONE)},
//                        new ItemStack(Items.ITEM_FRAME)
//                    ));
//                    add(new LavaBookScreen.CraftRecipe("Test",
//                        new ItemStack[]{new ItemStack(Items.CHAIN), new ItemStack(Items.OAK_BOAT), new ItemStack(Items.LODESTONE)},
//                        new ItemStack[]{new ItemStack(Items.DIAMOND), new ItemStack(Items.GOLDEN_AXE), new ItemStack(Items.SWEET_BERRIES)},
//                        new ItemStack[]{new ItemStack(Items.BAKED_POTATO), new ItemStack(Items.RED_BANNER), new ItemStack(Items.REDSTONE)},
//                        new ItemStack(Items.ITEM_FRAME)
//                    ));
//                }}
//        )));
//    }

}
