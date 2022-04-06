package me.miquiis.onlyblock.client.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.MutantCreeperEntity;
import me.miquiis.onlyblock.common.entities.MutantSkeletonEntity;
import me.miquiis.onlyblock.common.entities.renderer.*;
import me.miquiis.onlyblock.common.items.MoneyArmorItem;
import me.miquiis.onlyblock.common.items.renderer.MoneyArmorRenderer;
import me.miquiis.onlyblock.common.models.*;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.registries.TileEntityRegister;
import me.miquiis.onlyblock.common.tileentity.renderer.MoneyPrinterTileRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = OnlyBlock.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event)
    {
        ClientRegistry.bindTileEntityRenderer(TileEntityRegister.MONEY_PRINTER_TILE_ENTITY.get(), MoneyPrinterTileRenderer::new);

        GeoArmorRenderer.registerArmorRenderer(MoneyArmorItem.class, new MoneyArmorRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.CUSTOM_FALLING_BLOCK.get(), CustomFallingBlockRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.LIFE_KEEPER.get(), LifeKeeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.TIME_KEEPER.get(), TimeKeeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.BUILD_KEEPER.get(), BuildKeeperRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.MUTANT_CREEPER.get(), MutantCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.MUTANT_ZOMBIE.get(), MutantZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.MUTANT_SKELETON.get(), MutantSkeletonRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.FLYING_TESLA.get(), FlyingTeslaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.TNT_PROJECTILE.get(), TNTProjectileRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.SOLD.get(), SoldRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.SALE.get(), SaleRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.ALFRED.get(), manager -> new PlayerRenderer(manager, new AlfredModel()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.BOB.get(), manager -> new PlayerRenderer(manager, new BobModel()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.DEALER.get(), manager -> new PlayerRenderer(manager, new DealerModel()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.NOOB.get(), manager -> new PlayerRenderer(manager, new NoobModel()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.HACKER.get(), manager -> new PlayerRenderer(manager, new HackerModel()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.JEFF_BEZOS.get(), manager -> new PlayerRenderer(manager, new JeffBezosModel()));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.ELON_MUSK.get(), manager -> new PlayerRenderer(manager, new ElonMuskModel()));
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
