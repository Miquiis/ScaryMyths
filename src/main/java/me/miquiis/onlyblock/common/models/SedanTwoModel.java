package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.SedanEntity;
import me.miquiis.onlyblock.common.entities.SedanTwoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class SedanTwoModel extends AnimatedGeoModel<SedanTwoEntity> {
    @Override
    public ResourceLocation getModelLocation(SedanTwoEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/sedan.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SedanTwoEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/sedan_two.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SedanTwoEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/sedan.animation.json");
    }

    @Override
    public void setLivingAnimations(SedanTwoEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("bone");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
