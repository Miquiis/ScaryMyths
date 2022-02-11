package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.ResourceLocation;

public class XpChickenRenderer extends ChickenRenderer {
    private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_chicken.png");


    public XpChickenRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ChickenEntity entity) {
        return CHICKEN_TEXTURES;
    }
}
