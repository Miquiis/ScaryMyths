package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.FlyingTeslaEntity;
import me.miquiis.onlyblock.common.models.FlyingTeslaModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FlyingTeslaRenderer extends GeoEntityRenderer<FlyingTeslaEntity> {
    public FlyingTeslaRenderer(EntityRendererManager renderManager) {
        super(renderManager, new FlyingTeslaModel());
    }

    @Override
    public void render(FlyingTeslaEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entity.getControllingPassenger() != null && Minecraft.getInstance().player.equals(entity.getControllingPassenger()))
        {
            if (Minecraft.getInstance().gameSettings.getPointOfView() == PointOfView.FIRST_PERSON)
            return;
        }
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(FlyingTeslaEntity entity) {
        return null;
    }
}
