package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.models.SoldModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SoldRenderer extends GeoEntityRenderer {

    public SoldRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SoldModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
