package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.ElonMusk;
import me.miquiis.onlyblock.common.entities.Noob1234;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class ElonMuskModel extends AnimatedGeoModel<ElonMusk> {
    @Override
    public ResourceLocation getModelLocation(ElonMusk object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/elon_musk.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ElonMusk object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/elon_musk.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ElonMusk animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/elon_musk.animation.json");
    }

    @Override
    public void setLivingAnimations(ElonMusk entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
