package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.ElonMusk;
import me.miquiis.onlyblock.common.entities.Noob1234;
import me.miquiis.onlyblock.common.models.ElonMuskModel;
import me.miquiis.onlyblock.common.models.NoobModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ElonMuskRenderer extends GeoEntityRenderer<ElonMusk> {

    public ElonMuskRenderer(EntityRendererManager renderManager) {
        super(renderManager, new ElonMuskModel());
    }

    @Override
    protected void applyRotations(ElonMusk entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStackIn.scale(1.1f, 1.1f, 1.1f);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(ElonMusk entity) {
        return null;
    }

}
