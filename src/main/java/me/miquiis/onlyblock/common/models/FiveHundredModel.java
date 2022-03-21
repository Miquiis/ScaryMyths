package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.FiveHundredEntity;
import me.miquiis.onlyblock.common.entities.OneMilEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FiveHundredModel extends AnimatedGeoModel<FiveHundredEntity> {
    @Override
    public ResourceLocation getModelLocation(FiveHundredEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/five_hundred.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FiveHundredEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/five_hundred.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FiveHundredEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/five_hundred.animation.json");
    }
}
