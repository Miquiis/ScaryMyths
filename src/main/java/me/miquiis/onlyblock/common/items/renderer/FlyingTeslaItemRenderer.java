package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.FlyingTeslaItem;
import me.miquiis.onlyblock.common.models.FlyingTeslaModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class FlyingTeslaItemRenderer extends GeoItemRenderer<FlyingTeslaItem> {
    public FlyingTeslaItemRenderer() {
        super(new FlyingTeslaModel());
    }
}
