package me.miquiis.onlyblock.common.capability.interfaces;

import me.miquiis.onlyblock.common.classes.AmazonIsland;
import me.miquiis.onlyblock.common.classes.BillionaireIsland;
import me.miquiis.onlyblock.common.classes.StockIsland;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public interface IOnlyBlock {

    void sync(ServerPlayerEntity player);
    void setPlayer(ServerPlayerEntity player);
    void deserializeNBT(CompoundNBT data);

    CompoundNBT serializeNBT();
    AmazonIsland getAmazonIsland();
    BillionaireIsland getBillionaireIsland();
    StockIsland getStockIsland();
}
