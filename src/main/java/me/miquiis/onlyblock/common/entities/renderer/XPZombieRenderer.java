package me.miquiis.onlyblock.common.entities.renderer;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class XPZombieRenderer extends ZombieRenderer {

    private final ResourceLocation ZOMBIE_TEXTURE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_zombie");

    public XPZombieRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
    }

    @Override
    public ResourceLocation getEntityTexture(ZombieEntity entity) {
        return ZOMBIE_TEXTURE;
    }
}
