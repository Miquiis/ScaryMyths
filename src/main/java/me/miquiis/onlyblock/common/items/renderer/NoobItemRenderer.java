package me.miquiis.onlyblock.common.items.renderer;

import me.miquiis.onlyblock.common.items.MoneyShotgun;
import me.miquiis.onlyblock.common.items.NoobItem;
import me.miquiis.onlyblock.common.models.MoneyShotgunModel;
import me.miquiis.onlyblock.common.models.NoobItemModel;
import me.miquiis.onlyblock.common.models.NoobModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class NoobItemRenderer extends GeoItemRenderer<NoobItem> {
    public NoobItemRenderer() {
        super(new NoobItemModel());
    }
}
