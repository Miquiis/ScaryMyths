package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.tileentity.MoneyPrinterTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityRegister {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, OnlyBlock.MOD_ID);

    public static RegistryObject<TileEntityType<MoneyPrinterTileEntity>> MONEY_PRINTER_TILE_ENTITY = TILE_ENTITIES.register("money_printer_tile_entity", () ->
            TileEntityType.Builder.create(MoneyPrinterTileEntity::new, BlockRegister.MONEY_PRINTER.get()).build(null));

    public static void register(IEventBus eventBus) { TILE_ENTITIES.register(eventBus);}

}
