package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.WarhammerItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WarhammerModel extends AnimatedGeoModel<WarhammerItem> {

    @Override
    public ResourceLocation getModelLocation(WarhammerItem object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/warhammer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(WarhammerItem object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/entity/xp_king.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(WarhammerItem animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/warhammer.animation.json");
    }

}
