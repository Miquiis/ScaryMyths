package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.HundredEntity;
import me.miquiis.onlyblock.common.entities.OneMilEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HundredModel extends AnimatedGeoModel<HundredEntity> {
    @Override
    public ResourceLocation getModelLocation(HundredEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/hundred.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(HundredEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/hundred.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(HundredEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/hundred.animation.json");
    }
}
