package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.XPKingEntity;
import me.miquiis.onlyblock.common.entities.XPWarhammerProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class XPWarhammerProjectileModel extends AnimatedGeoModel<XPWarhammerProjectileEntity> {
    @Override
    public ResourceLocation getModelLocation(XPWarhammerProjectileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/warhammer_proj.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(XPWarhammerProjectileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_king.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(XPWarhammerProjectileEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/warhammer.animation.json");
    }
}
