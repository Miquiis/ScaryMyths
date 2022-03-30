package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.entities.LifeKeeperEntity;
import me.miquiis.onlyblock.common.models.KeeperModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class LifeKeeperRenderer extends GeoEntityRenderer<LifeKeeperEntity> {
    public LifeKeeperRenderer(EntityRendererManager renderManager) {
        super(renderManager, new KeeperModel(0));
    }

    @Override
    public ResourceLocation getEntityTexture(LifeKeeperEntity entity) {
        return null;
    }
}
