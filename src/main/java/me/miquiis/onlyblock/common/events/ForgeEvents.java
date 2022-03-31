package me.miquiis.onlyblock.common.events;

import me.miquiis.custombar.common.BarInfo;
import me.miquiis.custombar.common.BarManager;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.utils.TitleUtils;
import me.miquiis.onlyblock.server.commands.OnlyBlockCommand;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        new OnlyBlockCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    private static final ResourceLocation WAVE_BAR = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/wave_bar.png");
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (!event.player.world.isRemote)
        {
//            IWorldOnlyBlock onlyBlock = WorldOnlyBlock.getCapability(event.player.world);
//
//            BarInfo currentWave = BarManager.getBarInfoByID("wave");
//            if (!onlyBlock.hasWaveStarted())
//            {
//                if (currentWave != null) BarManager.removeBar(currentWave.getUniqueID());
//            } else
//            {
//                if (currentWave == null) BarManager.addBar(UUID.randomUUID(), "wave", new StringTextComponent("\u00A7c\u00A7lMobs Left: " + onlyBlock.getMobsLeft()), onlyBlock.getMobsPercentage(), (ServerPlayerEntity) event.player, WAVE_BAR, new int[]{255, 85, 85}, false);
//                else {
//                    BarManager.updateBar(currentWave.getUniqueID(), (ServerPlayerEntity) event.player, onlyBlock.getMobsPercentage(), new StringTextComponent("\u00A7c\u00A7lMobs Left: " + onlyBlock.getMobsLeft()), WAVE_BAR, currentWave.getRawColor());
//                }
//            }

        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        OnlyMoneyBlock.getCapability(event.getPlayer()).deserializeNBT(OnlyMoneyBlock.getCapability(event.getOriginal()).serializeNBT());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            OnlyMoneyBlock.getCapability(event.getPlayer()).sync();
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            OnlyMoneyBlock.getCapability(event.getPlayer()).sync();
        }
    }

    @SubscribeEvent
    public static void changeDimesionEvent(final PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().world.isRemote)
        {
            OnlyMoneyBlock.getCapability(event.getPlayer()).sync();
        }
    }

}
