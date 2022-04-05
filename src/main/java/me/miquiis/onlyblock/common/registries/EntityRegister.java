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

    public static final RegistryObject<EntityType<LifeKeeperEntity>> LIFE_KEEPER = ENTITIES.register("life_keeper", () ->
            EntityType.Builder.<LifeKeeperEntity>create(LifeKeeperEntity::new, EntityClassification.MISC)
                    .size(1.5f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "life_keeper").toString())
    );

    public static final RegistryObject<EntityType<CustomFallingBlockEntity>> CUSTOM_FALLING_BLOCK = ENTITIES.register("custom_falling_block",
            () -> EntityType.Builder.<CustomFallingBlockEntity>create(CustomFallingBlockEntity::new, EntityClassification.MISC)
                    .size(0.98F, 0.98F).trackingRange(10).updateInterval(20)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "custom_falling_block").toString())
    );

    public static final RegistryObject<EntityType<BuildKeeperEntity>> BUILD_KEEPER = ENTITIES.register("build_keeper", () ->
            EntityType.Builder.<BuildKeeperEntity>create(BuildKeeperEntity::new, EntityClassification.MISC)
                    .size(1.5f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "build_keeper").toString())
    );

    public static final RegistryObject<EntityType<TimeKeeperEntity>> TIME_KEEPER = ENTITIES.register("time_keeper", () ->
            EntityType.Builder.<TimeKeeperEntity>create(TimeKeeperEntity::new, EntityClassification.MISC)
                    .size(1.5f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "time_keeper").toString())
    );

    public static final RegistryObject<EntityType<MutantCreeperEntity>> MUTANT_CREEPER = ENTITIES.register("mutant_creeper", () ->
            EntityType.Builder.<MutantCreeperEntity>create(MutantCreeperEntity::new, EntityClassification.MISC)
                    .size(1f, 1.8f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "mutant_creeper").toString())
    );

    public static final RegistryObject<EntityType<MutantSkeletonEntity>> MUTANT_SKELETON = ENTITIES.register("mutant_skeleton", () ->
            EntityType.Builder.<MutantSkeletonEntity>create(MutantSkeletonEntity::new, EntityClassification.MISC)
                    .size(0.8f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "mutant_skeleton").toString())
    );

    public static final RegistryObject<EntityType<MutantZombieEntity>> MUTANT_ZOMBIE = ENTITIES.register("mutant_zombie", () ->
            EntityType.Builder.<MutantZombieEntity>create(MutantZombieEntity::new, EntityClassification.MISC)
                    .size(1f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "mutant_zombie").toString())
    );

    public static final RegistryObject<EntityType<BobEntity>> BOB = ENTITIES.register("bob", () ->
            EntityType.Builder.<BobEntity>create(BobEntity::new, EntityClassification.MISC)
                    .size(1f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "bob").toString())
    );

    public static final RegistryObject<EntityType<AlfredEntity>> ALFRED = ENTITIES.register("alfred", () ->
            EntityType.Builder.<AlfredEntity>create(AlfredEntity::new, EntityClassification.MISC)
                    .size(1f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "alfred").toString())
    );

    public static final RegistryObject<EntityType<DealerEntity>> DEALER = ENTITIES.register("dealer", () ->
            EntityType.Builder.<DealerEntity>create(DealerEntity::new, EntityClassification.MISC)
                    .size(1f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "dealer").toString())
    );

    public static final RegistryObject<EntityType<HackerEntity>> HACKER = ENTITIES.register("hacker", () ->
            EntityType.Builder.<HackerEntity>create(HackerEntity::new, EntityClassification.MISC)
                    .size(1f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "hacker").toString())
    );

    public static final RegistryObject<EntityType<FlyingTeslaEntity>> FLYING_TESLA = ENTITIES.register("flying_tesla",
            () -> EntityType.Builder.<FlyingTeslaEntity>create(FlyingTeslaEntity::new, EntityClassification.MISC)
                    .size(2.5f, 1.5f).trackingRange(10)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "flying_tesla").toString())
    );

    public static final RegistryObject<EntityType<NoobEntity>> NOOB = ENTITIES.register("noob", () ->
            EntityType.Builder.<NoobEntity>create(NoobEntity::new, EntityClassification.MISC)
                    .size(1f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "noob").toString())
    );

    public static final RegistryObject<EntityType<JeffBezosEntity>> JEFF_BEZOS = ENTITIES.register("jeff_bezos", () ->
            EntityType.Builder.<JeffBezosEntity>create(JeffBezosEntity::new, EntityClassification.MISC)
                    .size(1f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "jeff_bezos").toString())
    );

    public static final RegistryObject<EntityType<ElonMuskEntity>> ELON_MUSK = ENTITIES.register("elon_musk", () ->
            EntityType.Builder.<ElonMuskEntity>create(ElonMuskEntity::new, EntityClassification.MISC)
                    .size(1f, 2f)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "elon_musk").toString())
    );

    public static final RegistryObject<EntityType<SaleEntity>> SALE = ENTITIES.register("sale",
            () -> EntityType.Builder.<SaleEntity>create(SaleEntity::new, EntityClassification.MISC)
                    .size(5.0F, 5.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "sale").toString())
    );

    public static final RegistryObject<EntityType<SoldEntity>> SOLD = ENTITIES.register("sold",
            () -> EntityType.Builder.<SoldEntity>create(SoldEntity::new, EntityClassification.MISC)
                    .size(5.0F, 5.0F)
                    .trackingRange(8)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "sold").toString())
    );

    public static void register(IEventBus bus)
    {
        ENTITIES.register(bus);
    }

    @SubscribeEvent
    public static void onEntityRegister(EntityAttributeCreationEvent event)
    {
        event.put(MUTANT_CREEPER.get(), MutantCreeperEntity.registerAttributes().create());
        event.put(MUTANT_ZOMBIE.get(), MutantZombieEntity.registerAttributes().create());
        event.put(MUTANT_SKELETON.get(), MutantSkeletonEntity.registerAttributes().create());

        event.put(LIFE_KEEPER.get(), LifeKeeperEntity.func_233666_p_().create());
        event.put(BUILD_KEEPER.get(), BuildKeeperEntity.func_233666_p_().create());
        event.put(TIME_KEEPER.get(), TimeKeeperEntity.func_233666_p_().create());

        event.put(DEALER.get(), DealerEntity.func_233666_p_().create());
        event.put(BOB.get(), BobEntity.func_233666_p_().create());
        event.put(ALFRED.get(), AlfredEntity.func_233666_p_().create());
        event.put(NOOB.get(), NoobEntity.func_233666_p_().create());
        event.put(HACKER.get(), HackerEntity.func_233666_p_().create());
        event.put(ELON_MUSK.get(), ElonMuskEntity.func_233666_p_().create());
        event.put(JEFF_BEZOS.get(), JeffBezosEntity.func_233666_p_().create());

        event.put(SALE.get(), SaleEntity.func_233666_p_().create());
        event.put(SOLD.get(), SaleEntity.func_233666_p_().create());
        event.put(FLYING_TESLA.get(), FlyingTeslaEntity.func_233666_p_().create());
    }

}
