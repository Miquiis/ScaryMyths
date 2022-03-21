package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.VanEntity;
import me.miquiis.onlyblock.common.entities.VanTwoEntity;
import me.miquiis.onlyblock.common.models.VanModel;
import me.miquiis.onlyblock.common.models.VanTwoModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VanTwoRenderer extends GeoEntityRenderer<VanTwoEntity> {

    public VanTwoRenderer(EntityRendererManager renderManager) {
        super(renderManager, new VanTwoModel());
    }

    @Override
    protected void applyRotations(VanTwoEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
//        matrixStackIn.scale(2, 2, 2);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(VanTwoEntity entity) {
        return null;
    }

}
