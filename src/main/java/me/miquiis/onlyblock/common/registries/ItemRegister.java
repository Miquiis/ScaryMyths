package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnlyBlock.MOD_ID);

    public static final RegistryObject<Item> XP_SHEEP_EGG = ITEMS.register("xp_sheep_egg", () ->
            new ModSpawnEgg(EntityRegister.XP_SHEEP, 61793, 16777215, (new Item.Properties()).group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> XP_COW_EGG = ITEMS.register("xp_cow_egg", () ->
            new ModSpawnEgg(EntityRegister.XP_COW, 61793, 7295287, (new Item.Properties()).group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> XP_CHICKEN_EGG = ITEMS.register("xp_chicken_egg", () ->
            new ModSpawnEgg(EntityRegister.XP_CHICKEN, 61793, 16730420, (new Item.Properties()).group(ItemGroup.MISC))
    );

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
