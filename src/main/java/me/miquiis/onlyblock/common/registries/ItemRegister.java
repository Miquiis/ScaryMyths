package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.EnchantedItem;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnlyBlock.MOD_ID);

    public static final RegistryObject<Item> COBBLESTONE_BOOK = ITEMS.register("cobblestone_book", () -> new Item(
            new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)
    ));

    public static final RegistryObject<Item> ASH_BOOK = ITEMS.register("ash_book", () -> new Item(
            new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)
    ));

    public static final RegistryObject<Item> LAVA_BOOK = ITEMS.register("lava_book", () -> new Item(
            new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)
    ));

    public static final RegistryObject<Item> FROST_BOOK = ITEMS.register("frost_book", () -> new Item(
            new Item.Properties().maxStackSize(1).group(ItemGroup.MISC)
    ));

    public static final RegistryObject<Item> COBBLESTONE_STICK = ITEMS.register("cobblestone_stick", () -> new Item(
            new Item.Properties().group(ItemGroup.MATERIALS)
    ));

    public static final RegistryObject<Item> COBBLESTONE_SWORD = ITEMS.register("cobblestone_sword", () -> new SwordItem(
            ItemTier.STONE, 2, -2.8F, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> COBBLESTONE_PICKAXE = ITEMS.register("cobblestone_pickaxe", () -> new PickaxeItem(
            ItemTier.STONE, 1, -3.0F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> COBBLESTONE_AXE = ITEMS.register("cobblestone_axe", () -> new AxeItem(
            ItemTier.STONE, 6.0F, -3.5F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> COBBLESTONE_SHOVEL = ITEMS.register("cobblestone_shovel", () -> new ShovelItem(
            ItemTier.STONE, 1.2F, -3.25F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> COBBLESTONE_HOE = ITEMS.register("cobblestone_hoe", () -> new HoeItem(
            ItemTier.STONE, -2, -2.25F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
