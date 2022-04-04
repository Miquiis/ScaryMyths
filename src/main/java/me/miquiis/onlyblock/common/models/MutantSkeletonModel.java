package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MutantSkeletonModel extends AnimatedGeoModel {

    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/mutant_skeleton.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/mutant_skeleton.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/mutant_skeleton.animation.json");
    }

}
