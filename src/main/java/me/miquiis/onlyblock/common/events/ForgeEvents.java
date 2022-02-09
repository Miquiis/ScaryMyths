package me.miquiis.onlyblock.common.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.server.commands.OnlyBlockCommand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Hand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        new OnlyBlockCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if (event.getWorld().isRemote()) return;
        if (event.getState().getBlock().equals(BlockRegister.LAVA_BLOCK.get()))
        {
            OnlyBlock.getInstance().getBlockManager().onLavaBlockBreak(event);
        }
    }

    @SubscribeEvent
    public static void onBlockUse(PlayerInteractEvent event)
    {
        if (event.getHand() != Hand.MAIN_HAND) return;
        if (event.getWorld().isRemote()) return;
        final BlockState block = event.getWorld().getBlockState(event.getPos());
        if (block.getBlock().equals(BlockRegister.XP_BLOCK.get()))
        {
            OnlyBlock.getInstance().getBlockManager().onXPInteractEvent(event);
        }
    }
}
