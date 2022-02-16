package me.miquiis.onlyblock.common.registries;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.*;
import me.miquiis.onlyblock.common.items.renderer.WarhammerRenderer;
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

    public static final RegistryObject<Item> XP_INGOT = ITEMS.register("xp_ingot", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS))
    );

    public static final RegistryObject<Item> ENERGY_XP_INGOT = ITEMS.register("energy_xp_ingot", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS))
    );

    public static final RegistryObject<Item> XP_APPLE = ITEMS.register("xp_apple", () ->
            new Item(new Item.Properties().food(new Food.Builder().hunger(4).saturation(0.3F).effect(() -> new EffectInstance(EffectRegister.XP_BOOST.get(), 200, 0), 1.0f).setAlwaysEdible().build()).group(ItemGroup.FOOD))
    );

    public static final RegistryObject<Item> XP_MEAT = ITEMS.register("xp_meat", () ->
            new Item(new Item.Properties().food(new Food.Builder().hunger(6).saturation(0.6F).setAlwaysEdible().build()).group(ItemGroup.FOOD))
    );

    public static final RegistryObject<Item> XP_GUNPOWDER = ITEMS.register("xp_gunpowder", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS))
    );

    public static final RegistryObject<Item> XP_BONE = ITEMS.register("xp_bone", () ->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS))
    );

    public static final RegistryObject<Item> XP_WARHAMMER = ITEMS.register("xp_warhammer", () ->
            new WarhammerItem(15.0F, -1.0F, ItemTier.NETHERITE, new HashSet<>(), new Item.Properties().group(ItemGroup.TOOLS).setISTER(() -> WarhammerRenderer::new))
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

    public static final RegistryObject<Item> XP_LAUNCHER = ITEMS.register("xp_launcher", () ->
            new XPLauncherItem(new Item.Properties().group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> ENERGY_XP_SWORD = ITEMS.register("energy_xp_sword", () ->
            new EnergyExpSword(ItemTier.NETHERITE, 8, -1.4F, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> XP_CROWN = ITEMS.register("xp_crown", () ->
            new ArmorItem(CustomArmorMaterial.CROWN, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> XP_HELMET = ITEMS.register("xp_helmet", () ->
            new ArmorItem(CustomArmorMaterial.XP, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> XP_CHESTPLATE = ITEMS.register("xp_chestplate", () ->
            new ArmorItem(CustomArmorMaterial.XP, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> XP_LEGGINGS = ITEMS.register("xp_leggings", () ->
            new ArmorItem(CustomArmorMaterial.XP, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> XP_BOOTS = ITEMS.register("xp_boots", () ->
            new ArmorItem(CustomArmorMaterial.XP, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> ENERGY_XP_HELMET = ITEMS.register("energy_xp_helmet", () ->
            new ArmorItem(CustomArmorMaterial.ENERGY_XP, EquipmentSlotType.HEAD, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> ENERGY_XP_CHESTPLATE = ITEMS.register("energy_xp_chestplate", () ->
            new ArmorItem(CustomArmorMaterial.ENERGY_XP, EquipmentSlotType.CHEST, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> ENERGY_XP_LEGGINGS = ITEMS.register("energy_xp_leggings", () ->
            new ArmorItem(CustomArmorMaterial.ENERGY_XP, EquipmentSlotType.LEGS, (new Item.Properties()).group(ItemGroup.COMBAT))
    );

    public static final RegistryObject<Item> ENERGY_XP_BOOTS = ITEMS.register("energy_xp_boots", () ->
            new ArmorItem(CustomArmorMaterial.ENERGY_XP, EquipmentSlotType.FEET, (new Item.Properties()).group(ItemGroup.COMBAT))
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
