package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.EnchantedItem;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnlyBlock.MOD_ID);

    public static final RegistryObject<Item> XP_INGOT = ITEMS.register("xp_ingot", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS))
    );

    public static final RegistryObject<Item> XP_APPLE = ITEMS.register("xp_apple", () ->
            new Item(new Item.Properties().food(new Food.Builder().hunger(4).saturation(0.3F).effect(() -> new EffectInstance(EffectRegister.XP_BOOST.get(), 200, 0), 1.0f).setAlwaysEdible().build()).group(ItemGroup.FOOD))
    );

    public static final RegistryObject<Item> XP_AXE = ITEMS.register("xp_axe", () ->
            new AxeItem(ItemTier.DIAMOND, 5.0F, -3.0F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
