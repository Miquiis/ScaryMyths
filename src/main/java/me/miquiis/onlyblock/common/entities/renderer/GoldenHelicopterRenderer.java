package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.GoldenHelicopterEntity;
import me.miquiis.onlyblock.common.entities.VanEntity;
import me.miquiis.onlyblock.common.models.GoldenHelicopterModel;
import me.miquiis.onlyblock.common.models.VanModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GoldenHelicopterRenderer extends GeoEntityRenderer<GoldenHelicopterEntity> {

    public GoldenHelicopterRenderer(EntityRendererManager renderManager) {
        super(renderManager, new GoldenHelicopterModel());
    }

    @Override
    protected void applyRotations(GoldenHelicopterEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
//        matrixStackIn.scale(2, 2, 2);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(GoldenHelicopterEntity entity) {
        return null;
    }

}
