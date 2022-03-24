package me.miquiis.onlyblock.server.network.messages;

import me.miquiis.onlyblock.common.entities.GoldenProjectileEntity;
import me.miquiis.onlyblock.common.entities.SpaceshipEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ShootMissilePacket {

    public static void encode(ShootMissilePacket msg, PacketBuffer buf) {
    }

    public static ShootMissilePacket decode(PacketBuffer buf) {
        return new ShootMissilePacket();
    }

    public static void handle(final ShootMissilePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            System.out.println("Handle");
            ServerPlayerEntity player = ctx.get().getSender();
            System.out.println(player.world.isRemote);
            Entity ridingEntity = player.getRidingEntity();

            System.out.println(ridingEntity);
            if (ridingEntity != null && ridingEntity instanceof SpaceshipEntity)
            {
                System.out.println("Shooting");
                SpaceshipEntity spaceship = (SpaceshipEntity) ridingEntity;
                Vector3d position = player.getLookVec().add(player.getPositionVec());
                Vector3d direc = player.getLookVec().mul(3, 3,3);

                GoldenProjectileEntity goldenProjectileEntity = new GoldenProjectileEntity(spaceship.world);
                goldenProjectileEntity.setShooter(player);
                goldenProjectileEntity.setPosition(position.getX(), spaceship.getPosY() - 1, position.getZ());
                goldenProjectileEntity.setVelocity(direc.getX(), direc.getY(), direc.getZ());

                spaceship.world.addEntity(goldenProjectileEntity);
            }

        });
        ctx.get().setPacketHandled(true);
    }

}
