package me.miquiis.onlyblock.common.classes;

import me.miquiis.onlyblock.common.entities.SedanEntity;
import me.miquiis.onlyblock.common.entities.SedanTwoEntity;
import me.miquiis.onlyblock.common.entities.VanEntity;
import me.miquiis.onlyblock.common.entities.VanTwoEntity;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AmazonIsland implements IUnlockable {

    private static final List<Vector3d> POSSIBLE_DELIVERIES = new ArrayList<>(Arrays.asList(
            new Vector3d(1000, 66, 947),
            new Vector3d(977, 66, 1009),
            new Vector3d(977, 66, 1048),
            new Vector3d(1027, 66, 1048),
            new Vector3d(1050, 66, 1020),
            new Vector3d(1024, 66, 978),
            new Vector3d(1028, 66, 947),
            new Vector3d(1050, 66, 970),
            new Vector3d(957, 66, 977),
            new Vector3d(950, 66, 947),
            new Vector3d(926, 66, 982),
            new Vector3d(949, 66, 1048),
            new Vector3d(926, 66, 1009),
            new Vector3d(1023, 66, 1024),
            new Vector3d(926, 66, 1032)
    ));

    private static final List<Vector3d> CAR_SPAWNS = new ArrayList<>(Arrays.asList(
            new Vector3d(997, 66, 974),
            new Vector3d(997, 66, 1021),
            new Vector3d(1003, 66, 974),
            new Vector3d(1003, 66, 1021),
            new Vector3d(1023, 66, 961),
            new Vector3d(1000, 66, 955),
            new Vector3d(976, 66, 961),
            new Vector3d(1000, 66, 955),
            new Vector3d(976, 66, 961),
            new Vector3d(945, 66, 955),
            new Vector3d(931, 66, 980),
            new Vector3d(937, 66, 966),
            new Vector3d(937, 66, 1003),
            new Vector3d(931, 66, 1032),
            new Vector3d(950, 66, 1035),
            new Vector3d(977, 66, 1041),
            new Vector3d(1000, 66, 1036),
            new Vector3d(1020, 66, 1041),
            new Vector3d(1039, 66, 1029),
            new Vector3d(1045, 66, 1002),
            new Vector3d(1039, 66, 968),
            new Vector3d(1037, 66, 955)
    ));

    private final Vector3d CENTER_ISLAND = new Vector3d(176, 100, 0);

    private final int TARGET_AMOUNT = 1000000;
    private final int PACKAGE_AMOUNT = 100000;

    private Vector3d currentDelivery;
    private int amountGathered;
    private boolean isLocked;

    private List<Entity> spawnedCars;

    public AmazonIsland()
    {
        currentDelivery = null;
        amountGathered = 0;
    }

    public void startMinigame(World world)
    {
        System.out.println("Here");
        amountGathered = 0;
        currentDelivery = POSSIBLE_DELIVERIES.get(0);
        spawnCars(world);
    }

    private void spawnCars(World world)
    {
        System.out.println("Here 2");
        if (spawnedCars != null) spawnedCars.forEach(Entity::remove);
        System.out.println("Here 3");
        spawnedCars = new ArrayList<>();
        CAR_SPAWNS.forEach(vector3d -> {
            if (MathUtils.chance(30))
            {
                if (MathUtils.chance(50))
                {
                    System.out.println("Here 4");
                    SedanEntity sedanEntity = new SedanEntity(world);
                    sedanEntity.setPosition(vector3d.getX() + 0.5, vector3d.getY() + 0.5, vector3d.getZ() + 0.5);
                    sedanEntity.enablePersistence();
                    world.addEntity(sedanEntity);
                    spawnedCars.add(sedanEntity);
                } else
                {
                    SedanTwoEntity sedanEntity = new SedanTwoEntity(world);
                    sedanEntity.setPosition(vector3d.getX() + 0.5, vector3d.getY() + 0.5, vector3d.getZ() + 0.5);
                    sedanEntity.enablePersistence();
                    world.addEntity(sedanEntity);
                    spawnedCars.add(sedanEntity);
                }
            } else
            {
                if (MathUtils.chance(50))
                {
                    VanEntity sedanEntity = new VanEntity(world);
                    sedanEntity.setPosition(vector3d.getX() + 0.5, vector3d.getY() + 0.5, vector3d.getZ() + 0.5);
                    sedanEntity.enablePersistence();
                    world.addEntity(sedanEntity);
                    spawnedCars.add(sedanEntity);
                } else
                {
                    VanTwoEntity sedanEntity = new VanTwoEntity(world);
                    sedanEntity.setPosition(vector3d.getX() + 0.5, vector3d.getY() + 0.5, vector3d.getZ() + 0.5);
                    sedanEntity.enablePersistence();
                    world.addEntity(sedanEntity);
                    spawnedCars.add(sedanEntity);
                }
            }
        });
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

    public Vector3d getRandomTNTLocation()
    {
        double randomX = MathUtils.getRandomMinMax(-30d, 30d);
        double randomZ = MathUtils.getRandomMinMax(-30d, 30d);

        if (randomX < 0) randomX-= 10;
        if (randomX > 0) randomX+= 10;
        if (randomZ < 0) randomZ-= 20;
        if (randomZ > 0) randomZ+= 20;

        return CENTER_ISLAND.add(randomX, MathUtils.getRandomMinMax(0, 30), randomZ);
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
        if (compoundNBT.contains("IsLocked"))
        {
            isLocked = compoundNBT.getBoolean("IsLocked");
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
        compoundNBT.putBoolean("IsLocked", isLocked);
        return compoundNBT;
    }

    public float getPercentage()
    {
        return amountGathered / (float)TARGET_AMOUNT;
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public void unlock() {
        isLocked = false;
    }

    @Override
    public void lock() {
        isLocked = true;
    }
}
