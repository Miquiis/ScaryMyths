package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.entities.BuildKeeperEntity;
import me.miquiis.onlyblock.common.entities.LifeKeeperEntity;
import me.miquiis.onlyblock.common.models.KeeperModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BuildKeeperRenderer extends GeoEntityRenderer<BuildKeeperEntity> {
    public BuildKeeperRenderer(EntityRendererManager renderManager) {
        super(renderManager, new KeeperModel(1));
    }

    @Override
    public ResourceLocation getEntityTexture(BuildKeeperEntity entity) {
        return null;
    }
}
