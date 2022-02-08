package me.miquiis.onlyblock.common.tileentity;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntity {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, OnlyBlock.MOD_ID);

    public static void register(IEventBus eventBus) { TILE_ENTITIES.register(eventBus);}

}
