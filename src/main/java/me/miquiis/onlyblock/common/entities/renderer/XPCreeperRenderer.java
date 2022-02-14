package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.client.renderer.entity.CreeperRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.util.ResourceLocation;

public class XPCreeperRenderer extends CreeperRenderer {

    private final ResourceLocation CREEPER_TEXTURE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_creeper");

    public XPCreeperRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(CreeperEntity entity) {
        return CREEPER_TEXTURE;
    }
}
