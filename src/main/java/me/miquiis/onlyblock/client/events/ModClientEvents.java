package me.miquiis.onlyblock.client.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.MiniXPTNTEntity;
import me.miquiis.onlyblock.common.entities.XPTNTEntity;
import me.miquiis.onlyblock.common.entities.renderer.*;
import me.miquiis.onlyblock.common.items.ModSpawnEgg;
import me.miquiis.onlyblock.common.particles.ExpExplosionParticle;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.registries.ParticleRegister;
import me.miquiis.onlyblock.common.tileentity.ModTileEntity;
import me.miquiis.onlyblock.common.tileentity.renderer.XpMinerTileEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.TNTRenderer;
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

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = OnlyBlock.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void doClientStuff(final FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.FAKE_EXPERIENCE_ORB.get(), FakeExperienceOrbRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.DAMAGEABLE_EXPERIENCE_ORB.get(), DamageableExperienceOrbRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_TNT.get(), XPTNTRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.MINI_XP_TNT.get(), MiniXPTNTRenderer::new);

        ClientRegistry.bindTileEntityRenderer(ModTileEntity.XP_MINER_TILE_ENTITY.get(), XpMinerTileEntityRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_ZOMBIE.get(), XPZombieRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_CREEPER.get(), XPCreeperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_SKELETON.get(), XPSkeletonRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_SPIDER.get(), XPSpiderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_ENDERMAN.get(), XPEndermanRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_KING.get(), XPKingRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_WARHAMMER_PROJECTILE.get(), XPWarhammerRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_CHICKEN.get(), XpChickenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_COW.get(), XpCowRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRegister.XP_SHEEP.get(), XpSheepRenderer::new);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactory(ParticleFactoryRegisterEvent event)
    {
        Minecraft.getInstance().particles.registerFactory(ParticleRegister.EXP_EXPLOSION.get(), ExpExplosionParticle.Factory::new);
    }

    @SubscribeEvent
    public static void onRegistrEntities(final RegistryEvent.Register<EntityType<?>> event)
    {
        ModSpawnEgg.initSoawnEggs();
    }

}
