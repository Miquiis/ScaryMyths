package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.entities.MutantCreeperEntity;
import me.miquiis.onlyblock.common.models.MutantCreeperModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MutantCreeperRenderer extends GeoEntityRenderer<MutantCreeperEntity> {
    public MutantCreeperRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MutantCreeperModel());
    }

    @Override
    public ResourceLocation getEntityTexture(MutantCreeperEntity entity) {
        return null;
    }
}
