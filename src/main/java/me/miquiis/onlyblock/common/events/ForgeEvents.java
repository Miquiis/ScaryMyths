package me.miquiis.onlyblock.common.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.gui.LavaBookScreen;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if (event.getWorld().isRemote()) return;
        if (event.getState().getBlock().equals(BlockRegister.LAVA_BLOCK.get()))
        {
            if (event.getPlayer().isCreative()) return;
            event.setCanceled(true);
            double d0 = (double)(event.getPlayer().world.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(event.getPlayer().world.rand.nextFloat() * 0.5F) + 0.5D;
            double d2 = (double)(event.getPlayer().world.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itemEntity = new ItemEntity(event.getPlayer().world, event.getPos().getX() + d0, event.getPos().getY() + d1, event.getPos().getZ() + d2, new ItemStack(Items.CHAIN));
            itemEntity.setDefaultPickupDelay();
            event.getWorld().addEntity(itemEntity);
        }
    }

}
