package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.GiantEarthEntity;
import me.miquiis.onlyblock.common.entities.JeffBezosEntity;
import me.miquiis.onlyblock.common.models.GiantEarthModel;
import me.miquiis.onlyblock.common.models.JeffBezosModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GiantEarthRenderer extends GeoEntityRenderer<GiantEarthEntity> {

    public GiantEarthRenderer(EntityRendererManager renderManager) {
        super(renderManager, new GiantEarthModel());
    }

    @Override
    public ResourceLocation getEntityTexture(GiantEarthEntity entity) {
        return null;
    }



    @Override
    protected void applyRotations(GiantEarthEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStackIn.scale(50, 50, 50);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

}
