package me.miquiis.onlyblock.common.tileentity.renderer;

import me.miquiis.onlyblock.common.models.XpMinerModel;
import me.miquiis.onlyblock.common.tileentity.XpMinerTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class XpMinerTileEntityRenderer extends GeoBlockRenderer<XpMinerTileEntity> {
    public XpMinerTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new XpMinerModel());
    }
}
