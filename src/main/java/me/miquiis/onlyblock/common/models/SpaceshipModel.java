package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

public class SpaceshipModel extends AnimatedGeoModel {
    @Override
    public ResourceLocation getModelLocation(Object object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/spaceship.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Object object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/spaceship.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Object animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/golden_helicopter.animation.json");
    }

    @Override
    public void setLivingAnimations(Object entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations((IAnimatable) entity, uniqueID, customPredicate);
//        IBone head = this.getAnimationProcessor().getBone("Golden_Helicopter");
//
//        LivingEntity entityIn = (LivingEntity) entity;
//        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
//        System.out.println(extraData.headPitch);
//        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
//        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
    }
}
