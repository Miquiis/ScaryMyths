package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.TNTProjectileEntity;
import me.miquiis.onlyblock.common.models.TNTProjectileModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class TNTProjectileRenderer extends GeoProjectilesRenderer<TNTProjectileEntity> {
    public TNTProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager, new TNTProjectileModel());
    }

    @Override
    public void render(TNTProjectileEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-90));
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }
}
