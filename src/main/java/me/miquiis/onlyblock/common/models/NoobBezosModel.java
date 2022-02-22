package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.Noob1234;
import me.miquiis.onlyblock.common.entities.NoobBezos;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class NoobBezosModel extends AnimatedGeoModel<NoobBezos> {
    @Override
    public ResourceLocation getModelLocation(NoobBezos object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/noob.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(NoobBezos object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/noob_bezos.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(NoobBezos animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/player.animation.json");
    }

    @Override
    public void setLivingAnimations(NoobBezos entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("Head");

        LivingEntity entityIn = (LivingEntity) entity;
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        //head.setRotationX(-extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
