package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GiantEarthEntity extends MonsterEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public GiantEarthEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        setInvulnerable(true);
        enablePersistence();
        this.ignoreFrustumCheck = true;
    }

    public GiantEarthEntity(World worldIn) {
        super(EntityRegister.GIANT_EARTH.get(),worldIn);
        setInvulnerable(true);
        enablePersistence();
        this.ignoreFrustumCheck = true;
    }

    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 50.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5).createMutableAttribute(Attributes.FOLLOW_RANGE, 50).createMutableAttribute(Attributes.ARMOR, 5).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.5F);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<GiantEarthEntity>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
