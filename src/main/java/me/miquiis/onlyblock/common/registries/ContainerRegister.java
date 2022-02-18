package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.containers.MinazonContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerRegister {

    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, OnlyBlock.MOD_ID);

    public static final RegistryObject<ContainerType<MinazonContainer>> MINAZON_CONTAINER = CONTAINERS.register("minazon_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> new MinazonContainer(windowId, inv.player, inv)))
    );

    public static void register(IEventBus eventBus)
    {
        CONTAINERS.register(eventBus);
    }

}
