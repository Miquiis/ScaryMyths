package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.entities.MutantCreeperEntity;
import me.miquiis.onlyblock.common.entities.MutantSkeletonEntity;
import me.miquiis.onlyblock.common.models.MutantSkeletonModel;
import me.miquiis.onlyblock.common.models.MutantZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MutantSkeletonRenderer extends GeoEntityRenderer<MutantSkeletonEntity> {
    public MutantSkeletonRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MutantSkeletonModel());
    }

    @Override
    public ResourceLocation getEntityTexture(MutantSkeletonEntity entity) {
        return null;
    }
}
