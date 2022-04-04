package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MutantCreeperModel extends AnimatedGeoModel {

    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/mutant_creeper.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/mutant_creeper.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/mutant_creeper.animation.json");
    }

}
