package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.GoldenProjectileEntity;
import me.miquiis.onlyblock.common.models.GoldenProjectileModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class GoldenProjectileRenderer extends GeoProjectilesRenderer<GoldenProjectileEntity> {
    public GoldenProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager, new GoldenProjectileModel());
    }

    @Override
    public void render(GoldenProjectileEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90));
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
