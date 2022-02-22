package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.JetpackArmorItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class JetpackItemModel extends AnimatedGeoModel<JetpackArmorItem> {
    @Override
    public ResourceLocation getModelLocation(JetpackArmorItem object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/jetpack_item.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(JetpackArmorItem object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/models/armor/jetpack.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(JetpackArmorItem animatable) {
        return null;
    }
}
