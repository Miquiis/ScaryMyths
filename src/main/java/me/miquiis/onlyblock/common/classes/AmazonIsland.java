package me.miquiis.onlyblock.common.classes;

import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AmazonIsland {

    private final List<Vector3d> POSSIBLE_DELIVERIES = new ArrayList<>(Arrays.asList(
            new Vector3d(-35, 67, 11),
            new Vector3d(-34, 67, 20),
            new Vector3d(-46, 69, 12),
            new Vector3d(-49, 63, 19)
    ));

    private final int TARGET_AMOUNT = 1000000;
    private final int PACKAGE_AMOUNT = 100000;

    private Vector3d currentDelivery;
    private int amountGathered;

    public AmazonIsland()
    {
        currentDelivery = POSSIBLE_DELIVERIES.get(0);
        amountGathered = 0;
    }

    public void reset()
    {
        currentDelivery = POSSIBLE_DELIVERIES.get(0);
        amountGathered = 0;
    }

    public void deliver()
    {
        if (amountGathered + PACKAGE_AMOUNT >= TARGET_AMOUNT)
        {
            amountGathered = TARGET_AMOUNT;
            currentDelivery = null;
        } else
        {
            amountGathered += PACKAGE_AMOUNT;
            currentDelivery = POSSIBLE_DELIVERIES.get(MathUtils.getRandomMax(POSSIBLE_DELIVERIES.size()));
        }
    }

    public void addAmountGathered(int amountGathered)
    {
        this.amountGathered += amountGathered;
    }

    public void takeAmountGathered(int amountGathered)
    {
        this.amountGathered -= amountGathered;
    }

    public void setAmountGathered(int amountGathered)
    {
        this.amountGathered = amountGathered;
    }

    public Vector3d getCurrentDelivery() {
        return currentDelivery;
    }

    public int getAmountGathered() {
        return amountGathered;
    }

    public void deserializeNBT(CompoundNBT compoundNBT)
    {
        if (compoundNBT.contains("DeliverX"))
        {
            currentDelivery = new Vector3d(compoundNBT.getDouble("DeliverX"), compoundNBT.getDouble("DeliverY"), compoundNBT.getDouble("DeliverZ"));
        } else
        {
            currentDelivery = null;
        }
        if (compoundNBT.contains("GatheredAmount"))
        {
            amountGathered = compoundNBT.getInt("GatheredAmount");
        }
    }

    public CompoundNBT serializeNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (getCurrentDelivery() != null){
            compoundNBT.putDouble("DeliverX", getCurrentDelivery().getX());
            compoundNBT.putDouble("DeliverY", getCurrentDelivery().getY());
            compoundNBT.putDouble("DeliverZ", getCurrentDelivery().getZ());
        }
        compoundNBT.putInt("GatheredAmount", amountGathered);
        return compoundNBT;
    }

    public float getPercentage()
    {
        return amountGathered / (float)TARGET_AMOUNT;
    }
}
