package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.VanEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class VanModel extends AnimatedGeoModel<VanEntity> {
    @Override
    public ResourceLocation getModelLocation(VanEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/van.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(VanEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/van.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(VanEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/van.animation.json");
    }

    @Override
    public void setLivingAnimations(VanEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Van");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
