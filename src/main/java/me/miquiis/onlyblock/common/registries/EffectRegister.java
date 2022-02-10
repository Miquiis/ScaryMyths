package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.FakeExperienceOrbEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegister {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, OnlyBlock.MOD_ID);

    public static final RegistryObject<Effect> XP_BOOST = EFFECTS.register("xp_boost", () ->
            new ModEffect(EffectType.BENEFICIAL, 5242690)
    );

    public static void register(IEventBus bus)
    {
        EFFECTS.register(bus);
    }

}
