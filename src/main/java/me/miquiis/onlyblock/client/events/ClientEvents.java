package me.miquiis.onlyblock.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
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
    public static void onWorldLastRender(RenderWorldLastEvent renderWorldLastEvent)
    {
        renderName(renderWorldLastEvent);
    }

    protected static void renderName(RenderWorldLastEvent event) {
        Vector3d view = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();

        MatrixStack stack = event.getMatrixStack();
        stack.translate(-view.x, -view.y, -view.z); // translate

        RenderSystem.pushMatrix();
        RenderSystem.multMatrix(stack.getLast().getMatrix());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;

        RenderSystem.pushMatrix();
        RenderSystem.translated(100, 100, 100);

        RenderSystem.scaled(-2, -2, -2);
        fontRenderer.func_243247_a(new StringTextComponent("Test"), 0, 0, 0, true, event.getMatrixStack().getLast().getMatrix(), Minecraft.getInstance().getRenderTypeBuffers().getBufferSource(), true, 0, 0);

        RenderSystem.popMatrix();
        RenderSystem.popMatrix();



//        Vector3d view = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
//        matrixStackIn.push();
//        matrixStackIn.translate(-view.x, -view.y, -view.z);
//        matrixStackIn.translate(100, 100, 100);
//        matrixStackIn.rotate(Minecraft.getInstance().getRenderManager().getCameraOrientation());
//        matrixStackIn.scale(-0.5F, -0.5F, -0.5F);
//        Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();
//        float f1 = Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F);
//        int j = (int)(f1 * 255.0F) << 24;
//        FontRenderer fontrenderer = Minecraft.getInstance().fontRenderer;
//        float f2 = (float)(-fontrenderer.getStringPropertyWidth(displayNameIn) / 2);
//        fontrenderer.func_243247_a(displayNameIn, f2, (float)0, 553648127, false, matrix4f, bufferIn, false, j, packedLightIn);
//
////        if (flag) {
////            fontrenderer.func_243247_a(displayNameIn, f2, (float)i, -1, false, matrix4f, bufferIn, false, 0, packedLightIn);
////        }
//
//        matrixStackIn.pop();
    }

}
