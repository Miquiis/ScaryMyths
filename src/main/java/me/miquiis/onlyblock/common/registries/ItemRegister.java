package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.BridgerItem;
import me.miquiis.onlyblock.common.items.TNTBazooka;
import net.minecraft.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnlyBlock.MOD_ID);

    public static final RegistryObject<Item> DEBIT_CARD_SWORD = ITEMS.register("debit_card_sword", () ->
            new SwordItem(ItemTier.DIAMOND, 3, -2.4F, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> CRYPTO_MINER = ITEMS.register("crypto_miner", () ->
            new PickaxeItem(ItemTier.IRON, 1, -2.8F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> BRIDGER = ITEMS.register("bridger", () ->
            new BridgerItem(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1))
    );

    public static final RegistryObject<Item> TNT_BAZOOKA = ITEMS.register("tnt_bazooka", () ->
            new TNTBazooka(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1))
    );

    public static final RegistryObject<Item> BIG_MAC = ITEMS.register("big_mac", () ->
            new Item(new Item.Properties().food(Foods.COOKED_BEEF).group(ItemGroup.FOOD))
    );

    public static final RegistryObject<Item> FROZEN_CLOCK = ITEMS.register("frozen_clock", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> SABOTAGE = ITEMS.register("sabotage", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> MILLIONAIRE_NUKE = ITEMS.register("millionaire_nuke", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> BALACLAVA = ITEMS.register("balaclava", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC))
    );

    public static void register(IEventBus bus)
    {
        ITEMS.register(bus);
    }

}
