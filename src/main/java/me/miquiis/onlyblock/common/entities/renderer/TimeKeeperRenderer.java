package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.common.entities.BuildKeeperEntity;
import me.miquiis.onlyblock.common.entities.TimeKeeperEntity;
import me.miquiis.onlyblock.common.models.KeeperModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TimeKeeperRenderer extends GeoEntityRenderer<TimeKeeperEntity> {
    public TimeKeeperRenderer(EntityRendererManager renderManager) {
        super(renderManager, new KeeperModel(2));
    }

    @Override
    public ResourceLocation getEntityTexture(TimeKeeperEntity entity) {
        return null;
    }
}
