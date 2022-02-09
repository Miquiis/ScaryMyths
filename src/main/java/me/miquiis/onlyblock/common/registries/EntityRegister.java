package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.FakeExperienceOrbEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityRegister {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, OnlyBlock.MOD_ID);

    public static final RegistryObject<EntityType<FakeExperienceOrbEntity>> FAKE_EXPERIENCE_ORB = ENTITIES.register("fake_experience_orb",
            () -> EntityType.Builder.<FakeExperienceOrbEntity>create(FakeExperienceOrbEntity::new, EntityClassification.MISC)
                    .size(0.5F, 0.5F).trackingRange(6).updateInterval(20)
                    .build(new ResourceLocation(OnlyBlock.MOD_ID, "fake_experience_orb").toString())
    );

    public static void register(IEventBus bus)
    {
        ENTITIES.register(bus);
    }

}
