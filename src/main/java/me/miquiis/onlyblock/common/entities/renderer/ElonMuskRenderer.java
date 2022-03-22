package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.ElonMuskEntity;
import me.miquiis.onlyblock.common.models.ElonMuskModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ElonMuskRenderer extends GeoEntityRenderer<ElonMuskEntity> {

    public ElonMuskRenderer(EntityRendererManager renderManager) {
        super(renderManager, new ElonMuskModel());
    }

    @Override
    protected void applyRotations(ElonMuskEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(ElonMuskEntity entity) {
        return null;
    }

}
