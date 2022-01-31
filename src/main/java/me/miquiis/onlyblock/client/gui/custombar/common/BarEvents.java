package me.miquiis.onlyblock.client.gui.custombar.common;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.gui.custombar.client.BarOverlay;
import me.miquiis.onlyblock.client.gui.custombar.server.BarSave;
import me.miquiis.onlyblock.client.gui.custombar.server.commands.CustomBarCommand;
import me.miquiis.onlyblock.server.commands.OnlyBlockCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID)
public class BarEvents {

    private static Minecraft client;

    private static Minecraft getClient()
    {
        if (client == null) client = Minecraft.getInstance();
        return client;
    }

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        new CustomBarCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onGUIRender(RenderGameOverlayEvent.Text event)
    {
        Minecraft client = getClient();
        BarManager.getCurrentActiveBars().forEach((uuid, barInfo) -> {
            new BarOverlay(client, barInfo).draw(event.getMatrixStack());
        });
    }

    @SubscribeEvent
    public static void onWorldSave(WorldEvent.Save event)
    {
        if (!event.getWorld().isRemote())
        {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            BarSave barSave = BarSave.forWorld(serverWorld);
            barSave.saveBars(BarManager.getCurrentActiveBars().values());
            barSave.markDirty();
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event)
    {
        if (!event.getWorld().isRemote())
        {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();
            BarSave barSave = BarSave.forWorld(serverWorld);
            barSave.loadBars().forEach(BarManager::addBar);
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(WorldEvent.Unload event)
    {
        BarManager.clearBars();
    }

}
