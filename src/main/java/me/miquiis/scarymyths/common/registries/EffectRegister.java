package me.miquiis.scarymyths.common.registries;

import me.miquiis.scarymyths.ScaryMyths;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class EffectRegister {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, ScaryMyths.MOD_ID);

    public static void register(IEventBus bus)
    {
        EFFECTS.register(bus);
    }

}
