package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.MoneyArmorItem;
import me.miquiis.onlyblock.common.models.BootsMoneyArmorModel;
import me.miquiis.onlyblock.common.models.ChestplateMoneyArmorModel;
import me.miquiis.onlyblock.common.models.HelmetMoneyArmorModel;
import me.miquiis.onlyblock.common.models.LeggingsMoneyArmorModel;
import net.minecraft.inventory.EquipmentSlotType;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MoneyItemArmorRenderer extends GeoItemRenderer<MoneyArmorItem> {
    public MoneyItemArmorRenderer(EquipmentSlotType slotType) {
        super(slotType == EquipmentSlotType.HEAD ? new HelmetMoneyArmorModel() : slotType == EquipmentSlotType.CHEST ? new ChestplateMoneyArmorModel() : slotType == EquipmentSlotType.LEGS ? new LeggingsMoneyArmorModel() : new BootsMoneyArmorModel());
    }
}
