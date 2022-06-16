package me.miquiis.scarymyths.common.registries;

import me.miquiis.scarymyths.ScaryMyths;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundRegister {

    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ScaryMyths.MOD_ID);

    private static RegistryObject<SoundEvent> registerSoundEvent(String name)
    {
        return SOUND_EVENT.register(name, () -> new SoundEvent(new ResourceLocation(ScaryMyths.MOD_ID, name)));
    }

    public static void register(IEventBus bus)
    {
        SOUND_EVENT.register(bus);
    }

}
