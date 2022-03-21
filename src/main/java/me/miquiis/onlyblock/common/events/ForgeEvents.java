package me.miquiis.onlyblock.common.events;

import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.custombar.common.BarInfo;
import me.miquiis.custombar.common.BarManager;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.blocks.CustomBlockTags;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.containers.MinazonContainer;
import me.miquiis.onlyblock.common.entities.*;
import me.miquiis.onlyblock.common.managers.BlockManager;
import me.miquiis.onlyblock.common.quests.BreakHundredBlocksQuest;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
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
import net.minecraft.util.ResourceLocation;
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
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
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
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        new OnlyBlockCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event)
    {
        if (event.getWorld().isRemote) return;
        if (event.getHand() == Hand.MAIN_HAND)
        {
            BlockState blockState = event.getWorld().getBlockState(event.getPos());
            if (blockState.getBlock() == BlockRegister.LAPTOP.get())
            {
                OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new OpenShopPacket());
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event)
    {
        if (!event.getWorld().isRemote()){
            IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(event.getPlayer());
            if (onlyBlock.getCurrentQuest() != null && onlyBlock.getCurrentQuest() instanceof BreakHundredBlocksQuest)
            {
                BreakHundredBlocksQuest hundredBlocksQuest = (BreakHundredBlocksQuest) onlyBlock.getCurrentQuest();
                hundredBlocksQuest.onBlockBreak(event);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerDropItem(ItemTossEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(event.getPlayer());
            if (onlyBlock.getAmazonIsland().getCurrentDelivery() != null)
            {
                Vector3d delivery = onlyBlock.getAmazonIsland().getCurrentDelivery();
                if (event.getPlayer().getPositionVec().distanceTo(delivery) <= 5)
                {
                    event.getPlayer().world.playSound(null, event.getEntityItem().getPosX(), event.getEntityItem().getPosY(), event.getEntityItem().getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.5f, 1f);
                    event.getPlayer().world.playSound(null, event.getPlayer().getPosX(), event.getPlayer().getPosY(), event.getPlayer().getPosZ(), SoundRegister.KATCHING.get(), SoundCategory.PLAYERS, 0.5f, 1f);
                    event.getEntityItem().remove();
                    onlyBlock.getAmazonIsland().deliver();
                    onlyBlock.sync((ServerPlayerEntity)event.getPlayer());
                }
            }
        }
    }

    private static final ResourceLocation DELIVER_BAR = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/deliver_amount_bar.png");
    private static final ResourceLocation QUEST_BAR = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/quest_bar.png");
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {
        if (!event.player.world.isRemote)
        {
            IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(event.player);

            BarInfo barInfo = BarManager.getBarInfoByID("deliver");
            if (onlyBlock.getAmazonIsland().getCurrentDelivery() == null)
            {
                if (barInfo != null) BarManager.removeBar(barInfo.getUniqueID());
            } else
            {
                if (barInfo == null) BarManager.addBar(UUID.randomUUID(), "deliver", new StringTextComponent("\u00A7lCollected Amount: $" + onlyBlock.getAmazonIsland().getAmountGathered()), onlyBlock.getAmazonIsland().getPercentage(), (ServerPlayerEntity) event.player, DELIVER_BAR, new int[]{0, 236, 65}, false);
                else {
                    BarManager.updateBar(barInfo.getUniqueID(), (ServerPlayerEntity) event.player, onlyBlock.getAmazonIsland().getPercentage(), new StringTextComponent("\u00A7lCollected Amount: $" + onlyBlock.getAmazonIsland().getAmountGathered()), DELIVER_BAR, barInfo.getRawColor());
                }
            }

            BarInfo questBarInfo = BarManager.getBarInfoByID("quest");
            if (onlyBlock.getCurrentQuest() == null)
            {
                if (questBarInfo != null) BarManager.removeBar(questBarInfo.getUniqueID());
            } else
            {
                if (questBarInfo == null) BarManager.addBar(UUID.randomUUID(), "quest", new StringTextComponent(onlyBlock.getCurrentQuest().getTitle()), onlyBlock.getCurrentQuest().getProgress(), (ServerPlayerEntity) event.player, QUEST_BAR, new int[]{235, 235, 52}, false);
                else {
                    BarManager.updateBar(questBarInfo.getUniqueID(), (ServerPlayerEntity) event.player, onlyBlock.getCurrentQuest().getProgress(), new StringTextComponent(onlyBlock.getCurrentQuest().getTitle()), QUEST_BAR, questBarInfo.getRawColor());
                }
            }

            if (onlyBlock.getAmazonIsland() != null)
            {
                if (!onlyBlock.getAmazonIsland().isLocked())
                {
                    if (event.player.world.getGameTime() % 20 == 0)
                    {
                        for (int i = 0; i < 5; i++)
                        {
                            Vector3d pos = onlyBlock.getAmazonIsland().getRandomTNTLocation();
                            AmazonTNTEntity amazonTNTEntity = new AmazonTNTEntity(event.player.world, pos.getX(), pos.getY(), pos.getZ(), null);
                            event.player.world.addEntity(amazonTNTEntity);
                        }
                    }
                }
            }
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
        OnlyBlockModel.getCapability(event.getPlayer()).deserializeNBT(OnlyBlockModel.getCapability(event.getOriginal()).serializeNBT());
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            event.getPlayer().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
                iCurrency.sync((ServerPlayerEntity) event.getPlayer());
            });
            OnlyBlockModel.getCapability(event.getPlayer()).sync((ServerPlayerEntity)event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            event.getPlayer().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
                iCurrency.sync((ServerPlayerEntity)event.getPlayer());
            });
            OnlyBlockModel.getCapability(event.getPlayer()).sync((ServerPlayerEntity)event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void changeDimesionEvent(final PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().world.isRemote)
        {
            event.getPlayer().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
                iCurrency.sync((ServerPlayerEntity)event.getPlayer());
            });
            OnlyBlockModel.getCapability(event.getPlayer()).sync((ServerPlayerEntity)event.getPlayer());
        }
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
