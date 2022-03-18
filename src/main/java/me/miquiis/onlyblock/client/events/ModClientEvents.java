package me.miquiis.onlyblock.client.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.BadStockEntity;
import me.miquiis.onlyblock.common.entities.GoodStockEntity;
import me.miquiis.onlyblock.common.entities.renderer.*;
import me.miquiis.onlyblock.common.items.JetpackArmorItem;
import me.miquiis.onlyblock.common.items.ModSpawnEgg;
import me.miquiis.onlyblock.common.items.renderer.JetpackArmorRenderer;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.registries.TileEntityRegister;
import me.miquiis.onlyblock.common.tileentities.renderer.MoneyPrinterTileRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
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

        ClientRegistry.bindTileEntityRenderer(TileEntityRegister.MONEY_PRINTER_TILE_ENTITY.get(), MoneyPrinterTileRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_ZOMBIE.get(), XPZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_CREEPER.get(), XPCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_SKELETON.get(), XPSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_SPIDER.get(), XPSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_ENDERMAN.get(), XPEndermanRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.GOOD_STOCK.get(), renderManager -> new SpriteRenderer<GoodStockEntity>(renderManager, Minecraft.getInstance().getItemRenderer(), 3f, true));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.BAD_STOCK.get(), renderManager -> new SpriteRenderer<BadStockEntity>(renderManager, Minecraft.getInstance().getItemRenderer(), 3f, true));

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.NOOB.get(), NoobRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.NOOB_BEZOS.get(), NoobBezosRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.ELON_MUSK.get(), ElonMuskRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.BANK_OWNER.get(), BankOwnerRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.VAN.get(), VanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.GOLDEN_HELI.get(), GoldenHelicopterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.GOLDEN_PROJECTILE.get(), GoldenProjectileRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.ONE_MIL.get(), OneMilRenderer::new);

        GeoArmorRenderer.registerArmorRenderer(JetpackArmorItem.class, new JetpackArmorRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.AIRDROP.get(), AirdropRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_CHICKEN.get(), XpChickenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_COW.get(), XpCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_SHEEP.get(), XpSheepRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.STOCK_GHAST.get(), GhastRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.AMAZON_TNT.get(), AmazonTNTRenderer::new);

        RenderTypeLookup.setRenderLayer(BlockRegister.SERVER_BLOCK.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockRegister.TERMINAL_PANEL.get(), RenderType.getCutout());
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
