package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.XPKingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class XPKingModel extends AnimatedGeoModel<XPKingEntity> {
    @Override
    public ResourceLocation getModelLocation(XPKingEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/xp_king.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(XPKingEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_king.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(XPKingEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/xp_king.animation.json");
    }

    @Override
    public void setLivingAnimations(XPKingEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
