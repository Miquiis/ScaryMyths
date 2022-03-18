package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.GoldenHelicopterItem;
import me.miquiis.onlyblock.common.items.JetpackArmorItem;
import me.miquiis.onlyblock.common.models.GoldenHelicopterModel;
import me.miquiis.onlyblock.common.models.JetpackItemModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GoldenHelicopterItemRenderer extends GeoItemRenderer<GoldenHelicopterItem> {
    public GoldenHelicopterItemRenderer() {
        super(new GoldenHelicopterModel());
    }
}
