package me.miquiis.onlyblock;

import me.miquiis.custombar.common.BarManager;
import me.miquiis.onlyblock.common.capability.OnlyMoneyBlockCapability;
import me.miquiis.onlyblock.common.capability.WorldOnlyMoneyBlockCapability;
import me.miquiis.onlyblock.common.registries.*;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
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
        WorldOnlyMoneyBlockCapability.register();
        OnlyMoneyBlockCapability.register();
        BarManager.init();
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

    public static OnlyBlock getInstance() {
        return instance;
    }
}
