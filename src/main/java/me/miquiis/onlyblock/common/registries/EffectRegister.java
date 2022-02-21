package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.*;

public class EffectRegister {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, OnlyBlock.MOD_ID);

    public static final RegistryObject<Effect> MONEY_BOOST = EFFECTS.register("money_boost", () ->
            new ModEffect(EffectType.BENEFICIAL, new Color(46, 217, 91).getRGB())
    );

    public static void register(IEventBus bus)
    {
        EFFECTS.register(bus);
    }

}
