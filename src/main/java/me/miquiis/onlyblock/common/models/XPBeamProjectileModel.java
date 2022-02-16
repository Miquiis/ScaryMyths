package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.XPBeamProjectileEntity;
import me.miquiis.onlyblock.common.entities.XPWarhammerProjectileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class XPBeamProjectileModel extends AnimatedGeoModel<XPBeamProjectileEntity> {
    @Override
    public ResourceLocation getModelLocation(XPBeamProjectileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/xp_beam.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(XPBeamProjectileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_beam.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(XPBeamProjectileEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/warhammer.animation.json");
    }
}
