package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.MoneyArmorItem;
import me.miquiis.onlyblock.common.models.MoneyArmorModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class MoneyArmorRenderer extends GeoArmorRenderer<MoneyArmorItem> {
    public MoneyArmorRenderer() {
        super(new MoneyArmorModel());
    }
}
