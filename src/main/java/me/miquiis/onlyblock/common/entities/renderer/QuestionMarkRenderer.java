package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.HundredEntity;
import me.miquiis.onlyblock.common.entities.QuestionMarkEntity;
import me.miquiis.onlyblock.common.models.HundredModel;
import me.miquiis.onlyblock.common.models.QuestionMarkModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class QuestionMarkRenderer extends GeoEntityRenderer<QuestionMarkEntity> {

    public QuestionMarkRenderer(EntityRendererManager renderManager) {
        super(renderManager, new QuestionMarkModel());
    }

    @Override
    protected void applyRotations(QuestionMarkEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStackIn.scale(1, 1, 1);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(QuestionMarkEntity entity) {
        return null;
    }

}
