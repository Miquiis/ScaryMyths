package me.miquiis.scarymyths;

import me.miquiis.scarymyths.common.managers.FileManager;
import me.miquiis.scarymyths.common.registries.*;
import me.miquiis.scarymyths.server.network.ModNetwork;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ScaryMyths.MOD_ID)
public class ScaryMyths
{
    public static final String MOD_ID = "scarymyths";

    private static ScaryMyths instance;

    private FileManager onlyBlockFolder;

    public ScaryMyths() {
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

        //GeckoLib.initialize();
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        instance = this;
        ModNetwork.init();
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

    }

    public static ScaryMyths getInstance() {
        return instance;
    }
}
