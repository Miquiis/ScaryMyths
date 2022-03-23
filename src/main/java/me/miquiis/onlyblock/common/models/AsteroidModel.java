package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.AsteroidEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AsteroidModel extends AnimatedGeoModel<AsteroidEntity> {
    @Override
    public ResourceLocation getModelLocation(AsteroidEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/asteroid.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AsteroidEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/asteroid.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AsteroidEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/asteroid.animation.json");
    }
}
