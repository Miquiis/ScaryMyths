package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.*;
import me.miquiis.onlyblock.common.items.renderer.FlyingTeslaItemRenderer;
import me.miquiis.onlyblock.common.items.renderer.MoneyItemArmorRenderer;
import net.minecraft.inventory.EquipmentSlotType;
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

    public static final RegistryObject<Item> MONEY_HELMET = ITEMS.register("money_helmet", () ->
            new MoneyArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.HEAD, new Item.Properties().group(ItemGroup.COMBAT).setISTER(() -> () -> new MoneyItemArmorRenderer(EquipmentSlotType.HEAD)))
    );

    public static final RegistryObject<Item> MONEY_CHESTPLATE = ITEMS.register("money_chestplate", () ->
            new MoneyArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).setISTER(() -> () -> new MoneyItemArmorRenderer(EquipmentSlotType.CHEST)))
    );

    public static final RegistryObject<Item> MONEY_LEGGINGS = ITEMS.register("money_leggings", () ->
            new MoneyArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.LEGS, new Item.Properties().group(ItemGroup.COMBAT).setISTER(() -> () -> new MoneyItemArmorRenderer(EquipmentSlotType.LEGS)))
    );

    public static final RegistryObject<Item> MONEY_BOOTS = ITEMS.register("money_boots", () ->
            new MoneyArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.FEET, new Item.Properties().group(ItemGroup.COMBAT).setISTER(() -> () -> new MoneyItemArmorRenderer(EquipmentSlotType.FEET)))
    );

    public static final RegistryObject<Item> BIG_MAC = ITEMS.register("big_mac", () ->
            new Item(new Item.Properties().food(Foods.GOLDEN_APPLE).group(ItemGroup.FOOD))
    );

    public static final RegistryObject<Item> MCDONALDS = ITEMS.register("mcdonalds", () ->
            new McDonaldsCompanyItem(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> FLYING_TESLA = ITEMS.register("flying_tesla", () ->
            new FlyingTeslaItem(new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1).setISTER(() -> FlyingTeslaItemRenderer::new))
    );

    public static final RegistryObject<Item> TESLA = ITEMS.register("tesla", () ->
            new TeslaCompanyItem(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> AMAZON = ITEMS.register("amazon", () ->
            new AmazonCompanyItem(new Item.Properties().group(ItemGroup.MISC))
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
