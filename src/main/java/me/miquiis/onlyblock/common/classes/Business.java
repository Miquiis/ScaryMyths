package me.miquiis.onlyblock.common.classes;

import net.minecraft.nbt.CompoundNBT;

import java.util.UUID;

public class Business {

    private String businessId;
    private String businessName;
    private UUID businessOwner;

    public Business(String businessId, String businessName, UUID businessOwner)
    {
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessOwner = businessOwner;
    }

    public CompoundNBT serializeNBT()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("BusinessID", businessId);
        compoundNBT.putString("BusinessName", businessName);
        if (businessOwner != null)
        compoundNBT.putUniqueId("BusinessOwner", businessOwner);
        return compoundNBT;
    }

    public void deserializeNBT(CompoundNBT compoundNBT)
    {
        this.businessId = compoundNBT.getString("BusinessID");
        this.businessName = compoundNBT.getString("BusinessName");
        if (compoundNBT.contains("BusinessOwner")) this.businessOwner = compoundNBT.getUniqueId("BusinessOwner");
    }

    public void setBusinessOwner(UUID businessOwner) {
        this.businessOwner = businessOwner;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public UUID getBusinessOwner() {
        return businessOwner;
    }
}
