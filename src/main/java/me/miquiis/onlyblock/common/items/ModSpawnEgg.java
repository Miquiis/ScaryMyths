package me.miquiis.onlyblock.common.items;

import net.minecraft.entity.EntityType;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModSpawnEgg extends SpawnEggItem {

    protected static final List<ModSpawnEgg> UNADDED_EGGS = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entityTypeSupplier;

    public ModSpawnEgg(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, int primaryColor, int secondaryColor, Properties builder)
    {
        super(null, primaryColor, secondaryColor, builder);
        this.entityTypeSupplier = Lazy.of(entityTypeSupplier::get);
        UNADDED_EGGS.add(this);
    }

    public static void initSoawnEggs()
    {
        final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "EGGS");

        for (final SpawnEggItem spawnEgg : UNADDED_EGGS)
        {
            EGGS.put(spawnEgg.getType(null), spawnEgg);
        }
        UNADDED_EGGS.clear();
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT nbt) {
        return entityTypeSupplier.get();
    }
}
