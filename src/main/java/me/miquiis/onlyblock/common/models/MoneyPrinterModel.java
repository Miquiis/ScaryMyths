package me.miquiis.onlyblock.common.models;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.tileentities.MoneyPrinterTileEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MoneyPrinterModel extends AnimatedGeoModel<MoneyPrinterTileEntity> {
    @Override
    public ResourceLocation getModelLocation(MoneyPrinterTileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "geo/old_money_printer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MoneyPrinterTileEntity object) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "textures/block/money_printer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MoneyPrinterTileEntity animatable) {
        return new ResourceLocation(OnlyBlock.MOD_ID, "animations/money_printer.animation.json");
    }
}
