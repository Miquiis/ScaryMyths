package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.OneMilEntity;
import me.miquiis.onlyblock.common.entities.QuestionMarkEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class QuestionMarkModel extends AnimatedGeoModel<QuestionMarkEntity> {
    @Override
    public ResourceLocation getModelLocation(QuestionMarkEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/question_mark.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(QuestionMarkEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/question_mark.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(QuestionMarkEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/question_mark.animation.json");
    }
}
