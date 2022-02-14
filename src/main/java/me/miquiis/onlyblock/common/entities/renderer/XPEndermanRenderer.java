package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.layers.AbstractEyesLayer;
import net.minecraft.client.renderer.entity.layers.HeldBlockLayer;
import net.minecraft.client.renderer.entity.model.EndermanModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

public class XPEndermanRenderer extends MobRenderer<EndermanEntity, EndermanModel<EndermanEntity>> {
    private static final ResourceLocation ENDERMAN_TEXTURES = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_enderman.png");
    private final Random rnd = new Random();

    public XPEndermanRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new EndermanModel<>(0.0F), 0.5F);
        this.addLayer(new EndermanEyesLayer<>(this));
        this.addLayer(new HeldBlockLayer(this));
    }

    public void render(EndermanEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        BlockState blockstate = entityIn.getHeldBlockState();
        EndermanModel<EndermanEntity> endermanmodel = this.getEntityModel();
        endermanmodel.isCarrying = blockstate != null;
        endermanmodel.isAttacking = entityIn.isScreaming();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public Vector3d getRenderOffset(EndermanEntity entityIn, float partialTicks) {
        if (entityIn.isScreaming()) {
            double d0 = 0.02D;
            return new Vector3d(this.rnd.nextGaussian() * 0.02D, 0.0D, this.rnd.nextGaussian() * 0.02D);
        } else {
            return super.getRenderOffset(entityIn, partialTicks);
        }
    }

    /**
     * Returns the location of an entity's texture.
     */
    public ResourceLocation getEntityTexture(EndermanEntity entity) {
        return ENDERMAN_TEXTURES;
    }
}

class EndermanEyesLayer<T extends LivingEntity> extends AbstractEyesLayer<T, EndermanModel<T>> {
    private static final RenderType RENDER_TYPE = RenderType.getEyes(new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_enderman_eyes.png"));

    public EndermanEyesLayer(IEntityRenderer<T, EndermanModel<T>> rendererIn) {
        super(rendererIn);
    }

    public RenderType getRenderType() {
        return RENDER_TYPE;
    }
}
