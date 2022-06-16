package me.miquiis.scarymyths.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.AddServerScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.ServerSelectionList;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin {

    @Shadow protected ServerSelectionList serverListSelector;

    @Inject(method = "init",  at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/gui/screen/ServerSelectionList;updateOnlineServers(Lnet/minecraft/client/multiplayer/ServerList;)V"))
    private void inject(CallbackInfo ci)
    {
        ServerList serverList = new ServerList(Minecraft.getInstance());
        ((ServerListAccessor)serverList).getServers().clear();
        for (int i = 0; i < 15; i++)
        {
            serverList.addServerData(new ServerData("I SEE YOU", "I.SEE.YOU", false));
        }
        serverListSelector.updateOnlineServers(serverList);
    }

}
