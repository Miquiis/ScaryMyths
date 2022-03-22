package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.BuffetEntity;
import me.miquiis.onlyblock.common.entities.JeffBezosEntity;
import me.miquiis.onlyblock.common.models.BuffettModel;
import me.miquiis.onlyblock.common.models.JeffBezosModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class JeffBezosRenderer extends GeoEntityRenderer<JeffBezosEntity> {

    public JeffBezosRenderer(EntityRendererManager renderManager) {
        super(renderManager, new JeffBezosModel());
    }

    @Override
    public ResourceLocation getEntityTexture(JeffBezosEntity entity) {
        return null;
    }

    @Override
    protected void applyRotations(JeffBezosEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

}
