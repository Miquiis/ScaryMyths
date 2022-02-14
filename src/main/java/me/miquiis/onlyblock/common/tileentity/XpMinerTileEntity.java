package me.miquiis.onlyblock.common.tileentity;

import me.miquiis.onlyblock.common.blocks.CustomBlockTags;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.server.ServerWorld;
import software.bernie.example.entity.GeoExampleEntity;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.function.Predicate;

public class XpMinerTileEntity extends TileEntity implements IAnimatable, ITickableTileEntity {

    private AnimationFactory factory = new AnimationFactory(this);

    private int lastDrillTick;

    private MinerState currentState;

    public XpMinerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        currentState = MinerState.IDLE;
        lastDrillTick = 0;
    }

    public XpMinerTileEntity()
    {
        super(ModTileEntity.XP_MINER_TILE_ENTITY.get());
        currentState = MinerState.IDLE;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        switch (currentState)
        {
            case IDLE:
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
                break;
            }
            case STARTING:
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("start_drilling", false));
                break;
            }
            case DRILLING:
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("drilling", true));
                break;
            }
            case ENDING:
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("stop_drilling", false));
                break;
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void tick() {
        BlockState targetBlock = world.getBlockState(getPos().down());
        if (targetBlock.getBlock() != BlockRegister.XP_BLOCK.get())
        {
            if (currentState == MinerState.DRILLING)
            {
                currentState = MinerState.ENDING;
            }
            else
            {
                lastDrillTick = 0;
                currentState = MinerState.IDLE;
            }
        }
        else {
            lastDrillTick++;
            if (!world.isRemote)
            {
                final ServerWorld serverWorld = (ServerWorld)world;
                if (MathUtils.chance(10))
                {
                    serverWorld.spawnParticle(ParticleTypes.SMOKE, getPos().getX() + 0.5, getPos().getY() + 0.9, getPos().getZ() + 0.5, 5, 0, 0, 0, 0);
                }
                if (MathUtils.chance(40))
                {
                    serverWorld.spawnParticle(new BlockParticleData(ParticleTypes.BLOCK, targetBlock), getPos().getX() + 0.5, getPos().getY() - 0.1, getPos().getZ() + 0.5, 5, 0, 0, 0, 0);
                }
            }
            currentState = MinerState.DRILLING;
        }

        if (lastDrillTick >= 10)
        {
            lastDrillTick = 0;
            drops();
        }
    }

    private void drops()
    {
        if (world.isRemote) return;

        ServerWorld serverWorld = (ServerWorld) world;

        List<ExperienceOrbEntity> entityList = serverWorld.getEntitiesWithinAABB(EntityType.EXPERIENCE_ORB, getRenderBoundingBox().grow(32d), experienceOrbEntity -> true);
        if (entityList.size() >= 250)
        {
            return;
        }

        world.playSound(null, getPos().getX() + 0.5, getPos().getY() - 0.1, getPos().getZ() + 0.5, SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.BLOCKS, 0.5f, (float)MathUtils.getRandomMinMax(0.8f, 1.2f));
        ExperienceOrbEntity experienceOrbEntity = new ExperienceOrbEntity(world, getPos().getX() + 0.5, getPos().getY() + 1.1, getPos().getZ() + 0.5, 5);
        world.addEntity(experienceOrbEntity);
    }

    private enum MinerState {
        IDLE,
        STARTING,
        DRILLING,
        ENDING
    }

}
