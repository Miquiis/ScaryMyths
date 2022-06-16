package me.miquiis.scarymyths.client.events;

import me.miquiis.scarymyths.ScaryMyths;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ScaryMyths.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event)
    {

    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event)
    {
//        Minecraft.getInstance().particles.registerFactory(ParticleRegister.EXP_EXPLOSION.get(), ExpExplosionParticle.Factory::new);
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event)
    {
    }

}
