package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.GoldenHelicopterItem;
import me.miquiis.onlyblock.common.models.GoldenHelicopterModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GoldenHelicopterItemRenderer extends GeoItemRenderer<GoldenHelicopterItem> {
    public GoldenHelicopterItemRenderer() {
        super(new GoldenHelicopterModel());
    }
}
