package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.XPCreeperEntity;
import me.miquiis.onlyblock.common.models.XPCreeperModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.CreeperChargeLayer;
import net.minecraft.client.renderer.entity.layers.EnergyLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CreeperModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IChargeableMob;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class XPCreeperRenderer extends MobRenderer<XPCreeperEntity, XPCreeperModel<XPCreeperEntity>> {

    private final ResourceLocation CREEPER_TEXTURE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_creeper.png");

    public XPCreeperRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new XPCreeperModel<>(), 0.5F);
        this.addLayer(new XPCreeperChargeLayer(this));
    }

    protected void preRenderCallback(XPCreeperEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
        float f = entitylivingbaseIn.getCreeperFlashIntensity(partialTickTime);
        float f1 = 1.0F + MathHelper.sin(f * 100.0F) * f * 0.01F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        f = f * f;
        f = f * f;
        float f2 = (1.0F + f * 0.4F) * f1;
        float f3 = (1.0F + f * 0.1F) / f1;
        matrixStackIn.scale(f2, f3, f2);
    }

    protected float getOverlayProgress(XPCreeperEntity livingEntityIn, float partialTicks) {
        float f = livingEntityIn.getCreeperFlashIntensity(partialTicks);
        return (int)(f * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(f, 0.5F, 1.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(XPCreeperEntity entity) {
        return CREEPER_TEXTURE;
    }
}

class XPCreeperChargeLayer extends EnergyLayer<XPCreeperEntity, XPCreeperModel<XPCreeperEntity>> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private final XPCreeperModel<XPCreeperEntity> creeperModel = new XPCreeperModel<>(2.0F);

    public XPCreeperChargeLayer(IEntityRenderer<XPCreeperEntity, XPCreeperModel<XPCreeperEntity>> p_i226038_1_) {
        super(p_i226038_1_);
    }

    protected float func_225634_a_(float p_225634_1_) {
        return p_225634_1_ * 0.01F;
    }

    protected ResourceLocation func_225633_a_() {
        return LIGHTNING_TEXTURE;
    }

    protected EntityModel<XPCreeperEntity> func_225635_b_() {
        return this.creeperModel;
    }
}