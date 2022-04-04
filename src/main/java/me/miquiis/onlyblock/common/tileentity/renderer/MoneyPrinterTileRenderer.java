package me.miquiis.onlyblock.common.tileentity.renderer;

import me.miquiis.onlyblock.common.models.MoneyPrinterModel;
import me.miquiis.onlyblock.common.tileentity.MoneyPrinterTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class MoneyPrinterTileRenderer extends GeoBlockRenderer<MoneyPrinterTileEntity> {
    public MoneyPrinterTileRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new MoneyPrinterModel());
    }
}
