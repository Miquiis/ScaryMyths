package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.SedanEntity;
import me.miquiis.onlyblock.common.entities.SedanTwoEntity;
import me.miquiis.onlyblock.common.models.SedanModel;
import me.miquiis.onlyblock.common.models.SedanTwoModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SedanTwoRenderer extends GeoEntityRenderer<SedanTwoEntity> {

    public SedanTwoRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SedanTwoModel());
    }

    @Override
    protected void applyRotations(SedanTwoEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
//        matrixStackIn.scale(2, 2, 2);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(SedanTwoEntity entity) {
        return null;
    }

}
