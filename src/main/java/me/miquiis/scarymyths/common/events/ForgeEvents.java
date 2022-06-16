package me.miquiis.scarymyths.common.events;

import me.miquiis.scarymyths.ScaryMyths;
import me.miquiis.scarymyths.server.commands.ModCommand;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = ScaryMyths.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event)
    {
        new ModCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onSignPlace(BlockEvent.EntityPlaceEvent event)
    {
        if (event.getPlacedBlock().getBlock() == Blocks.WARPED_SIGN.getBlock() && event.getWorld().getTileEntity(event.getPos()) instanceof SignTileEntity)
        {
            if (!event.getWorld().isRemote())
            {
                ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) event.getEntity();
                serverPlayerEntity.closeScreen();
            }
            SignTileEntity signTileEntity = (SignTileEntity) event.getWorld().getTileEntity(event.getPos());
            signTileEntity.setText(1, new StringTextComponent("\u00A7cL I S T E N"));
            signTileEntity.setText(2, new StringTextComponent("\u00A7cA G A I N"));
            signTileEntity.setEditable(false);
            signTileEntity.markDirty();
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {

    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {

        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (!event.getPlayer().world.isRemote)
        {

        }
    }

    @SubscribeEvent
    public static void changeDimesionEvent(final PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!event.getPlayer().world.isRemote)
        {

        }
    }

}
