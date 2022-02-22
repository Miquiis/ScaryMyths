package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.MoneyShotgun;
import me.miquiis.onlyblock.common.models.MoneyShotgunModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MoneyShotgunRenderer extends GeoItemRenderer<MoneyShotgun> {
    public MoneyShotgunRenderer() {
        super(new MoneyShotgunModel());
    }
}
