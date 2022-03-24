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
import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = OnlyBlock.MOD_ID)
public class EntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, OnlyBlock.MOD_ID);

    public static final RegistryObject<EntityType<CustomFallingBlockEntity>> CUSTOM_FALLING_BLOCK = ENTITIES.register("custom_falling_block",
            () -> EntityType.Builder.<CustomFallingBlockEntity>create(CustomFallingBlockEntity::new, EntityClassification.MISC)
                    .size(0.98F, 0.98F).trackingRange(10).updateInterval(20)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "custom_falling_block").toString())
    );

    public static final RegistryObject<EntityType<OneMilEntity>> ONE_MIL = ENTITIES.register("one_mil",
            () -> EntityType.Builder.<OneMilEntity>create(OneMilEntity::new, EntityClassification.MISC)
                    .size(2.0F, 2.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "one_mil").toString())
    );

    public static final RegistryObject<EntityType<FiveHundredEntity>> FIVE_HUNDRED = ENTITIES.register("five_hundred",
            () -> EntityType.Builder.<FiveHundredEntity>create(FiveHundredEntity::new, EntityClassification.MISC)
                    .size(2.0F, 2.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "five_hundred").toString())
    );

    public static final RegistryObject<EntityType<HundredEntity>> HUNDRED = ENTITIES.register("hundred",
            () -> EntityType.Builder.<HundredEntity>create(HundredEntity::new, EntityClassification.MISC)
                    .size(2.0F, 2.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "hundred").toString())
    );

    public static final RegistryObject<EntityType<AsteroidEntity>> ASTEROID = ENTITIES.register("asteroid",
            () -> EntityType.Builder.<AsteroidEntity>create(AsteroidEntity::new, EntityClassification.MISC)
                    .size(20.0f, 20.0f).trackingRange(20)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "asteroid").toString())
    );

    public static final RegistryObject<EntityType<QuestionMarkEntity>> QUESTION_MARK = ENTITIES.register("question_mark",
            () -> EntityType.Builder.<QuestionMarkEntity>create(QuestionMarkEntity::new, EntityClassification.MISC)
                    .size(2.0F, 2.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "question_mark").toString())
    );

    public static final RegistryObject<EntityType<BuffetEntity>> BUFFETT = ENTITIES.register("buffett",
            () -> EntityType.Builder.<BuffetEntity>create(BuffetEntity::new, EntityClassification.MISC)
                    .size(0.6F, 1.95F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "buffett").toString())
    );

    public static final RegistryObject<EntityType<GiantEarthEntity>> GIANT_EARTH = ENTITIES.register("giant_earth",
            () -> EntityType.Builder.<GiantEarthEntity>create(GiantEarthEntity::new, EntityClassification.MISC)
                    .size(20F, 20F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "giant_earth").toString())
    );

    public static final RegistryObject<EntityType<JeffBezosEntity>> JEFF_BEZOS = ENTITIES.register("jeff_bezos",
            () -> EntityType.Builder.<JeffBezosEntity>create(JeffBezosEntity::new, EntityClassification.MISC)
                    .size(0.6F, 1.95F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "jeff_bezos").toString())
    );

    public static final RegistryObject<EntityType<ElonMuskEntity>> ELON_MUSK = ENTITIES.register("elon_musk",
            () -> EntityType.Builder.<ElonMuskEntity>create(ElonMuskEntity::new, EntityClassification.MISC)
                    .size(0.6F, 1.95F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "elon_musk").toString())
    );

    public static final RegistryObject<EntityType<GoodStockEntity>> GOOD_STOCK = ENTITIES.register("good_stock",
            () -> EntityType.Builder.<GoodStockEntity>create(GoodStockEntity::new, EntityClassification.MISC)
                    .size(1.0F, 1.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "good_stock").toString())
    );

    public static final RegistryObject<EntityType<BadStockEntity>> BAD_STOCK = ENTITIES.register("bad_stock",
            () -> EntityType.Builder.<BadStockEntity>create(BadStockEntity::new, EntityClassification.MISC)
                    .size(1.0F, 1.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "bad_stock").toString())
    );

    public static final RegistryObject<EntityType<VanEntity>> VAN = ENTITIES.register("van",
            () -> EntityType.Builder.<VanEntity>create(VanEntity::new, EntityClassification.MISC)
                    .size(2.0F, 2.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "van").toString())
    );

    public static final RegistryObject<EntityType<VanTwoEntity>> VAN_TWO = ENTITIES.register("van_two",
            () -> EntityType.Builder.<VanTwoEntity>create(VanTwoEntity::new, EntityClassification.MISC)
                    .size(2.0F, 2.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "van_two").toString())
    );

    public static final RegistryObject<EntityType<SedanEntity>> SEDAN = ENTITIES.register("sedan",
            () -> EntityType.Builder.<SedanEntity>create(SedanEntity::new, EntityClassification.MISC)
                    .size(2.0F, 2.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "sedan").toString())
    );

    public static final RegistryObject<EntityType<SedanTwoEntity>> SEDAN_TWO = ENTITIES.register("sedan_two",
            () -> EntityType.Builder.<SedanTwoEntity>create(SedanTwoEntity::new, EntityClassification.MISC)
                    .size(2.0F, 2.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "sedan_two").toString())
    );

    public static final RegistryObject<EntityType<GoldenProjectileEntity>> GOLDEN_PROJECTILE = ENTITIES.register("golden_projectile",
            () -> EntityType.Builder.<GoldenProjectileEntity>create(GoldenProjectileEntity::new, EntityClassification.MISC)
                    .size(1.0f, 0.5f).trackingRange(20)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "golden_projectile").toString())
    );

    public static final RegistryObject<EntityType<StockGhastEntity>> STOCK_GHAST = ENTITIES.register("stock_ghast",
            () -> EntityType.Builder.<StockGhastEntity>create(StockGhastEntity::new, EntityClassification.MONSTER)
                    .immuneToFire().size(4.0F, 4.0F).trackingRange(10)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "stock_ghast").toString())
    );

    public static final RegistryObject<EntityType<GoldenHelicopterEntity>> GOLDEN_HELI = ENTITIES.register("golden_helicopter",
            () -> EntityType.Builder.<GoldenHelicopterEntity>create(GoldenHelicopterEntity::new, EntityClassification.MISC)
                    .size(3f, 3f).trackingRange(10)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "golden_helicopter").toString())
    );

    public static final RegistryObject<EntityType<SpaceshipEntity>> SPACESHIP = ENTITIES.register("spaceship",
            () -> EntityType.Builder.<SpaceshipEntity>create(SpaceshipEntity::new, EntityClassification.MISC)
                    .size(3f, 3f).trackingRange(10)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "spaceship").toString())
    );

    public static final RegistryObject<EntityType<AmazonTNTEntity>> AMAZON_TNT = ENTITIES.register("amazon_tnt",
            () -> EntityType.Builder.<AmazonTNTEntity>create(AmazonTNTEntity::new, EntityClassification.MISC)
                    .immuneToFire().size(0.98F, 0.98F)
                    .trackingRange(10)
                    .updateInterval(10).build(new ResourceLocation(OnlyBlock.MOD_ID, "amazon_tnt").toString())
    );

    public static void register(IEventBus bus)
    {
        ENTITIES.register(bus);
    }

    @SubscribeEvent
    public static void onEntityRegister(EntityAttributeCreationEvent event)
    {
        event.put(STOCK_GHAST.get(), StockGhastEntity.func_234290_eH_().create());

        event.put(BUFFETT.get(), BuffetEntity.registerAttributes().create());
        event.put(JEFF_BEZOS.get(), JeffBezosEntity.registerAttributes().create());
        event.put(ELON_MUSK.get(), ElonMuskEntity.registerAttributes().create());

        event.put(ASTEROID.get(), AsteroidEntity.registerAttributes().create());

        event.put(VAN.get(), VanEntity.func_233666_p_().create());
        event.put(VAN_TWO.get(), VanTwoEntity.func_233666_p_().create());
        event.put(SEDAN.get(), SedanEntity.func_233666_p_().create());
        event.put(SEDAN_TWO.get(), SedanTwoEntity.func_233666_p_().create());

        event.put(GOLDEN_HELI.get(), GoldenHelicopterEntity.func_233666_p_().create());
        event.put(SPACESHIP.get(), SpaceshipEntity.func_233666_p_().create());
        event.put(GIANT_EARTH.get(), GiantEarthEntity.registerAttributes().create());

        event.put(ONE_MIL.get(), OneMilEntity.registerAttributes().create());
        event.put(FIVE_HUNDRED.get(), FiveHundredEntity.registerAttributes().create());
        event.put(HUNDRED.get(), HundredEntity.registerAttributes().create());
        event.put(QUESTION_MARK.get(), QuestionMarkEntity.registerAttributes().create());
    }

}
