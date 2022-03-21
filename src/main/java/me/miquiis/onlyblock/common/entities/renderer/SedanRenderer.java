package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.SedanEntity;
import me.miquiis.onlyblock.common.entities.VanEntity;
import me.miquiis.onlyblock.common.models.SedanModel;
import me.miquiis.onlyblock.common.models.VanModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SedanRenderer extends GeoEntityRenderer<SedanEntity> {

    public SedanRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SedanModel());
    }

    @Override
    protected void applyRotations(SedanEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
//        matrixStackIn.scale(2, 2, 2);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(SedanEntity entity) {
        return null;
    }

}
