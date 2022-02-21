package me.miquiis.onlyblock.common.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.blocks.CustomBlockTags;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.containers.MinazonContainer;
import me.miquiis.onlyblock.common.entities.*;
import me.miquiis.onlyblock.common.managers.BlockManager;
import me.miquiis.onlyblock.common.registries.*;
import me.miquiis.onlyblock.common.utils.MathUtils;
import me.miquiis.onlyblock.server.commands.OnlyBlockCommand;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.OpenShopPacket;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.entity.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.play.server.STitlePacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.server.command.ConfigCommand;

import javax.annotation.Nullable;
import java.util.ArrayList;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        new OnlyBlockCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteract event)
    {
        if (event.getTarget() == null) return;
        if (event.getWorld().isRemote) return;
        if (event.getHand() == Hand.MAIN_HAND)
        {
            if (event.getEntity() != null)
            {
                OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new OpenShopPacket());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (!event.player.world.isRemote && event.player.world.getGameTime() % 10 == 0)
        {
//            event.player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
//                iCurrency.addOrSubtractAmount(1);
//            });
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
        event.getOriginal().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(oldCurrency -> {
            event.getPlayer().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
                iCurrency.setAmount(oldCurrency.getAmount(), false);
            });
        });
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        event.getPlayer().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
            iCurrency.sync((ServerPlayerEntity) event.getPlayer());
        });
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            event.getPlayer().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
                iCurrency.sync((ServerPlayerEntity)event.getPlayer());
            });
        }
    }

    @SubscribeEvent
    public static void changeDimesionEvent(final PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().world.isRemote)
        {
            event.getPlayer().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
                iCurrency.sync((ServerPlayerEntity)event.getPlayer());
            });
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
//        if (event.getWorld().isRemote) return;
//        if (!event.getWorld().isAreaLoaded(event.getEntity().getPosition(), 1)) return;
//        if (event.getEntity() instanceof IXPMob) return;
//        final ServerWorld serverWorld = (ServerWorld) event.getWorld();
//
//        if (event.getEntity() instanceof ZombieEntity)
//        {
//            event.setCanceled(true);
//            XPZombieEntity zombieEntity = new XPZombieEntity(event.getWorld());
//            zombieEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(event.getEntity().getPosition()), SpawnReason.NATURAL, null, null);
//            zombieEntity.setPositionAndRotation(event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), event.getEntity().rotationYaw, event.getEntity().rotationPitch);
//            event.getWorld().addEntity(zombieEntity);
//            return;
//        }
//
//        if (event.getEntity() instanceof CreeperEntity)
//        {
//            event.setCanceled(true);
//            XPCreeperEntity customEntity = new XPCreeperEntity(event.getWorld());
//            customEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(event.getEntity().getPosition()), SpawnReason.NATURAL, null, null);
//            customEntity.setPositionAndRotation(event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), event.getEntity().rotationYaw, event.getEntity().rotationPitch);
//            event.getWorld().addEntity(customEntity);
//            return;
//        }
//
//        if (event.getEntity() instanceof SkeletonEntity)
//        {
//            event.setCanceled(true);
//            XPSkeletonEntity customEntity = new XPSkeletonEntity(event.getWorld());
//            customEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(event.getEntity().getPosition()), SpawnReason.NATURAL, null, null);
//            customEntity.setPositionAndRotation(event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), event.getEntity().rotationYaw, event.getEntity().rotationPitch);
//            event.getWorld().addEntity(customEntity);
//            return;
//        }
//
//        if (event.getEntity() instanceof EndermanEntity)
//        {
//            event.setCanceled(true);
//            XPEndermanEntity customEntity = new XPEndermanEntity(event.getWorld());
//            customEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(event.getEntity().getPosition()), SpawnReason.NATURAL, null, null);
//            customEntity.setPositionAndRotation(event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), event.getEntity().rotationYaw, event.getEntity().rotationPitch);
//            event.getWorld().addEntity(customEntity);
//            return;
//        }
//
//        if (event.getEntity() instanceof SpiderEntity)
//        {
//            event.setCanceled(true);
//            XPSpiderEntity customEntity = new XPSpiderEntity(event.getWorld());
//            customEntity.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(event.getEntity().getPosition()), SpawnReason.NATURAL, null, null);
//            customEntity.setPositionAndRotation(event.getEntity().getPosX(), event.getEntity().getPosY(), event.getEntity().getPosZ(), event.getEntity().rotationYaw, event.getEntity().rotationPitch);
//            event.getWorld().addEntity(customEntity);
//            return;
//        }
    }

    public static void spawnItem(World worldIn, BlockPos pos, ItemStack stack) {
        if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
            float f = 0.5F;
            double d0 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double)(worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX() + d0, (double)pos.getY() + d1, (double)pos.getZ() + d2, stack);
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
        }
    }
}
