package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.BuffetEntity;
import me.miquiis.onlyblock.common.entities.JeffBezosEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class JeffBezosModel extends AnimatedGeoModel<JeffBezosEntity> {
    @Override
    public ResourceLocation getModelLocation(JeffBezosEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/player.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(JeffBezosEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/jeff_bezos.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(JeffBezosEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/player.animation.json");
    }

    @Override
    public void setLivingAnimations(JeffBezosEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
