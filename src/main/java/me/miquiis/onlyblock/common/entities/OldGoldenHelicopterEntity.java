package me.miquiis.onlyblock.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class OldGoldenHelicopterEntity extends LivingEntity implements IAnimatable {

    final AnimationFactory factory = new AnimationFactory(this);

    private boolean leftInputDown, rightInputDown, forwardInputDown, backInputDown;

    public OldGoldenHelicopterEntity(EntityType<? extends LivingEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.preventEntitySpawning = true;
    }

//    public GoldenHelicopterEntity(World worldIn, double x, double y, double z) {
//        this(EntityRegister.SPACESHIP.get(), worldIn);
//        this.setPosition(x, y, z);
//        this.setMotion(Vector3d.ZERO);
//        this.prevPosX = x;
//        this.prevPosY = y;
//        this.prevPosZ = z;
//    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return LivingEntity.registerAttributes().createMutableAttribute(ForgeMod.ENTITY_GRAVITY.get(), 0f);
    }

    @Override
    public boolean canCollide(Entity entity) {
        return !entity.isRidingSameEntity(entity);
    }

    @Override
    public HandSide getPrimaryHand() {
        return null;
    }

    @Override
    public double getMountedYOffset() {
        return 0.1D;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.removed;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        //this.setRotation(ticksExisted, 0);
        if (canPassengerSteer())
        {
            if (this.world.isRemote && this.getControllingPassenger() != null)
            {
//                if (this.world.getGameTime() % 5 == 0) this.getControllingPassenger().playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 1f, (float)MathUtils.getRandomMinMax(0.7f, 0.8f));
                Vector3d lookVec = this.getControllingPassenger().getLookVec().mul(0.1, 0.1, 0.1);
                Vector3d velocity = new Vector3d(0, 0, 0);

                if (forwardInputDown) {
                    velocity = new Vector3d(lookVec.getX(), (lookVec.getY() * 0.2) + 0.01, lookVec.getZ());
                } else if (backInputDown) {
                    velocity = new Vector3d(lookVec.getX() * 0.5, (lookVec.getY() * 0.2) - 0.0125, lookVec.getZ() * 0.5);
                }
                this.setRotation(this.getControllingPassenger().rotationYaw, this.getControllingPassenger().rotationPitch);
                this.setMotion(this.getMotion().getX() + velocity.getX(), this.getMotion().getY() + velocity.getY(), this.getMotion().getZ() + velocity.getZ());
            }
            this.move(MoverType.SELF, this.getMotion());
        }
        if (getControllingPassenger() == null)
        {
            if (isOnGround())
            {
                this.setMotion(0, 0, 0);
            } else
            {
                this.setMotion(0, -1.0, 0);
            }
        }
    }

    public void updateInputs(boolean forwardInputDown, boolean backInputDown, boolean leftInputDown, boolean rightInputDown)
    {
        this.forwardInputDown = forwardInputDown;
        this.backInputDown = backInputDown;
        this.leftInputDown = leftInputDown;
        this.rightInputDown = rightInputDown;
    }

    public void updatePassenger(Entity passenger) {
        if (this.isPassenger(passenger)) {
            float f = 5.0F;
            float f1 = (float)((this.removed ? (double)0.01F : this.getMountedYOffset()) + passenger.getYOffset());
            if (this.getPassengers().size() > 1) {
                int i = this.getPassengers().indexOf(passenger);
                if (i == 0) {
                    f = 2.0F;
                } else {
                    f = -0.6F;
                }

                if (passenger instanceof AnimalEntity) {
                    f = (float)((double)f + 0.2D);
                }
            }

            Vector3d vector3d = (new Vector3d((double)f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
            passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY() + (double)f1, this.getPosZ() + vector3d.z);

            if (passenger instanceof AnimalEntity && this.getPassengers().size() > 1) {
                int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
                passenger.setRenderYawOffset(((AnimalEntity)passenger).renderYawOffset + (float)j);
                passenger.setRotationYawHead(passenger.getRotationYawHead() + (float)j);
            }

        }
    }

    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isSecondaryUseActive()) {
//            remove();
//            ItemStack itemStack = new ItemStack(ItemRegister.SPACESHIP.get());
//            SpaceshipItem.repairShip(itemStack);
//            player.addItemStackToInventory(itemStack);
            return ActionResultType.CONSUME;
        } else {
            if (!this.world.isRemote) {
                return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
            } else {
                return ActionResultType.SUCCESS;
            }
        }
    }

    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() == 0;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return super.canBeRidden(entityIn);
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getItemStackFromSlot(EquipmentSlotType slotIn) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EquipmentSlotType slotIn, ItemStack stack) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 10, this::predicate));
    }

    private PlayState predicate(AnimationEvent animationEvent) {
        animationEvent.getController().setAnimation(new AnimationBuilder().addAnimation("fly"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
