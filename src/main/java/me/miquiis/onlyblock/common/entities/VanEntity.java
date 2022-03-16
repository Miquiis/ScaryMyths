package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.blocks.BaseHorizontalBlock;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.concurrent.atomic.AtomicReference;

public class VanEntity extends MobEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);

    public VanEntity(EntityType<? extends MobEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        BlockState foundDirection = getNearestDirectionBlock();
        if (foundDirection == null) return;
        Direction direction = foundDirection.get(BaseHorizontalBlock.HORIZONTAL_FACING);
        moveController.setMoveTo(getPosX() + direction.getXOffset(), getPosY() + direction.getYOffset(), getPosZ() + direction.getZOffset(), 0.5);
    }


    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        super.onCollideWithPlayer(entityIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 20, this::predicate));
    }

    private PlayState predicate(AnimationEvent animationEvent) {
        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("drive"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    private BlockState getNearestDirectionBlock()
    {
        AtomicReference<BlockState> blockPosAtomicReference = new AtomicReference<>();
        BlockPos.getAllInBox(getBoundingBox().grow(-3, 2, -3)).forEach(blockPos -> {
            if (world.getBlockState(blockPos).getBlock() == BlockRegister.ROAD_DIRECTION.get())
            {
                blockPosAtomicReference.set(world.getBlockState(blockPos));
            }
        });
        return blockPosAtomicReference.get();
    }
}
