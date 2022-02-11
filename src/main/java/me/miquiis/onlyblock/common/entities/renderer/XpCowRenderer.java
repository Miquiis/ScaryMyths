package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.CowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.util.ResourceLocation;

public class XpCowRenderer extends CowRenderer {

    private static final ResourceLocation COW_TEXTURES = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_cow.png");

    public XpCowRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(CowEntity entity) {
        return COW_TEXTURES;
    }
}
