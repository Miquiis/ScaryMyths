package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class XPSkeletonRenderer extends SkeletonRenderer {

    private final ResourceLocation SKELETON_TEXTURE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_skeleton");

    public XPSkeletonRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(AbstractSkeletonEntity entity) {
        return SKELETON_TEXTURE;
    }
}
