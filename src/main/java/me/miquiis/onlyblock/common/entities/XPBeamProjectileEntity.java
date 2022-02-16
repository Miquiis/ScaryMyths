package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class XPBeamProjectileEntity extends ThrowableEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public XPBeamProjectileEntity(EntityType<? extends ThrowableEntity> p_i50161_1_, World p_i50161_2_) {
        super(p_i50161_1_, p_i50161_2_);
    }

    public XPBeamProjectileEntity(World p_i50161_2_) {
        super(EntityRegister.XP_BEAM_PROJECTILE.get(), p_i50161_2_);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        result.getEntity().attackEntityFrom(DamageSource.GENERIC, 2f);
        if (this.getShooter() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) getShooter();
            player.giveExperiencePoints(10);
            player.world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1f, (float)MathUtils.getRandomMinMax(0.8, 1.1));
        }
        this.remove();
        super.onEntityHit(result);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<XPBeamProjectileEntity>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected float getGravityVelocity() {
        return 0f;
    }

    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
