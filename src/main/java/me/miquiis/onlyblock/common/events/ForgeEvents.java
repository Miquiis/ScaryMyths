package me.miquiis.onlyblock.common.events;

import me.miquiis.custombar.common.BarInfo;
import me.miquiis.custombar.common.BarManager;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.entities.MutantCreeperEntity;
import me.miquiis.onlyblock.common.entities.MutantSkeletonEntity;
import me.miquiis.onlyblock.common.entities.MutantZombieEntity;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import me.miquiis.onlyblock.common.utils.TitleUtils;
import me.miquiis.onlyblock.server.commands.OnlyBlockCommand;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.ATMPacket;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.server.command.ConfigCommand;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        new OnlyBlockCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    private static List<LivingEntity> spawnedEntities = new ArrayList<>();
    private static boolean firstSpawn = true;
    private static int getCurrentZombies() { return (int)spawnedEntities.stream().filter(lW -> lW != null && lW.isAlive() && lW instanceof MutantZombieEntity).count(); }
    private static int getCurrentSkeletons() { return (int)spawnedEntities.stream().filter(lW -> lW != null && lW.isAlive() && lW instanceof MutantSkeletonEntity).count(); }
    private static int getCurrentCreepers() { return (int)spawnedEntities.stream().filter(lW -> lW != null && lW.isAlive() && lW instanceof MutantCreeperEntity).count(); }
    private static int getCurrentRavangers() { return (int)spawnedEntities.stream().filter(lW -> lW!= null && lW.isAlive() && lW instanceof RavagerEntity).count(); }
    private static int getCurrentWithers() { return (int)spawnedEntities.stream().filter(lW -> lW != null && lW.isAlive() && lW instanceof WitherEntity).count(); }

    @SubscribeEvent
    public static void onServerTick(TickEvent.WorldTickEvent event)
    {
        if (!event.world.isRemote && event.phase == TickEvent.Phase.END && event.world.getDimensionKey().getLocation().toString().contains("overworld"))
        {
            spawnedEntities.removeIf(lW -> lW == null || !lW.isAlive() || lW.removed || event.world.getEntityByID(lW.getEntityId()) == null);
            if (firstSpawn || event.world.getGameTime() % (20 * 30) == 0)
            {
                if (getCurrentZombies() < 10)
                {
                    int amountToSpawn = 10 - getCurrentZombies();
                    for (int i = 0; i < amountToSpawn; i++)
                    {
                        Vector3d rand = getRandomSpawnPosition();
                        MutantZombieEntity mutantZombieEntity = new MutantZombieEntity(event.world);
                        mutantZombieEntity.setPosition(rand.getX(), rand.getY(), rand.getZ());
                        mutantZombieEntity.enablePersistence();
                        event.world.addEntity(mutantZombieEntity);
                        spawnedEntities.add(mutantZombieEntity);
                    }
                }
            }

            if (firstSpawn || event.world.getGameTime() % (20 * 45) == 0)
            {
                if (getCurrentSkeletons() < 6)
                {
                    int amountToSpawn = 6 - getCurrentSkeletons();
                    for (int i = 0; i < amountToSpawn; i++)
                    {
                        Vector3d rand = getRandomSpawnPosition();
                        MutantSkeletonEntity mutantZombieEntity = new MutantSkeletonEntity(event.world);
                        mutantZombieEntity.setPosition(rand.getX(), rand.getY(), rand.getZ());
                        mutantZombieEntity.enablePersistence();
                        event.world.addEntity(mutantZombieEntity);
                        spawnedEntities.add(mutantZombieEntity);
                    }
                }
            }

            if (firstSpawn || event.world.getGameTime() % (20 * 60) == 0)
            {
                if (getCurrentCreepers() < 3)
                {
                    int amountToSpawn = 3 - getCurrentCreepers();
                    for (int i = 0; i < amountToSpawn; i++)
                    {
                        Vector3d rand = getRandomSpawnPosition();
                        MutantCreeperEntity mutantZombieEntity = new MutantCreeperEntity(event.world);
                        mutantZombieEntity.setPosition(rand.getX(), rand.getY(), rand.getZ());
                        mutantZombieEntity.enablePersistence();
                        event.world.addEntity(mutantZombieEntity);
                        spawnedEntities.add(mutantZombieEntity);
                    }
                }
            }

            if (firstSpawn || event.world.getGameTime() % (20 * 60 * 5) == 0)
            {
                if (getCurrentRavangers() < 2)
                {
                    int amountToSpawn = 2 - getCurrentRavangers();
                    for (int i = 0; i < amountToSpawn; i++)
                    {
                        Vector3d rand = getRandomSpawnPosition();
                        RavagerEntity mutantZombieEntity = new RavagerEntity(EntityType.RAVAGER, event.world);
                        mutantZombieEntity.setPosition(rand.getX(), rand.getY(), rand.getZ());
                        mutantZombieEntity.enablePersistence();
                        mutantZombieEntity.onInitialSpawn((ServerWorld)event.world, event.world.getDifficultyForLocation(new BlockPos(rand)), SpawnReason.MOB_SUMMONED, null, null);
                        event.world.addEntity(mutantZombieEntity);
                        spawnedEntities.add(mutantZombieEntity);
                    }
                }
            }

            if (firstSpawn || event.world.getGameTime() % (20 * 60 * 10) == 0)
            {
                if (getCurrentWithers() < 1)
                {
                    int amountToSpawn = 1 - getCurrentWithers();
                    for (int i = 0; i < amountToSpawn; i++)
                    {
                        Vector3d rand = getRandomSpawnPosition();
                        WitherEntity mutantZombieEntity = new WitherEntity(EntityType.WITHER, event.world);
                        mutantZombieEntity.setPosition(rand.getX(), rand.getY(), rand.getZ());
                        mutantZombieEntity.enablePersistence();
                        mutantZombieEntity.onInitialSpawn((ServerWorld)event.world, event.world.getDifficultyForLocation(new BlockPos(rand)), SpawnReason.MOB_SUMMONED, null, null);
                        event.world.addEntity(mutantZombieEntity);
                        spawnedEntities.add(mutantZombieEntity);
                    }
                }
            }

            firstSpawn = false;
        }

        if (!event.world.isRemote && event.phase == TickEvent.Phase.END && event.world.getGameTime() % 10 == 0 && event.world.getDimensionKey().getLocation().toString().contains("overworld"))
        {
            IWorldOnlyMoneyBlock onlyMoneyBlock = WorldOnlyMoneyBlock.getCapability(event.world);
            onlyMoneyBlock.getMcDonaldsBusiness().checkForFlyingText((ServerWorld)event.world);
            onlyMoneyBlock.getAmazonBusiness().checkForFlyingText((ServerWorld)event.world);
            onlyMoneyBlock.getTeslaBusiness().checkForFlyingText((ServerWorld)event.world);
        }
    }

    final static Vector3d CENTER_SPAWN = new Vector3d(4, 66, 2);
    private static Vector3d getRandomSpawnPosition() {
        return CENTER_SPAWN.add(MathUtils.getRandomMinMax(-20, 20), 0, MathUtils.getRandomMinMax(-20, 20));
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        if (!event.getEntity().world.isRemote && event.getSource().getTrueSource() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)event.getSource().getTrueSource();
            if (event.getEntity() instanceof MutantZombieEntity) {
                OnlyMoneyBlock.getCapability(player).sumCash(2000);
            } else if (event.getEntity() instanceof MutantSkeletonEntity) {
                OnlyMoneyBlock.getCapability(player).sumCash(5000);
            } else if (event.getEntity() instanceof MutantCreeperEntity) {
                OnlyMoneyBlock.getCapability(player).sumCash(1000);
            } else if (event.getEntity() instanceof RavagerEntity) {
                OnlyMoneyBlock.getCapability(player).sumCash(25000);
            } else if (event.getEntity() instanceof WitherEntity) {
                OnlyMoneyBlock.getCapability(player).sumCash(100000);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClick(PlayerInteractEvent.RightClickBlock event)
    {
        if (!event.getWorld().isRemote && event.getHand() == Hand.MAIN_HAND)
        {
            BlockState blockState = event.getWorld().getBlockState(event.getPos());
            if (blockState.getBlock() == BlockRegister.ATM.get())
            {
                OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) event.getPlayer()), new ATMPacket(ATMPacket.ATMPacketType.SEND_ATM, ATMPacket.createSendATMData(-1)));
            }
        }
    }

    private static final ResourceLocation WAVE_BAR = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/wave_bar.png");
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event)
    {

        if (!event.player.world.isRemote && event.phase == TickEvent.Phase.END)
        {
            IOnlyMoneyBlock onlyMoneyBlock = OnlyMoneyBlock.getCapability(event.player);
            IWorldOnlyMoneyBlock worldOnlyMoneyBlock = WorldOnlyMoneyBlock.getCapability(event.player.world);
            if (worldOnlyMoneyBlock.getMcDonaldsBusiness().getBusinessOwner() == null) {
                event.player.addPotionEffect(new EffectInstance(Effects.SATURATION, 20, 0, false, false));
            }

            if (event.player.world.getGameTime() % 1200 == 0 && event.player.getUniqueID().equals(worldOnlyMoneyBlock.getMcDonaldsBusiness().getBusinessOwner()))
            {
                onlyMoneyBlock.sumCash(4000);
            }

            if (event.player.world.getGameTime() % 1200 == 0 && event.player.getUniqueID().equals(worldOnlyMoneyBlock.getAmazonBusiness().getBusinessOwner()))
            {
                onlyMoneyBlock.sumCash(4000);
            }

            if (event.player.world.getGameTime() % 1200 == 0 && event.player.getUniqueID().equals(worldOnlyMoneyBlock.getTeslaBusiness().getBusinessOwner()))
            {
                onlyMoneyBlock.sumCash(4000);
            }
        }

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
            OnlyMoneyBlock.getCapability(event.getPlayer()).sync(false);
            WorldOnlyMoneyBlock.getCapability(event.getPlayer().world).sync((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {
            OnlyMoneyBlock.getCapability(event.getPlayer()).sync(true);
            WorldOnlyMoneyBlock.getCapability(event.getPlayer().world).sync((ServerPlayerEntity) event.getPlayer());
        }
    }

    @SubscribeEvent
    public static void changeDimesionEvent(final PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().world.isRemote)
        {
            OnlyMoneyBlock.getCapability(event.getPlayer()).sync(false);
            WorldOnlyMoneyBlock.getCapability(event.getPlayer().world).sync((ServerPlayerEntity) event.getPlayer());
        }
    }

}
