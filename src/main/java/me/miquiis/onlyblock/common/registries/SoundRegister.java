package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.*;
import me.miquiis.onlyblock.common.items.renderer.WarhammerRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;

public class SoundRegister {

    public static final DeferredRegister<SoundEvent> SOUND_EVENT = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, OnlyBlock.MOD_ID);

    public static final RegistryObject<SoundEvent> UNLOCK = registerSoundEvent("unlock");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name)
    {
        return SOUND_EVENT.register(name, () -> new SoundEvent(new ResourceLocation(OnlyBlock.MOD_ID, name)));
    }

    public static void register(IEventBus bus)
    {
        SOUND_EVENT.register(bus);
    }

}
