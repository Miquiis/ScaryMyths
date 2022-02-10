package me.miquiis.onlyblock.common.particles;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ExpExplosionParticle extends SpriteTexturedParticle {
   private final IAnimatedSprite spriteWithAge;

   private ExpExplosionParticle(ClientWorld world, double x, double y, double z, double scale, IAnimatedSprite spriteWithAge) {
      super(world, x, y, z, 0.0D, 0.0D, 0.0D);
      this.maxAge = 6 + this.rand.nextInt(4);
      float f = this.rand.nextFloat() * 0.6F + 0.4F;
      this.particleRed = 255 - 55;
      this.particleGreen = 255 - 230;
      this.particleBlue = 255 - 78;
      this.particleScale = 1.0F * (1.5F - (float)scale * 0.5F);
      this.spriteWithAge = spriteWithAge;
      this.selectSpriteWithAge(spriteWithAge);
   }

   public int getBrightnessForRender(float partialTick) {
      return 15728880;
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.particleRed -= 20;

      if (this.age++ >= this.maxAge) {
         this.setExpired();
      } else {
         this.selectSpriteWithAge(this.spriteWithAge);
      }
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_LIT;
   }

   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite spriteSet) {
         this.spriteSet = spriteSet;
      }

      public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new ExpExplosionParticle(worldIn, x, y, z, xSpeed, this.spriteSet);
      }
   }
}