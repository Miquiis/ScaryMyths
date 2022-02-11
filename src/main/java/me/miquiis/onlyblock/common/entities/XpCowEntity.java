package me.miquiis.onlyblock.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;

public class XpCowEntity extends CowEntity {
    public XpCowEntity(EntityType<? extends CowEntity> type, World worldIn) {
        super(type, worldIn);
    }
}
