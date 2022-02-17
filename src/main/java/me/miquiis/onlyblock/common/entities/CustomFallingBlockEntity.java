package me.miquiis.onlyblock.common.entities;

import com.google.common.collect.Lists;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.EntityRegister;
import net.minecraft.block.*;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.item.DirectionalPlaceContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;

public class CustomFallingBlockEntity extends Entity {
   private BlockState fallTile = Blocks.SAND.getDefaultState();
   public int fallTime;
   public boolean shouldDropItem = true;
   private boolean dontSetBlock;
   private boolean hurtEntities;
   private int fallHurtMax = 40;
   private float fallHurtAmount = 2.0F;
   public CompoundNBT tileEntityData;
   protected static final DataParameter<BlockPos> ORIGIN = EntityDataManager.createKey(CustomFallingBlockEntity.class, DataSerializers.BLOCK_POS);

   public CustomFallingBlockEntity(EntityType<? extends CustomFallingBlockEntity> p_i50218_1_, World world) {
      super(p_i50218_1_, world);
   }

   public CustomFallingBlockEntity(World worldIn, double x, double y, double z, BlockState fallingBlockState) {
      this(EntityRegister.CUSTOM_FALLING_BLOCK.get(), worldIn);
      this.fallTile = fallingBlockState;
      this.preventEntitySpawning = true;
      this.setPosition(x, y + (double)((1.0F - this.getHeight()) / 2.0F), z);
      this.setMotion(Vector3d.ZERO);
      this.prevPosX = x;
      this.prevPosY = y;
      this.prevPosZ = z;
      this.setOrigin(this.getPosition());
   }

   /**
    * Returns true if it's possible to attack this entity with an item.
    */
   public boolean canBeAttackedWithItem() {
      return false;
   }

   public void setOrigin(BlockPos origin) {
      this.dataManager.set(ORIGIN, origin);
   }

   public BlockPos getOrigin() {
      return this.dataManager.get(ORIGIN);
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void registerData() {
      this.dataManager.register(ORIGIN, BlockPos.ZERO);
   }

   /**
    * Returns true if other Entities should be prevented from moving through this Entity.
    */
   public boolean canBeCollidedWith() {
      return !this.removed;
   }

   /**
    * Called to update the entity's position/logic.
    */
   public void tick() {
      if (this.fallTile.isAir()) {
         this.remove();
      } else {
         Block block = this.fallTile.getBlock();
         if (this.fallTime++ == 0) {
            if (!this.world.isRemote) {
               this.remove();
               return;
            }
         }

         if (!this.hasNoGravity()) {
            this.setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
         }

         this.move(MoverType.SELF, this.getMotion());
         if (!this.world.isRemote) {
            BlockPos blockpos1 = this.getPosition();
            boolean flag = this.fallTile.getBlock() instanceof ConcretePowderBlock;
            boolean flag1 = flag && this.world.getFluidState(blockpos1).isTagged(FluidTags.WATER);
            double d0 = this.getMotion().lengthSquared();
            if (flag && d0 > 1.0D) {
               BlockRayTraceResult blockraytraceresult = this.world.rayTraceBlocks(new RayTraceContext(new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ), this.getPositionVec(), RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.SOURCE_ONLY, this));
               if (blockraytraceresult.getType() != RayTraceResult.Type.MISS && this.world.getFluidState(blockraytraceresult.getPos()).isTagged(FluidTags.WATER)) {
                  blockpos1 = blockraytraceresult.getPos();
                  flag1 = true;
               }
            }

            if (!this.onGround && !flag1) {
               if (!this.world.isRemote && (this.fallTime > 100 && (blockpos1.getY() < 1 || blockpos1.getY() > 256) || this.fallTime > 600)) {
                  if (this.shouldDropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                     this.entityDropItem(block);
                  }

                  this.remove();
               }
            } else {
               BlockState blockstate = this.world.getBlockState(blockpos1);
               this.setMotion(this.getMotion().mul(0.7D, -0.5D, 0.7D));
               if (!blockstate.matchesBlock(Blocks.MOVING_PISTON)) {
                  this.remove();
               }
            }
         }

         this.setMotion(this.getMotion().scale(0.98D));
      }
   }

   public boolean onLivingFall(float distance, float damageMultiplier) {
      if (this.hurtEntities) {
         int i = MathHelper.ceil(distance - 1.0F);
         if (i > 0) {
            List<Entity> list = Lists.newArrayList(this.world.getEntitiesWithinAABBExcludingEntity(this, this.getBoundingBox()));
            boolean flag = this.fallTile.isIn(BlockTags.ANVIL);
            DamageSource damagesource = flag ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;

            for(Entity entity : list) {
               entity.attackEntityFrom(damagesource, (float)Math.min(MathHelper.floor((float)i * this.fallHurtAmount), this.fallHurtMax));
            }

            if (flag && (double)this.rand.nextFloat() < (double)0.05F + (double)i * 0.05D) {
               BlockState blockstate = AnvilBlock.damage(this.fallTile);
               if (blockstate == null) {
                  this.dontSetBlock = true;
               } else {
                  this.fallTile = blockstate;
               }
            }
         }
      }

      return false;
   }

   protected void writeAdditional(CompoundNBT compound) {
      compound.put("BlockState", NBTUtil.writeBlockState(this.fallTile));
      compound.putInt("Time", this.fallTime);
      compound.putBoolean("DropItem", this.shouldDropItem);
      compound.putBoolean("HurtEntities", this.hurtEntities);
      compound.putFloat("FallHurtAmount", this.fallHurtAmount);
      compound.putInt("FallHurtMax", this.fallHurtMax);
      if (this.tileEntityData != null) {
         compound.put("TileEntityData", this.tileEntityData);
      }

   }

   /**
    * (abstract) Protected helper method to read subclass entity data from NBT.
    */
   protected void readAdditional(CompoundNBT compound) {
      this.fallTile = NBTUtil.readBlockState(compound.getCompound("BlockState"));
      this.fallTime = compound.getInt("Time");
      if (compound.contains("HurtEntities", 99)) {
         this.hurtEntities = compound.getBoolean("HurtEntities");
         this.fallHurtAmount = compound.getFloat("FallHurtAmount");
         this.fallHurtMax = compound.getInt("FallHurtMax");
      } else if (this.fallTile.isIn(BlockTags.ANVIL)) {
         this.hurtEntities = true;
      }

      if (compound.contains("DropItem", 99)) {
         this.shouldDropItem = compound.getBoolean("DropItem");
      }

      if (compound.contains("TileEntityData", 10)) {
         this.tileEntityData = compound.getCompound("TileEntityData");
      }

      if (this.fallTile.isAir()) {
         this.fallTile = Blocks.SAND.getDefaultState();
      }

   }

   public World getWorldObj() {
      return this.world;
   }

   public void setHurtEntities(boolean hurtEntitiesIn) {
      this.hurtEntities = hurtEntitiesIn;
   }

   /**
    * Return whether this entity should be rendered as on fire.
    */
   public boolean canRenderOnFire() {
      return false;
   }

   public void fillCrashReport(CrashReportCategory category) {
      super.fillCrashReport(category);
      category.addDetail("Immitating BlockState", this.fallTile.toString());
   }

   public BlockState getBlockState() {
      return this.fallTile;
   }


   public boolean ignoreItemEntityData() {
      return true;
   }

   public IPacket<?> createSpawnPacket() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}