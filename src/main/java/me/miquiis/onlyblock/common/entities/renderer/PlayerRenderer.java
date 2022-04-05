package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import me.miquiis.onlyblock.common.entities.AlfredEntity;
import me.miquiis.onlyblock.common.entities.BobEntity;
import me.miquiis.onlyblock.common.entities.DealerEntity;
import me.miquiis.onlyblock.common.entities.HackerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class PlayerRenderer extends GeoEntityRenderer {
    public PlayerRenderer(EntityRendererManager renderManager, AnimatedGeoModel modelProvider) {
        super(renderManager, modelProvider);
    }

    @Override
    public void render(LivingEntity entity, float entityYaw, float partialTicks, MatrixStack stack, IRenderTypeBuffer bufferIn, int packedLightIn) {
        String name = entity instanceof BobEntity ? "\u00A76\u00a7lBob, the Builder" : entity instanceof DealerEntity ? "\u00A77\u00a7lDealer" : entity instanceof AlfredEntity ? "\u00A7f\u00a7lAlfred" : entity instanceof HackerEntity ? "\u00A7a\u00A7l\u00a7kHacker" : "";
        if (!name.isEmpty())
        renderName(entity, new StringTextComponent(name), stack, bufferIn, packedLightIn);
        super.render(entity, entityYaw, partialTicks, stack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity) {
        return null;
    }
}
