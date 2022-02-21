package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.AirdropEntity;
import me.miquiis.onlyblock.common.models.AirdropModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.model.BoatModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class AirdropRenderer extends LivingRenderer<AirdropEntity, AirdropModel> {
    private static final ResourceLocation AIRDROP_TEXTURES = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/airdrop.png");

    public AirdropRenderer(EntityRendererManager rendererManager) {
        super(rendererManager, new AirdropModel(), 1f);
    }

    @Override
    public ResourceLocation getEntityTexture(AirdropEntity entity) {
        return AIRDROP_TEXTURES;
    }

    @Override
    protected boolean canRenderName(AirdropEntity entity) {
        return false;
    }
}
