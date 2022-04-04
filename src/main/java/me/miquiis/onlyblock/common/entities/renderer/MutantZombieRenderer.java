package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.entities.MutantCreeperEntity;
import me.miquiis.onlyblock.common.entities.MutantZombieEntity;
import me.miquiis.onlyblock.common.models.MutantCreeperModel;
import me.miquiis.onlyblock.common.models.MutantZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MutantZombieRenderer extends GeoEntityRenderer<MutantZombieEntity> {
    public MutantZombieRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MutantZombieModel());
    }

    @Override
    public ResourceLocation getEntityTexture(MutantZombieEntity entity) {
        return null;
    }
}
