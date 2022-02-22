package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.entities.Noob1234;
import me.miquiis.onlyblock.common.items.NoobItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class NoobItemModel extends AnimatedGeoModel<NoobItem> {
    @Override
    public ResourceLocation getModelLocation(NoobItem object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/noob.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(NoobItem object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/noob.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(NoobItem animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/player.animation.json");
    }
}
