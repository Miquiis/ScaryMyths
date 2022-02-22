package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.JetpackArmorItem;
import me.miquiis.onlyblock.common.models.JetpackArmorModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class JetpackArmorRenderer extends GeoArmorRenderer<JetpackArmorItem> {
    public JetpackArmorRenderer() {
        super(new JetpackArmorModel());
    }
}
