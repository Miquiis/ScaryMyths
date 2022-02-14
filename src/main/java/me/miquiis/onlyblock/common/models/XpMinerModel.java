package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.tileentity.XpMinerTileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class XpMinerModel extends AnimatedGeoModel<XpMinerTileEntity> {
    @Override
    public ResourceLocation getModelLocation(XpMinerTileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/xp_miner.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(XpMinerTileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/block/xp_miner.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(XpMinerTileEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/xp_miner.animation.json");
    }
}
