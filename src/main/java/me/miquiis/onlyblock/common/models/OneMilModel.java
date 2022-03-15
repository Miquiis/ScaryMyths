package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.Noob1234;
import me.miquiis.onlyblock.common.entities.OneMilEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class OneMilModel extends AnimatedGeoModel<OneMilEntity> {
    @Override
    public ResourceLocation getModelLocation(OneMilEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/one_mil.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(OneMilEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/one_mil.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(OneMilEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/one_mil.animation.json");
    }
}
