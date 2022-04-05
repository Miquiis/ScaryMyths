package me.miquiis.onlyblock.common.classes;

import me.miquiis.onlyblock.common.entities.ISign;
import me.miquiis.onlyblock.common.entities.SaleEntity;
import me.miquiis.onlyblock.common.entities.SoldEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.lang.ref.WeakReference;
import java.util.UUID;

public class Business {

    private String businessId;
    private String businessName;
    private UUID businessOwner;
    private Vector3d textPosition;

    public Business(String businessId, String businessName, UUID businessOwner, Vector3d textPosition)
    {
        this.businessId = businessId;
        this.businessName = businessName;
        this.businessOwner = businessOwner;
        this.textPosition = textPosition;
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
        else this.businessOwner = null;
    }

    public void checkForFlyingText(ServerWorld world) {

        boolean found = false;
        for (LivingEntity entity : world.getEntitiesWithinAABB(LivingEntity.class, AxisAlignedBB.fromVector(textPosition).grow(3, 3, 3)))
        {
            if (entity instanceof ISign)
            {
                found = true;
                if (entity instanceof SaleEntity)
                {
                    if (businessOwner != null)
                    {
                        entity.remove();
                        found = false;
                    }
                }
                if (entity instanceof SoldEntity)
                {
                    if (businessOwner == null)
                    {
                        entity.remove();
                        found = false;
                    }
                }
                break;
            }
        }

        if (!found)
        {
            if (businessOwner == null)
            {
                SaleEntity soldEntity = new SaleEntity(world);
                soldEntity.setPosition(textPosition.getX(), textPosition.getY(), textPosition.getZ());
                soldEntity.getPersistentData().putString("Business", businessId);
                soldEntity.enablePersistence();
                world.addEntity(soldEntity);
            } else
            {
                SoldEntity soldEntity = new SoldEntity(world);
                soldEntity.setPosition(textPosition.getX(), textPosition.getY(), textPosition.getZ());
                soldEntity.getPersistentData().putString("Business", businessId);
                soldEntity.enablePersistence();
                world.addEntity(soldEntity);
            }
        }
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
