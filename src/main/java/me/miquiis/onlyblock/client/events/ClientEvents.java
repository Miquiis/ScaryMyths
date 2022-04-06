package me.miquiis.onlyblock.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onLastWorldRender(RenderWorldLastEvent event)
    {
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
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
        matrixStack.scale(-scale, -scale, scale);

        float f2 = (float)(-fontRenderer.getStringPropertyWidth(text) / 2);
        float f1 = hasBackground ? Minecraft.getInstance().gameSettings.getTextBackgroundOpacity(0.25F) : 0;
        int j = (int)(f1 * 255.0F) << 24;

        fontRenderer.func_243247_a(text, f2, 0f, color, false, matrixStack.getLast().getMatrix(), buffer, false, j, 15728880);

        matrixStack.pop();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
    }

    private static void renderItemInWorld(MatrixStack matrixStack, ItemStack item, double x, double y, double z, double offsetY, float scale)
    {
        Minecraft mc = Minecraft.getInstance();
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        Vector3d view = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();

        matrixStack.push();
        matrixStack.translate(-view.x, -view.y, -view.z);
        matrixStack.translate(x, y, z);
        matrixStack.rotate(Vector3f.YP.rotationDegrees(Minecraft.getInstance().world.getGameTime() * 10));
        matrixStack.scale(scale, scale, scale);

        Minecraft.getInstance().getItemRenderer().renderItem(item, ItemCameraTransforms.TransformType.GROUND, 15728880, OverlayTexture.NO_OVERLAY, matrixStack, buffer);

        matrixStack.pop();
    }
}
