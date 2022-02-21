package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.*;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = OnlyBlock.MOD_ID)
public class EntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, OnlyBlock.MOD_ID);

    public static final RegistryObject<EntityType<CustomFallingBlockEntity>> CUSTOM_FALLING_BLOCK = ENTITIES.register("custom_falling_block",
            () -> EntityType.Builder.<CustomFallingBlockEntity>create(CustomFallingBlockEntity::new, EntityClassification.MISC)
                    .size(0.98F, 0.98F).trackingRange(10).updateInterval(20)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "custom_falling_block").toString())
    );

    public static final RegistryObject<EntityType<AirdropEntity>> AIRDROP = ENTITIES.register("airdrop",
            () -> EntityType.Builder.<AirdropEntity>create(AirdropEntity::new, EntityClassification.MISC)
                    .size(1.0F, 4F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "airdrop").toString())
    );

    public static final RegistryObject<EntityType<XPZombieEntity>> XP_ZOMBIE = ENTITIES.register("xp_zombie",
            () -> EntityType.Builder.<XPZombieEntity>create(XPZombieEntity::new, EntityClassification.MONSTER)
                    .size(0.6F, 1.95F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "xp_zombie").toString())
    );

    public static final RegistryObject<EntityType<XPCreeperEntity>> XP_CREEPER = ENTITIES.register("xp_creeper",
            () -> EntityType.Builder.<XPCreeperEntity>create(XPCreeperEntity::new, EntityClassification.MONSTER)
                    .size(0.6F, 1.7F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "xp_creeper").toString())
    );

    public static final RegistryObject<EntityType<XPSkeletonEntity>> XP_SKELETON = ENTITIES.register("xp_skeleton",
            () -> EntityType.Builder.<XPSkeletonEntity>create(XPSkeletonEntity::new, EntityClassification.MONSTER)
                    .size(0.6F, 1.99F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "xp_skeleton").toString())
    );

    public static final RegistryObject<EntityType<XPEndermanEntity>> XP_ENDERMAN = ENTITIES.register("xp_enderman",
            () -> EntityType.Builder.<XPEndermanEntity>create(XPEndermanEntity::new, EntityClassification.MONSTER)
                    .size(0.6F, 2.9F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "xp_enderman").toString())
    );

    public static final RegistryObject<EntityType<XPSpiderEntity>> XP_SPIDER = ENTITIES.register("xp_spider",
            () -> EntityType.Builder.<XPSpiderEntity>create(XPSpiderEntity::new, EntityClassification.MONSTER)
                    .size(1.4F, 0.9F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "xp_spider").toString())
    );

    public static final RegistryObject<EntityType<XpCowEntity>> XP_COW = ENTITIES.register("xp_cow",
            () -> EntityType.Builder.<XpCowEntity>create(XpCowEntity::new, EntityClassification.CREATURE)
                    .size(0.9F, 1.4F)
                    .trackingRange(10)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "xp_cow").toString())
    );

    public static final RegistryObject<EntityType<XpSheepEntity>> XP_SHEEP = ENTITIES.register("xp_sheep",
            () -> EntityType.Builder.<XpSheepEntity>create(XpSheepEntity::new, EntityClassification.CREATURE)
                    .size(0.9F, 1.3F)
                    .trackingRange(10)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "xp_sheep").toString())
    );

    public static final RegistryObject<EntityType<XpChickenEntity>> XP_CHICKEN = ENTITIES.register("xp_chicken",
            () -> EntityType.Builder.<XpChickenEntity>create(XpChickenEntity::new, EntityClassification.CREATURE)
                    .size(0.4F, 0.7F)
                    .trackingRange(10)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "xp_chicken").toString())
    );

    public static void register(IEventBus bus)
    {
        ENTITIES.register(bus);
    }

    @SubscribeEvent
    public static void onEntityRegister(EntityAttributeCreationEvent event)
    {
        event.put(XP_CHICKEN.get(), XpChickenEntity.func_234187_eI_().create());
        event.put(XP_COW.get(), XpCowEntity.registerAttributes().create());
        event.put(XP_SHEEP.get(), XpSheepEntity.registerAttributes().create());

        event.put(XP_ZOMBIE.get(), XPZombieEntity.func_234342_eQ_().create());
        event.put(XP_SKELETON.get(), XPSkeletonEntity.registerAttributes().create());
        event.put(XP_ENDERMAN.get(), XPEndermanEntity.func_234287_m_().create());
        event.put(XP_SPIDER.get(), XPSpiderEntity.func_234305_eI_().create());
        event.put(XP_CREEPER.get(), XPCreeperEntity.registerAttributes().create());

        event.put(AIRDROP.get(), AirdropEntity.registerAttributes().create());
    }

}
