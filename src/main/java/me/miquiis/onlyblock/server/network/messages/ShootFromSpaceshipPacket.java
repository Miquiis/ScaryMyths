package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.entities.GoldenProjectileEntity;
import me.miquiis.onlyblock.common.entities.SpaceshipEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ShootFromSpaceshipPacket {

    public static void encode(ShootFromSpaceshipPacket msg, PacketBuffer buf) {
    }

    public static ShootFromSpaceshipPacket decode(PacketBuffer buf) {
        return new ShootFromSpaceshipPacket();
    }

    public static void handle(final ShootFromSpaceshipPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            Entity ridingEntity = player.getRidingEntity();
            if (ridingEntity != null && ridingEntity instanceof SpaceshipEntity)
            {
                SpaceshipEntity spaceship = (SpaceshipEntity) ridingEntity;
                Vector3d position = player.getLookVec().add(player.getPositionVec());
                Vector3d direc = player.getLookVec().normalize().mul(5, 5,5);
                GoldenProjectileEntity goldenProjectileEntity = new GoldenProjectileEntity(spaceship.world);
                goldenProjectileEntity.setShooter(player);
                goldenProjectileEntity.setPosition(position.getX(), spaceship.getPosY() - 1, position.getZ());
                goldenProjectileEntity.setVelocity(direc.getX(), direc.getY(), direc.getZ());
                player.world.addEntity(goldenProjectileEntity);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
