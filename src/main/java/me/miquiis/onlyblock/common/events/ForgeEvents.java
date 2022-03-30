package me.miquiis.onlyblock.common.events;

import me.miquiis.custombar.common.BarInfo;
import me.miquiis.custombar.common.BarManager;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyBlock;
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
            IWorldOnlyBlock onlyBlock = WorldOnlyBlock.getCapability(event.player.world);

            BarInfo currentWave = BarManager.getBarInfoByID("wave");
            if (!onlyBlock.hasWaveStarted())
            {
                if (currentWave != null) BarManager.removeBar(currentWave.getUniqueID());
            } else
            {
                if (currentWave == null) BarManager.addBar(UUID.randomUUID(), "wave", new StringTextComponent("\u00A7c\u00A7lMobs Left: " + onlyBlock.getMobsLeft()), onlyBlock.getMobsPercentage(), (ServerPlayerEntity) event.player, WAVE_BAR, new int[]{255, 85, 85}, false);
                else {
                    BarManager.updateBar(currentWave.getUniqueID(), (ServerPlayerEntity) event.player, onlyBlock.getMobsPercentage(), new StringTextComponent("\u00A7c\u00A7lMobs Left: " + onlyBlock.getMobsLeft()), WAVE_BAR, currentWave.getRawColor());
                }
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerUseClock(PlayerInteractEvent.RightClickItem event)
    {
        if (!event.getWorld().isRemote && event.getHand() == Hand.MAIN_HAND)
        {
            ItemStack itemUsed = event.getItemStack();
            if (itemUsed.getOrCreateTag().contains("TimeForward"))
            {
                int amountForwared = itemUsed.getOrCreateTag().getInt("TimeForward");
                IWorldOnlyBlock worldOnlyBlock = WorldOnlyBlock.getCapability(event.getWorld());
                worldOnlyBlock.setDaysLeft(worldOnlyBlock.getDaysLeft() - amountForwared);
                ((ServerWorld)event.getWorld()).setDayTime(1);
                event.getWorld().getServer().getPlayerList().getPlayers().forEach(player -> {
                    TitleUtils.sendTitleToPlayer(player, "\u00A76\u00A7lTime Forwarded", "\u00A7fTime has been forwared by " + amountForwared + " days.", 20, 100, 20);
                });
                itemUsed.shrink(1);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerPressurePlate(TickEvent.PlayerTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote)
        {
            if (event.player.world.getBlockState(event.player.getPosition()).getBlock().getRegistryName().toString().contains("pressure_plate"))
            {
                IWorldOnlyBlock worldOnlyBlock = WorldOnlyBlock.getCapability(event.player.world);
                if (worldOnlyBlock.hasActivatedWaves()) return;
                worldOnlyBlock.activateWaves();
                worldOnlyBlock.sync();
                event.player.getServer().getPlayerList().getPlayers().forEach(player -> {
                    TitleUtils.sendTitleToPlayer(player, "&c&lTrap Activated", "&f&lWaves are now unleashed...", 20, 100, 20);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onDayChange(TickEvent.WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START && event.world.getDimensionKey().getLocation().toString().contains("overworld"))
        {
            if (event.world.getDayTime() % 24000 == 0)
            {
                WorldOnlyBlock.getCapability(event.world).skipDay();
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTickEvent(TickEvent.WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.END && !event.world.isRemote && event.world.getDimensionKey().getLocation().toString().contains("overworld"))
        {
            IWorldOnlyBlock worldOnlyBlock = WorldOnlyBlock.getCapability(event.world);
            if (worldOnlyBlock.getNextIronDropTime() == 1)
            {
                ItemEntity itemEntity = new ItemEntity(event.world, worldOnlyBlock.getIronGenerator().getX() + 0.5, worldOnlyBlock.getIronGenerator().getY() + 2, worldOnlyBlock.getIronGenerator().getZ() + 0.5);
                //itemEntity.setVelocity(0, 0, 0);
                itemEntity.setMotion(0, 0, 0);
                itemEntity.setItem(new ItemStack(Items.IRON_INGOT));
                itemEntity.setNoDespawn();
                event.world.addEntity(itemEntity);
            }

            if (worldOnlyBlock.getNextGoldDropTime() == 1)
            {
                ItemEntity itemEntity = new ItemEntity(event.world, worldOnlyBlock.getGoldGenerator().getX() + 0.5, worldOnlyBlock.getGoldGenerator().getY() + 2, worldOnlyBlock.getGoldGenerator().getZ() + 0.5);
                itemEntity.setMotion(0, 0, 0);
                itemEntity.setItem(new ItemStack(Items.GOLD_INGOT));
                itemEntity.setNoDespawn();
                event.world.addEntity(itemEntity);
            }

            if (worldOnlyBlock.getNextDiamondDropTime() == 1)
            {
                ItemEntity itemEntity = new ItemEntity(event.world, worldOnlyBlock.getDiamondGenerator().getX() + 0.5, worldOnlyBlock.getDiamondGenerator().getY() + 2, worldOnlyBlock.getDiamondGenerator().getZ() + 0.5);
                itemEntity.setMotion(0, 0, 0);
                itemEntity.setItem(new ItemStack(Items.DIAMOND));
                itemEntity.setNoDespawn();
                event.world.addEntity(itemEntity);
            }

            if (worldOnlyBlock.getNextEmeraldDropTime() == 1)
            {
                ItemEntity itemEntity = new ItemEntity(event.world, worldOnlyBlock.getEmeraldGenerator().getX() + 0.5, worldOnlyBlock.getEmeraldGenerator().getY() + 2, worldOnlyBlock.getEmeraldGenerator().getZ() + 0.5);
                itemEntity.setMotion(0, 0, 0);
                itemEntity.setItem(new ItemStack(Items.EMERALD));
                itemEntity.setNoDespawn();
                event.world.addEntity(itemEntity);
            }

        }
    }

}
