package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.BankOwner;
import me.miquiis.onlyblock.common.entities.ElonMusk;
import me.miquiis.onlyblock.common.models.BankOwnerModel;
import me.miquiis.onlyblock.common.models.ElonMuskModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BankOwnerRenderer extends GeoEntityRenderer<BankOwner> {

    public BankOwnerRenderer(EntityRendererManager renderManager) {
        super(renderManager, new BankOwnerModel());
    }

    @Override
    protected void applyRotations(BankOwner entityLiving, MatrixStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStackIn.scale(1.5f, 1.5f, 1.5f);
        super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
    }

    @Override
    public ResourceLocation getEntityTexture(BankOwner entity) {
        return null;
    }

}
