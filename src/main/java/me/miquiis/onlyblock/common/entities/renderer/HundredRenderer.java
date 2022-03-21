package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.FiveHundredEntity;
import me.miquiis.onlyblock.common.entities.HundredEntity;
import me.miquiis.onlyblock.common.models.FiveHundredModel;
import me.miquiis.onlyblock.common.models.HundredModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HundredRenderer extends GeoEntityRenderer<HundredEntity> {

    public HundredRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HundredModel());
    }

    @Override
    protected void applyRotations(HundredEntity entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStackIn.scale(3, 3, 3);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(HundredEntity entity) {
        return null;
    }

}
