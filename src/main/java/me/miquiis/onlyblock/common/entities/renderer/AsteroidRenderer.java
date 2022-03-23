package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.AsteroidEntity;
import me.miquiis.onlyblock.common.models.AsteroidModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class AsteroidRenderer extends GeoProjectilesRenderer<AsteroidEntity> {
    public AsteroidRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AsteroidModel());
    }

    @Override
    public void render(AsteroidEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.scale(10, 10, 10);
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
