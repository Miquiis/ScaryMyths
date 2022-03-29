package me.miquiis.onlyblock.common.capability;

import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = OnlyBlock.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityEvents {
    @SubscribeEvent
    public static void attachCapabilitiesWorld(final AttachCapabilitiesEvent<World> event)
    {
        event.addCapability(new ResourceLocation(OnlyBlock.MOD_ID, "world_onlyblock"), new WorldOnlyBlockCapability(event.getObject()));
    }


}
