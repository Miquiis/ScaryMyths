package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.GoldenProjectileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GoldenProjectileModel extends AnimatedGeoModel<GoldenProjectileEntity> {
    @Override
    public ResourceLocation getModelLocation(GoldenProjectileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/golden_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GoldenProjectileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/item/golden_bazooka.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GoldenProjectileEntity animatable) {
        return null;
    }
}
