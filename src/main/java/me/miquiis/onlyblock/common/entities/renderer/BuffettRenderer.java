package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.BuffetEntity;
import me.miquiis.onlyblock.common.entities.ElonMuskEntity;
import me.miquiis.onlyblock.common.models.BuffettModel;
import me.miquiis.onlyblock.common.models.ElonMuskModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BuffettRenderer extends GeoEntityRenderer<BuffetEntity> {

    public BuffettRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BuffettModel());
    }

    @Override
    public ResourceLocation getEntityTexture(BuffetEntity entity) {
        return null;
    }

    @Override
    protected void applyRotations(BuffetEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

}
