package me.miquiis.onlyblock.common.entities.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.common.entities.XPKingEntity;
import me.miquiis.onlyblock.common.entities.XPWarhammerProjectileEntity;
import me.miquiis.onlyblock.common.models.XPKingModel;
import me.miquiis.onlyblock.common.models.XPWarhammerProjectileModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class XPWarhammerRenderer extends GeoProjectilesRenderer<XPWarhammerProjectileEntity> {
    public XPWarhammerRenderer(EntityRendererManager renderManager) {
        super(renderManager, new XPWarhammerProjectileModel());
    }
}
