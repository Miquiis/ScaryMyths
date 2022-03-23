package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.BuffetEntity;
import me.miquiis.onlyblock.common.entities.GiantEarthEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class GiantEarthModel extends AnimatedGeoModel<GiantEarthEntity> {
    @Override
    public ResourceLocation getModelLocation(GiantEarthEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/giant_earth.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(GiantEarthEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/giant_earth.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GiantEarthEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/hundred.animation.json");
    }
}
