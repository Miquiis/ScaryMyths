package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.Noob1234;
import me.miquiis.onlyblock.common.models.NoobModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NoobRenderer extends GeoEntityRenderer<Noob1234> {

    public NoobRenderer(EntityRendererManager renderManager) {
        super(renderManager, new NoobModel());
    }

    @Override
    protected void applyRotations(Noob1234 entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
//        matrixStackIn.scale(2, 2, 2);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(Noob1234 entity) {
        return null;
    }

}
