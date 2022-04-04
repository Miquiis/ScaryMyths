package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.models.SaleModel;
import me.miquiis.onlyblock.common.models.SoldModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SaleRenderer extends GeoEntityRenderer {

    public SaleRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SaleModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
