package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.*;
import me.miquiis.onlyblock.common.items.renderer.GoldenHelicopterItemRenderer;
import me.miquiis.onlyblock.common.items.renderer.JetpackItemRenderer;
import me.miquiis.onlyblock.common.items.renderer.MoneyShotgunRenderer;
import me.miquiis.onlyblock.common.items.renderer.NoobItemRenderer;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.example.item.PotatoArmorItem;

import java.util.HashSet;

public class ItemRegister {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnlyBlock.MOD_ID);

    public static final RegistryObject<Item> MONEY_SHOTGUN = ITEMS.register("money_shotgun", () ->
            new MoneyShotgun(new Item.Properties().group(ItemGroup.COMBAT).setISTER(() -> MoneyShotgunRenderer::new))
    );

    public static final RegistryObject<Item> NOOB_ITEM = ITEMS.register("noob_item", () ->
            new NoobItem(new Item.Properties().group(ItemGroup.MISC).setISTER(() -> NoobItemRenderer::new))
    );

    public static final RegistryObject<Item> CASH = ITEMS.register("cash", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> GOLDEN_BAZOOKA = ITEMS.register("golden_bazooka", () ->
            new GoldenBazooka(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1))
    );

    public static final RegistryObject<Item> GOLDEN_HELICOPTER = ITEMS.register("golden_helicopter", () ->
            new GoldenHelicopterItem(new Item.Properties().group(ItemGroup.TRANSPORTATION).maxStackSize(1).setISTER(() -> GoldenHelicopterItemRenderer::new))
    );

    public static final RegistryObject<Item> DOLLAR_SWORD = ITEMS.register("dollar_sword", () ->
            new SwordItem(ItemTier.WOOD, 3, -2.4F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> DOLLAR_PICKAXE = ITEMS.register("dollar_pickaxe", () ->
            new PickaxeItem(ItemTier.WOOD, 1, -2.8F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> DOLLAR_AXE = ITEMS.register("dollar_axe", () ->
            new AxeItem(ItemTier.WOOD, 6.0F, -3.2F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> DOLLAR_SHOVEL = ITEMS.register("dollar_shovel", () ->
            new AxeItem(ItemTier.WOOD, 1.5F, -3.0F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> MILLIONAIRE_SWORD = ITEMS.register("millionaire_sword", () ->
            new SwordItem(ItemTier.NETHERITE, 10, -1.1F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> MILLIONAIRE_PICKAXE = ITEMS.register("millionaire_pickaxe", () ->
            new PickaxeItem(ItemTier.NETHERITE, 5, -1.2F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> MILLIONAIRE_AXE = ITEMS.register("millionaire_axe", () ->
            new AxeItem(ItemTier.NETHERITE, 15.0F, -1.0F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<Item> MILLIONAIRE_SHOVEL = ITEMS.register("millionaire_shovel", () ->
            new AxeItem(ItemTier.NETHERITE, 2.0F, -3.0F, (new Item.Properties()).group(ItemGroup.TOOLS))
    );

    public static final RegistryObject<JetpackArmorItem> JETPACK = ITEMS.register("jetpack",
            () -> new JetpackArmorItem(ArmorMaterial.DIAMOND, EquipmentSlotType.CHEST, new Item.Properties().group(ItemGroup.COMBAT).setISTER(() -> JetpackItemRenderer::new)));

    public static final RegistryObject<Item> BITCOIN = ITEMS.register("bitcoin", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> BRIDGER = ITEMS.register("bridger", () ->
            new Item(new Item.Properties().group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> LASER_CANNON = ITEMS.register("laser_cannon", () ->
            new Item(new Item.Properties().group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> AIRDROP_CALLER = ITEMS.register("airdrop_caller", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC).maxStackSize(1).food(new Food.Builder().fastToEat().setAlwaysEdible().build()))
    );

    public static final RegistryObject<Item> GOLDEN_WRAP = ITEMS.register("golden_wrap", () ->
            new Item(new Item.Properties().group(ItemGroup.MISC))
    );

    public static final RegistryObject<Item> WRAPPED_COOKED_BEEF = ITEMS.register("wrapped_cooked_beef", () ->
            new Item(new Item.Properties().food(new Food.Builder().hunger(8).saturation(0.6F).effect(() -> new EffectInstance(EffectRegister.MONEY_BOOST.get(), 3600, 1), 1.0f).setAlwaysEdible().build()).group(ItemGroup.FOOD))
    );

    public static final RegistryObject<Item> WRAPPED_APPLE = ITEMS.register("wrapped_apple", () ->
            new Item(new Item.Properties().food(new Food.Builder().hunger(5).saturation(0.4F).effect(() -> new EffectInstance(EffectRegister.MONEY_BOOST.get(), 2300, 0), 1.0f).setAlwaysEdible().build()).group(ItemGroup.FOOD))
    );

    public static final RegistryObject<Item> GOLDEN_COOKED_BEEF = ITEMS.register("golden_cooked_beef", () ->
            new Item(new Item.Properties().food(new Food.Builder().hunger(20).saturation(0.6F).effect(() -> new EffectInstance(Effects.REGENERATION, 400, 1), 1.0F).effect(() -> new EffectInstance(Effects.RESISTANCE, 6000, 0), 1.0F).effect(() -> new EffectInstance(Effects.FIRE_RESISTANCE, 6000, 0), 1.0F).effect(() -> new EffectInstance(Effects.ABSORPTION, 2400, 3), 1.0F).effect(() -> new EffectInstance(EffectRegister.MONEY_BOOST.get(), 3600, 2), 1.0f).setAlwaysEdible().build()).group(ItemGroup.FOOD))
    );

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
