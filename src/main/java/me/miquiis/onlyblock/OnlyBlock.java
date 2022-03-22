package me.miquiis.onlyblock;

import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.OnlyBlockCapability;
import me.miquiis.onlyblock.common.managers.BlockManager;
import me.miquiis.onlyblock.common.managers.FileManager;
import me.miquiis.onlyblock.common.registries.*;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.PointOfView;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(OnlyBlock.MOD_ID)
public class OnlyBlock
{
    public static final String MOD_ID = "onlyblock";

    private static OnlyBlock instance;

    private FileManager onlyBlockFolder;
    private BlockManager blockManager;

    public OnlyBlock() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::enqueueIMC);
        modEventBus.addListener(this::processIMC);

        ParticleRegister.PARTICLES.register(modEventBus);
        EntityRegister.register(modEventBus);
        ContainerRegister.register(modEventBus);
        SoundRegister.register(modEventBus);
        TileEntityRegister.register(modEventBus);
        EffectRegister.register(modEventBus);
        ItemRegister.register(modEventBus);
        BlockRegister.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        GeckoLib.initialize();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        instance = this;
        OnlyBlockNetwork.init();
        CurrencyCapability.register();
        OnlyBlockCapability.register();
        //BarManager.init();
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event)
    {
        this.onlyBlockFolder = new FileManager("onlyblock", event.getServer().getDataDirectory(), "1.1");
        this.blockManager = new BlockManager(this);
    }

    public BlockManager getBlockManager() {
        return blockManager;
    }

    public FileManager getOnlyBlockFolder() {
        return onlyBlockFolder;
    }

    public static OnlyBlock getInstance() {
        return instance;
    }
}
