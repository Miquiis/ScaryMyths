package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.entities.XPBeamProjectileEntity;
import me.miquiis.onlyblock.common.entities.XPWarhammerProjectileEntity;
import me.miquiis.onlyblock.common.models.XPBeamProjectileModel;
import me.miquiis.onlyblock.common.models.XPWarhammerProjectileModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class XPBeamRenderer extends GeoProjectilesRenderer<XPBeamProjectileEntity> {
    public XPBeamRenderer(EntityRendererManager renderManager) {
        super(renderManager, new XPBeamProjectileModel());
    }
}
