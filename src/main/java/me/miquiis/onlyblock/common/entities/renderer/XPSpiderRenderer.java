package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.SpiderRenderer;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.model.SpiderModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;

public class XPSpiderRenderer<T extends SpiderEntity> extends MobRenderer<T, SpiderModel<T>> {

    private final ResourceLocation SPIDER_TEXTURE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_spider.png");

    public XPSpiderRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new SpiderModel<>(), 0.8F);
        this.addLayer(new SpiderEyesLayer<>(this));
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn) {
        return 180.0F;
    }

    @Override
    public ResourceLocation getEntityTexture(SpiderEntity entity) {
        return SPIDER_TEXTURE;
    }
}

class SpiderEyesLayer<T extends Entity, M extends SpiderModel<T>> extends AbstractEyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_spider_eyes.png"));

    public SpiderEyesLayer(IEntityRenderer<T, M> rendererIn) {
        super(rendererIn);
    }

    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
