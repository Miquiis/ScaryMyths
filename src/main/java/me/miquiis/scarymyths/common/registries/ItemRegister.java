package me.miquiis.scarymyths.common.registries;

import me.miquiis.scarymyths.ScaryMyths;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ScaryMyths.MOD_ID);

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
