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

    public static void register(IEventBus bus)
    {
        ENTITIES.register(bus);
    }

    @SubscribeEvent
    public static void onEntityRegister(EntityAttributeCreationEvent event)
    {
        event.put(LIFE_KEEPER.get(), LifeKeeperEntity.func_233666_p_().create());
        event.put(BUILD_KEEPER.get(), BuildKeeperEntity.func_233666_p_().create());
        event.put(TIME_KEEPER.get(), TimeKeeperEntity.func_233666_p_().create());
    }

}
