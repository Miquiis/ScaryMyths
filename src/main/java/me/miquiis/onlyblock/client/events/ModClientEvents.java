package me.miquiis.onlyblock.client.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.BadStockEntity;
import me.miquiis.onlyblock.common.entities.GiantEarthEntity;
import me.miquiis.onlyblock.common.entities.GoodStockEntity;
import me.miquiis.onlyblock.common.entities.renderer.*;
import me.miquiis.onlyblock.common.items.JetpackArmorItem;
import me.miquiis.onlyblock.common.items.ModSpawnEgg;
import me.miquiis.onlyblock.common.items.renderer.JetpackArmorRenderer;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = OnlyBlock.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.CUSTOM_FALLING_BLOCK.get(), CustomFallingBlockRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.GOOD_STOCK.get(), renderManager -> new SpriteRenderer<GoodStockEntity>(renderManager, Minecraft.getInstance().getItemRenderer(), 3f, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.BAD_STOCK.get(), renderManager -> new SpriteRenderer<BadStockEntity>(renderManager, Minecraft.getInstance().getItemRenderer(), 3f, true));

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.BUFFETT.get(), BuffettRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.JEFF_BEZOS.get(), JeffBezosRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.ELON_MUSK.get(), ElonMuskRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.ASTEROID.get(), AsteroidRenderer::new);

        GeoArmorRenderer.registerArmorRenderer(JetpackArmorItem.class, new JetpackArmorRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.VAN.get(), VanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.VAN_TWO.get(), VanTwoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.SEDAN.get(), SedanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.SEDAN_TWO.get(), SedanTwoRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.GIANT_EARTH.get(), GiantEarthRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.GOLDEN_HELI.get(), GoldenHelicopterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.SPACESHIP.get(), SpaceshipRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.GOLDEN_PROJECTILE.get(), GoldenProjectileRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.ONE_MIL.get(), OneMilRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.FIVE_HUNDRED.get(), FiveHundredRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.HUNDRED.get(), HundredRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.QUESTION_MARK.get(), QuestionMarkRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.STOCK_GHAST.get(), GhastRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.AMAZON_TNT.get(), AmazonTNTRenderer::new);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event)
    {
//        Minecraft.getInstance().particles.registerFactory(ParticleRegister.EXP_EXPLOSION.get(), ExpExplosionParticle.Factory::new);
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event)
    {
        ModSpawnEgg.initSpawnEggs();
    }

}
