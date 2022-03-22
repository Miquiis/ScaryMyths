package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.OneMilEntity;
import me.miquiis.onlyblock.common.models.OneMilModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OneMilRenderer extends GeoEntityRenderer<OneMilEntity> {

    public OneMilRenderer(EntityRendererManager renderManager) {
        super(renderManager, new OneMilModel());
    }

    @Override
    protected void applyRotations(OneMilEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStackIn.scale(3, 3, 3);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(OneMilEntity entity) {
        return null;
    }

}
