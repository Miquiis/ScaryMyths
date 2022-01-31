package me.miquiis.onlyblock.common.custombar.server;

import me.miquiis.onlyblock.common.custombar.common.BarInfo;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

public class BarSave extends WorldSavedData implements Supplier {

    public CompoundNBT data = new CompoundNBT();

    public BarSave()
    {
        super("CustomBars");
    }

    @Override
    public Object get() {
        return this;
    }

    @Override
    public void read(CompoundNBT nbt) {
        data = nbt.getCompound("CustomBars");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put("CustomBars", data);
        return compound;
    }

    public void saveBars(Collection<BarInfo> bars)
    {
        ListNBT listNBT = new ListNBT();
        for (BarInfo barInfo : bars)
        {
            CompoundNBT barData = new CompoundNBT();
            barData.putUniqueId("uuid", barInfo.getUniqueID());
            barData.putString("text", barInfo.getText().getString());
            barData.putFloat("percent", barInfo.getPercent());
            listNBT.add(barData);
        }
        data.put("ActiveBars", listNBT);
    }

    public Collection<BarInfo> loadBars()
    {
        Collection<BarInfo> barInfos = new ArrayList<>();
        ListNBT barsList = data.getList("ActiveBars", 10);
        for(int i = 0; i < barsList.size(); ++i) {
            CompoundNBT compoundNBT = barsList.getCompound(i);
            BarInfo barInfo = new BarInfo(compoundNBT.getUniqueId("uuid"), new StringTextComponent(compoundNBT.getString("text")), compoundNBT.getFloat("percent"));
            barInfos.add(barInfo);
        }
        return barInfos;
    }

    public static BarSave forWorld(ServerWorld world)
    {
        DimensionSavedDataManager storage = world.getSavedData();
        Supplier<BarSave> sup = new BarSave();
        return storage.getOrCreate(sup, "custombars");
    }
}
