package me.miquiis.onlyblock.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.classes.OldEasyGUI;
import me.miquiis.onlyblock.common.entities.GoldenHelicopterEntity;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.OpenAmazonPackage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    private static float lerp(float a, float b, float amt) {
        amt = clamp(amt, 1F, 0F);
        return a * amt + b * (1F - amt);
    }

    public static float clamp(float val, float max, float min) {
        return val > max ? max : val < min ? min : val;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onClickEvent(InputEvent.KeyInputEvent event)
    {
        if (event.getAction() != 0) return;
        if (event.getKey() != 85) return;

        if (Minecraft.getInstance().currentScreen instanceof ContainerScreen)
        {
            ContainerScreen<?> containerScreen = (ContainerScreen<?>) Minecraft.getInstance().currentScreen;
            if (containerScreen.getSlotUnderMouse() != null)
            {
                OnlyBlockNetwork.CHANNEL.sendToServer(new OpenAmazonPackage(containerScreen.getSlotUnderMouse().getStack()));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderLivingEvent<PlayerEntity, PlayerModel<PlayerEntity>> event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            renderName((PlayerEntity)event.getEntity(), new StringTextComponent("\u00A7a\u00A7lDays: \u00A7c\u00A7l100"), event.getMatrixStack(), event.getBuffers(), event.getLight());
        }
    }

    private static void renderName(PlayerEntity entityIn, ITextComponent displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        double d0 = Minecraft.getInstance().getRenderManager().squareDistanceTo(entityIn);
        if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(entityIn, d0)) {
            boolean flag = !entityIn.isDiscrete();
            float f = entityIn.getHeight() + 0.5F;
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, (double)f, 0.0D);
            matrixStackIn.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
            matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
            float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
            int j = (int)(f1 * 255.0F) << 24;
            FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
            float f2 = (float)(-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);
            fontrenderer.func_243247_a(displayNameIn, f2, (float)i, 553648127, false, matrix4f, bufferIn, flag, j, packedLightIn);
            if (flag) {
                fontrenderer.func_243247_a(displayNameIn, f2, (float)i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
            }

            matrixStackIn.pop();
        }
    }

    @SubscribeEvent
    public static void onGUIRender(RenderGameOverlayEvent event)
    {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            event.setCanceled(true);

            final Minecraft minecraft = Minecraft.getInstance();

            ICurrency currency = minecraft.player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
            int amount;

            if (currency != null)
                amount = (int) lerp(currency.getAmount(), currency.getLastAmount(), currency.getLerp());
            else
                amount = 0;

//            String amount = NumberFormat.getCurrencyInstance(new Locale("en", "US")).format(100000000);
//            amount = amount.substring(0, amount.length() - 3);

            OldEasyGUI.StringGUIElement stringGUIElement = new OldEasyGUI.StringGUIElement(
                    new OldEasyGUI.Anchor(OldEasyGUI.VAnchor.BOTTOM, OldEasyGUI.HAnchor.CENTER),
                    event.getMatrixStack(), event.getWindow(), Minecraft.getInstance().fontRenderer,
                    "$" + amount,
                    true,
                    0f, 22f, 0.80f,
                    true,
                    new Color(46, 217, 91).getRGB());
            stringGUIElement.render(null);
        }
    }

    @SubscribeEvent
    public static void onLastWorldRender(RenderWorldLastEvent event)
    {
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        renderTextInWorld(event.getMatrixStack(), new StringTextComponent("Player One"), 171.5, 88, -97.5, 20, 0.03f, Color.WHITE.getRGB(), true);
        renderTextInWorld(event.getMatrixStack(), new StringTextComponent("Money: $99999"), 171.5, 88, -97.5, 10, 0.03f, Color.green.getRGB(), true);
        renderTextInWorld(event.getMatrixStack(), new StringTextComponent("Days Left: 100"), 171.5, 88, -97.5, 0, 0.03f, Color.RED.getRGB(), true);
        buffer.finish();
    }

    private static void renderTextInWorld(MatrixStack matrixStack, StringTextComponent text, double x, double y, double z, double offsetY, float scale, int color, boolean hasBackground)
    {
        Minecraft mc = Minecraft.getInstance();
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        Vector3d view = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
        FontRenderer fontRenderer = mc.fontRenderer;

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        matrixStack.push();
        matrixStack.translate(-view.x, -view.y, -view.z);
        matrixStack.translate(x, y, z);
        matrixStack.rotate(mc.getRenderManager().getCameraOrientation());
        matrixStack.scale(-scale, -scale, 0.025f);

        float f2 = (float)(-fontRenderer.getStringPropertyWidth(text) / 2);
        float f1 = hasBackground ? Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F) : 0;
        int j = (int)(f1 * 255.0F) << 24;

        fontRenderer.func_243247_a(text, f2, (float) ((-fontRenderer.FONT_HEIGHT / 2) - offsetY), color, false, matrixStack.getLast().getMatrix(), buffer, false, j, 15728880);

        matrixStack.pop();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
    }
}
