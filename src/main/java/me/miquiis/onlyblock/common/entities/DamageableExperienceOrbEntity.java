package me.miquiis.onlyblock.common.entities;

import me.miquiis.onlyblock.common.classes.ExpExplosion;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class DamageableExperienceOrbEntity extends Entity {
   public int xpColor;
   public int xpOrbAge;
   private int xpOrbHealth = 5;
   private Entity closestEntity;
   private int xpTargetColor;
   private double damage;

   public DamageableExperienceOrbEntity(World worldIn, double x, double y, double z, double damage) {
      this(EntityRegister.DAMAGEABLE_EXPERIENCE_ORB.get(), worldIn);
      this.setPosition(x, y, z);
      this.rotationYaw = (float)(this.rand.nextDouble() * 360.0D);
      this.damage = damage;
      this.xpOrbAge = 0;
   }

   public DamageableExperienceOrbEntity(EntityType<DamageableExperienceOrbEntity> p_i50382_1_, World entity) {
      super(p_i50382_1_, entity);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void registerData() {
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      this.prevPosX = this.getPosX();
      this.prevPosY = this.getPosY();
      this.prevPosZ = this.getPosZ();

      if (this.areEyesInFluid(FluidTags.WATER)) {
         this.applyFloatMotion();
      }

      double d0 = 8.0D;
      if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
         this.xpTargetColor = this.xpColor;
      }

      BlockPos.getAllInBox(getBoundingBox().grow(0, 0.1, 0)).forEach(blockPos -> {
         final BlockState blockState = world.getBlockState(blockPos);
         if (blockState.getBlock() != Blocks.AIR)
         {
            ExpExplosion explosion = new ExpExplosion(world, null, null, null, getPosX(), getPosY(), getPosZ(), 2, false, Explosion.Mode.BREAK, true);
            explosion.doExplosionA();
            explosion.doExplosionB(true);
            remove();
         }
      });

      world.getEntitiesInAABBexcluding(this, getBoundingBox(), entity -> {
         return !(entity instanceof PlayerEntity) && entity instanceof LivingEntity;
      }).forEach(entity -> {
         LivingEntity livingEntity = (LivingEntity) entity;
         livingEntity.hurtResistantTime = 0;
         livingEntity.attackEntityFrom(DamageSource.MAGIC, (float)damage);
         livingEntity.hurtResistantTime = 0;

         ExpExplosion explosion = new ExpExplosion(world, null, null, null, getPosX(), getPosY(), getPosZ(), 2, false, Explosion.Mode.BREAK, false);
         explosion.doExplosionA();
         explosion.doExplosionB(true);
         remove();
      });

      this.move(MoverType.SELF, this.getMotion());

      ++this.xpColor;
      ++this.xpOrbAge;

      if (this.xpOrbAge >= 60) {
         this.remove();
      }
   }

   private void applyFloatMotion() {
      Vector3d vector3d = this.getMotion();
      this.setMotion(vector3d.x * (double)0.99F, Math.min(vector3d.y + (double)5.0E-4F, (double)0.06F), vector3d.z * (double)0.99F);
   }

   protected void doWaterSplashEffect() {
   }

   /**
    * Called when the entity is attacked.
    */
   public boolean attackEntityFrom(DamageSource source, float amount) {
      if (this.world.isRemote || this.removed) return false; //Forge: Fixes MC-53850
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         this.markVelocityChanged();
         this.xpOrbHealth = (int)((float)this.xpOrbHealth - amount);
         if (this.xpOrbHealth <= 0) {
            this.remove();
         }

         return false;
      }
   }

   public void writeAdditional(CompoundNBT compound) {
      compound.putShort("Health", (short)this.xpOrbHealth);
      compound.putShort("Age", (short)this.xpOrbAge);
      compound.putDouble("Damage", (double)this.damage);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      this.xpOrbHealth = compound.getShort("Health");
      this.xpOrbAge = compound.getShort("Age");
      this.damage = compound.getDouble("Damage");
   }

   /**
    * Called by a player entity when they collide with an entity
    */
   public void onCollideWithPlayer(PlayerEntity entityIn) {
   }

   private int durabilityToXp(int durability) {
      return durability / 2;
   }

   private int xpToDurability(int xp) {
      return xp * 2;
   }

   /**
    * Get a fragment of the maximum experience points value for the supplied value of experience points value.
    */
   public static int getXPSplit(int expValue) {
      if (expValue >= 2477) {
         return 2477;
      } else if (expValue >= 1237) {
         return 1237;
      } else if (expValue >= 617) {
         return 617;
      } else if (expValue >= 307) {
         return 307;
      } else if (expValue >= 149) {
         return 149;
      } else if (expValue >= 73) {
         return 73;
      } else if (expValue >= 37) {
         return 37;
      } else if (expValue >= 17) {
         return 17;
      } else if (expValue >= 7) {
         return 7;
      } else {
         return expValue >= 3 ? 3 : 1;
      }
   }

   /**
    * Returns true if it's possible to attack this entity with an item.
    */
   public boolean canBeAttackedWithItem() {
      return false;
   }

   @Override
   public IPacket<?> createSpawnPacket() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
