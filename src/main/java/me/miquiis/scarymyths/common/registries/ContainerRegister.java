package me.miquiis.scarymyths.common.registries;

import me.miquiis.scarymyths.ScaryMyths;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerRegister {

    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ScaryMyths.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        CONTAINERS.register(eventBus);
    }

}
