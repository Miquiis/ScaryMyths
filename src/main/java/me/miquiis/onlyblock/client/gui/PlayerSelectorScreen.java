package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.classes.JHTML;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.IPlayerSelectablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerSelectorScreen extends Screen {

    public static class PlayerSelectorInfo {
        private UUID playerUUID;
        private String playerName;
        private ResourceLocation playerSkin;

        public PlayerSelectorInfo(UUID playerUUID, String playerName, ResourceLocation playerSkin)
        {
            this.playerName = playerName;
            this.playerUUID = playerUUID;
            this.playerSkin = playerSkin;
        }

        public CompoundNBT serializeNBT()
        {
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.putUniqueId("PlayerID", playerUUID);
            compoundNBT.putString("PlayerName", playerName);
            compoundNBT.putString("PlayerSkin", playerSkin.toString());
            return compoundNBT;
        }

        public String getPlayerName() {
            return playerName;
        }

        public ResourceLocation getPlayerSkin() {
            return playerSkin;
        }

        public UUID getPlayerUUID() {
            return playerUUID;
        }

        public static PlayerSelectorInfo deserializeNBT(CompoundNBT compoundNBT)
        {
            return new PlayerSelectorInfo(compoundNBT.getUniqueId("PlayerID"), compoundNBT.getString("PlayerName"), new ResourceLocation(compoundNBT.getString("PlayerSkin")));
        }
    }

    private static final ResourceLocation BACKGROUND = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/player_selector/background.png");
    private static final ResourceLocation PLAYER_BACKGROUND = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/player_selector/player_background.png");
    private static final ResourceLocation PLAYER_HEAD_FRAME = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/player_selector/player_head_frame.png");

    private List<PlayerSelectorInfo> listOfPlayers;
    private IPlayerSelectablePacket packetToSend;

    public PlayerSelectorScreen(List<PlayerSelectorInfo> players, IPlayerSelectablePacket packetToSend) {
        super(new StringTextComponent("Player Selector"));
        this.listOfPlayers = players;
        this.packetToSend = packetToSend;
        init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        JHTML.Canvas(1920, 1080, true,
                JHTML.Image(770, 515, BACKGROUND,
                        createAllPlayers()
                ).setCenteredHorizontally().setCenteredVertically()
        ).render(matrixStack, Minecraft.getInstance(), 0, 0);
    }

    private JHTML.Canvas createAllPlayers()
    {
        List<JHTML.Canvas> players = new ArrayList<>();

        listOfPlayers.forEach(info -> {
            players.add(createPlayerSelection(info));
        });

        return JHTML.Canvas(770, 515, players.stream().toArray(JHTML.Canvas[]::new));
    }

    private JHTML.Canvas createPlayerSelection(PlayerSelectorInfo info)
    {
        return JHTML.Image(722, 97, 24, 31, 0, 0, false, PLAYER_BACKGROUND,
                JHTML.Image(68, 68, 30, 0, 0, 0, PLAYER_HEAD_FRAME,
                        JHTML.UVImage(64, 64, info.getPlayerSkin(), 0, 0).setCenteredHorizontally().setCenteredVertically()
                ).setCenteredVertically(),
                JHTML.Text(0, 30, 5, 0, 0, info.getPlayerName(), 6f, true, Color.WHITE.getRGB()).setCenteredVertically()
        ).setOnRenderEvent((x, y, width1, height1) -> {
            this.addButton(new Button((int)x, (int)y, (int)width1, (int)height1, new StringTextComponent("player_select_button"), p_onPress_1_ -> {
                packetToSend.setPlayerInfo(info);
                OnlyBlockNetwork.CHANNEL.sendToServer(packetToSend);
            }));
        });
    }
}
