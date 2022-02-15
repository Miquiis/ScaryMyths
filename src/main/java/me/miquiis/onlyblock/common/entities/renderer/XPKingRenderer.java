package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.XPKingEntity;
import me.miquiis.onlyblock.common.models.XPKingModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class XPKingRenderer extends GeoEntityRenderer<XPKingEntity> {

    public XPKingRenderer(EntityRendererManager renderManager) {
        super(renderManager, new XPKingModel());
    }

    @Override
    protected void applyRotations(XPKingEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStackIn.scale(2, 2, 2);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(XPKingEntity entity) {
        return null;
    }
}
