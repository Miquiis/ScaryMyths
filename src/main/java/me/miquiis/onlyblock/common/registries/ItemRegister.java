package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.EnchantedItem;
import me.miquiis.onlyblock.common.items.ExpShovel;
import me.miquiis.onlyblock.common.items.ExpSword;
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

    public static final RegistryObject<Item> ENERGIZED_XP_INGOT = ITEMS.register("energized_xp_ingot", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS))
    );

    public static final RegistryObject<Item> XP_APPLE = ITEMS.register("xp_apple", () ->
            new Item(new Item.Properties().food(new Food.Builder().hunger(4).saturation(0.3F).effect(() -> new EffectInstance(EffectRegister.XP_BOOST.get(), 200, 0), 1.0f).setAlwaysEdible().build()).group(ItemGroup.FOOD))
    );

    public static final RegistryObject<Item> XP_AXE = ITEMS.register("xp_axe", () ->
            new AxeItem(ItemTier.DIAMOND, 1.5F, -3.0F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> XP_SHOVEL = ITEMS.register("xp_shovel", () ->
            new ExpShovel(ItemTier.DIAMOND, 5.0F, -3.0F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> XP_PICKAXE = ITEMS.register("xp_pickaxe", () ->
            new PickaxeItem(ItemTier.DIAMOND, 1, -2.8F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> XP_SWORD = ITEMS.register("xp_sword", () ->
            new ExpSword(ItemTier.DIAMOND, 3, -2.4F, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
