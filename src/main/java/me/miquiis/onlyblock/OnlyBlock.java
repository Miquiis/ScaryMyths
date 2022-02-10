package me.miquiis.onlyblock;

import me.miquiis.onlyblock.common.entities.renderer.FakeExperienceOrbRenderer;
import me.miquiis.onlyblock.common.managers.BlockManager;
import me.miquiis.onlyblock.common.managers.FileManager;
import me.miquiis.onlyblock.common.registries.*;
import me.miquiis.onlyblock.common.tileentity.ModTileEntity;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.lang.ref.Reference;

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
        EffectRegister.register(modEventBus);
        ItemRegister.register(modEventBus);
        BlockRegister.register(modEventBus);
        ModTileEntity.register(modEventBus);
        EntityRegister.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        instance = this;
        OnlyBlockNetwork.init();
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
