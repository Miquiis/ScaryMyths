package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.TNTProjectileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TNTProjectileModel extends AnimatedGeoModel<TNTProjectileEntity> {
    @Override
    public ResourceLocation getModelLocation(TNTProjectileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/tnt_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(TNTProjectileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/item/tnt_bazooka.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(TNTProjectileEntity animatable) {
        return null;
    }
}
