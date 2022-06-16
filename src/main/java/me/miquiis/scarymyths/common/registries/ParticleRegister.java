package me.miquiis.scarymyths.common.registries;

import me.miquiis.scarymyths.ScaryMyths;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = ScaryMyths.MOD_ID, value = Dist.CLIENT)
public class ParticleRegister {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ScaryMyths.MOD_ID);
}
