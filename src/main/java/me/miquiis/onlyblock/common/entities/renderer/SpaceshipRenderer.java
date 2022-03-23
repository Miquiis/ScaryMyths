package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.GoldenHelicopterEntity;
import me.miquiis.onlyblock.common.entities.SpaceshipEntity;
import me.miquiis.onlyblock.common.models.GoldenHelicopterModel;
import me.miquiis.onlyblock.common.models.SpaceshipModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SpaceshipRenderer extends GeoEntityRenderer<SpaceshipEntity> {

    public SpaceshipRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SpaceshipModel());
    }

    @Override
    protected void applyRotations(SpaceshipEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
//        matrixStackIn.scale(2, 2, 2);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(SpaceshipEntity entity) {
        return null;
    }

}
