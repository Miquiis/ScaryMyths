package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.miquiis.onlyblock.common.entities.AsteroidEntity;
import me.miquiis.onlyblock.common.models.AsteroidModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

import javax.annotation.Nullable;

public class AsteroidRenderer extends GeoProjectilesRenderer<AsteroidEntity> {
    public AsteroidRenderer(EntityRendererManager renderManager) {
        super(renderManager, new AsteroidModel());
    }

    @Override
    public void render(GeoModel model, AsteroidEntity animatable, float partialTicks, RenderType type, MatrixStack matrixStackIn, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.scale(5, 5, 5);
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
