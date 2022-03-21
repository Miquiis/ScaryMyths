package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.SedanEntity;
import me.miquiis.onlyblock.common.entities.VanEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class SedanModel extends AnimatedGeoModel<SedanEntity> {
    @Override
    public ResourceLocation getModelLocation(SedanEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/sedan.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(SedanEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/sedan.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SedanEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/sedan.animation.json");
    }

    @Override
    public void setLivingAnimations(SedanEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("bone");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
