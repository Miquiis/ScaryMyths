package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.JetpackArmorItem;
import me.miquiis.onlyblock.common.models.JetpackItemModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class JetpackItemRenderer extends GeoItemRenderer<JetpackArmorItem> {
    public JetpackItemRenderer() {
        super(new JetpackItemModel());
    }
}
