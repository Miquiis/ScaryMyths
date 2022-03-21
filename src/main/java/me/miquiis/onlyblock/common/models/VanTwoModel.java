package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.VanEntity;
import me.miquiis.onlyblock.common.entities.VanTwoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class VanTwoModel extends AnimatedGeoModel<VanTwoEntity> {
    @Override
    public ResourceLocation getModelLocation(VanTwoEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/van.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(VanTwoEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/van_two.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(VanTwoEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/van.animation.json");
    }

    @Override
    public void setLivingAnimations(VanTwoEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Van");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
