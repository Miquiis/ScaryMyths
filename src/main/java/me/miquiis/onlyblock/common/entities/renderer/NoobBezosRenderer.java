package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.BankOwner;
import me.miquiis.onlyblock.common.entities.NoobBezos;
import me.miquiis.onlyblock.common.models.BankOwnerModel;
import me.miquiis.onlyblock.common.models.NoobBezosModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class NoobBezosRenderer extends GeoEntityRenderer<NoobBezos> {

    public NoobBezosRenderer(EntityRendererManager renderManager) {
        super(renderManager, new NoobBezosModel());
    }

    @Override
    protected void applyRotations(NoobBezos entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
//        matrixStackIn.scale(2, 2, 2);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(NoobBezos entity) {
        return null;
    }

}
