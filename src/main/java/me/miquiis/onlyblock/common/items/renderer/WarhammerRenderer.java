package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.WarhammerItem;
import me.miquiis.onlyblock.common.models.WarhammerModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class WarhammerRenderer extends GeoItemRenderer<WarhammerItem> {
    public WarhammerRenderer() {
        super(new WarhammerModel());
    }
}
