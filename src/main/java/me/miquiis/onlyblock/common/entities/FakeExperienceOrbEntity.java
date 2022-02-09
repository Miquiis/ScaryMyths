package me.miquiis.onlyblock.common.entities;

import java.util.Map.Entry;

import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnExperienceOrbPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class FakeExperienceOrbEntity extends Entity {
   public int xpColor;
   public int xpOrbAge;
   public int delayBeforeCanPickup;
   private int xpOrbHealth = 5;
   private final int originalXpValue;
   public int xpValue;
   private PlayerEntity closestPlayer;
   private int xpTargetColor;

   public FakeExperienceOrbEntity(World worldIn, double x, double y, double z, int expValue) {
      this(EntityRegister.FAKE_EXPERIENCE_ORB.get(), worldIn);
      this.setPosition(x, y, z);
      this.xpValue = rand.nextInt(10);
      this.rotationYaw = (float)(this.rand.nextDouble() * 360.0D);
      this.setMotion((this.rand.nextDouble() * (double)0.2F - (double)0.1F), this.rand.nextDouble() * 0.2D, (this.rand.nextDouble() * (double)0.2F - (double)0.1F));
      this.xpOrbAge = 5960;
   }

   public FakeExperienceOrbEntity(EntityType<FakeExperienceOrbEntity> p_i50382_1_, World entity) {
      super(p_i50382_1_, entity);
      this.xpValue = rand.nextInt(10);
      this.originalXpValue = xpValue;
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
      } else if (!this.hasNoGravity()) {
         this.setMotion(this.getMotion().add(0.0D, -0.03D, 0.0D));
      }

      if (this.world.getFluidState(this.getPosition()).isTagged(FluidTags.LAVA)) {
         this.setMotion((double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F), (double)0.2F, (double)((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
         this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
      }

      double d0 = 8.0D;
      if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
         this.xpTargetColor = this.xpColor;
      }

      this.move(MoverType.SELF, this.getMotion());
      float f = 0.98F;
      if (this.onGround) {
         BlockPos pos =new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ());
         f = this.world.getBlockState(pos).getSlipperiness(this.world, pos, this) * 0.98F;
      }

      this.setMotion(this.getMotion().mul((double)f, 0.98D, (double)f));
      if (this.onGround) {
         this.setMotion(this.getMotion().mul(1.0D, -0.9D, 1.0D));
      }

      ++this.xpColor;
      ++this.xpOrbAge;

      if (this.xpOrbAge >= 6000) {
         this.remove();
      }

      if (this.xpValue <= 0)
      {
         this.xpValue = originalXpValue;
      }
      --this.xpValue;
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
      compound.putShort("Value", (short)this.xpValue);
   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   public void readAdditional(CompoundNBT compound) {
      this.xpOrbHealth = compound.getShort("Health");
      this.xpOrbAge = compound.getShort("Age");
      this.xpValue = compound.getShort("Value");
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
    * Returns the XP value of this XP orb.
    */
   public int getXpValue() {
      return this.xpValue;
   }

   /**
    * Returns a number from 1 to 10 based on how much XP this orb is worth. This is used by RenderXPOrb to determine
    * what texture to use.
    */
   @OnlyIn(Dist.CLIENT)
   public int getTextureByXP() {
      return xpValue;
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
