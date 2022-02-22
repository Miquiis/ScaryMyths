package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.items.MoneyShotgun;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MoneyShotgunModel extends AnimatedGeoModel<MoneyShotgun> {
    @Override
    public ResourceLocation getModelLocation(MoneyShotgun object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/money_shotgun.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MoneyShotgun object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/item/money_shotgun.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MoneyShotgun animatable) {
        return null;
    }
}
