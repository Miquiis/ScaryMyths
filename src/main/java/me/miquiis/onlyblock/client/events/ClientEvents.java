package me.miquiis.onlyblock.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.classes.OldEasyGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
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
        IOnlyBlock onlyBlockCap = OnlyBlockModel.getCapability(Minecraft.getInstance().player);
        if (onlyBlockCap.getAmazonIsland().getCurrentDelivery() != null)
        {
            Vector3d currentDelivery = onlyBlockCap.getAmazonIsland().getCurrentDelivery();
            double distance = Minecraft.getInstance().player.getPositionVec().distanceTo(currentDelivery);
            float distanceScale = 0.05f + (float)distance / 300f;

            renderTextInWorld(event.getMatrixStack(), new StringTextComponent(Math.round(distance) + "m"), currentDelivery.getX() + 0.5, currentDelivery.getY(), currentDelivery.getZ() + 0.5, 0, distanceScale, Color.WHITE.getRGB());
            renderTextInWorld(event.getMatrixStack(), new StringTextComponent("Next Delivery"), currentDelivery.getX() + 0.5, currentDelivery.getY(), currentDelivery.getZ() + 0.5, 10.0, distanceScale, new Color(255,153,0).getRGB());
        }
    }

    private static void renderTextInWorld(MatrixStack matrixStack, StringTextComponent text, double x, double y, double z, double offsetY, float scale, int color)
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
        fontRenderer.func_243247_a(text, f2, (float) ((-fontRenderer.FONT_HEIGHT / 2) - offsetY), color, false, matrixStack.getLast().getMatrix(), buffer, true, 0, 15728880);

        matrixStack.pop();
        buffer.finish();
    }
}
