package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.blocks.BaseHorizontalBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class SedanTwoEntity extends MobEntity implements IAnimatable {

    private AnimationFactory factory = new AnimationFactory(this);
    private double randomSpeed;
    private boolean isFirstMove;

    public SedanTwoEntity(EntityType<? extends MobEntity> type, World worldIn) {
        super(type, worldIn);
        this.randomSpeed = MathUtils.getRandomMinMax(-0.2, 0.1);
        this.isFirstMove = true;
    }

    public SedanTwoEntity(World worldIn) {
        super(EntityRegister.SEDAN_TWO.get(), worldIn);
        this.randomSpeed = MathUtils.getRandomMinMax(-0.2, 0.1);
        this.isFirstMove = true;
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos foundDirection = getNearestDirectionBlock();
        if (foundDirection == null) return;
        BlockState foundBlockState = world.getBlockState(foundDirection);
        if (foundBlockState.getBlock() != BlockRegister.ROAD_DIRECTION.get()) return;
        Direction direction = foundBlockState.get(BaseHorizontalBlock.HORIZONTAL_FACING);
        if (isFirstMove)
        {
            setPosition(foundDirection.getX() + 0.5, getPosY(), foundDirection.getZ() + 0.5);
            isFirstMove = false;
            return;
        }
        moveController.setMoveTo(getPosX() + direction.getXOffset(), getPosY() + direction.getYOffset(), getPosZ() + direction.getZOffset(), 1.0 + randomSpeed);
    }


    @Override
    protected void collideWithEntity(Entity entityIn) {
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
    }

    @Override
    public void onCollideWithPlayer(PlayerEntity entityIn) {
        if (entityIn.world.isRemote)
        {
            Vector3d direction = entityIn.getLookVec().mul(-1, -1, -1).mul(0.5, 0.5, 0.5);
            entityIn.addVelocity(direction.getX(), 0.3, direction.getZ());
        }else
        {
            ICurrency currency = entityIn.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
            currency.addOrSubtractAmount(-25000);
        }
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

    private BlockPos getNearestDirectionBlock()
    {
        final AxisAlignedBB box = getBoundingBox().grow(-3, 2, -3);
        Iterable<BlockPos> blocksAround = BlockPos.getAllInBoxMutable((int)box.minX, (int)box.minY, (int)box.minZ, (int)box.maxX, (int)box.maxY, (int)box.maxZ);

        for (BlockPos blockPos : blocksAround)
        {
            if (world.getBlockState(blockPos).getBlock() == BlockRegister.ROAD_DIRECTION.get())
            {
                return blockPos;
            }
        }

        return null;
    }
}
