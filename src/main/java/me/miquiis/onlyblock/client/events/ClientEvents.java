package me.miquiis.onlyblock.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.gui.LeaderboardInventoryScreen;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
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

    @SubscribeEvent
    public static void onInventoryOpen(GuiScreenEvent event)
    {
        if (event.getGui() instanceof InventoryScreen)
        {
            if (!(event.getGui() instanceof LeaderboardInventoryScreen))
            {
                Minecraft.getInstance().displayGuiScreen(new LeaderboardInventoryScreen(event.getGui().getMinecraft().player));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderLivingEvent<?,?> event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            float yOffset = Minecraft.getInstance().player.equals(event.getEntity()) ? 0f : 0.3f;
            IOnlyMoneyBlock moneyBlock = OnlyMoneyBlock.getCapability((PlayerEntity)event.getEntity());
            renderName((PlayerEntity)event.getEntity(), new StringTextComponent("\u00A7e\u00A7l" + moneyBlock.getDays() + " Days"), yOffset + yOffset, event.getMatrixStack(), event.getBuffers(), event.getLight());
            if (yOffset == 0.3f)
            renderName((PlayerEntity)event.getEntity(), new StringTextComponent("\u00A7a\u00A7l$" + moneyBlock.getCash()), yOffset, event.getMatrixStack(), event.getBuffers(), event.getLight());
        }
    }

    private static void renderName(PlayerEntity entityIn, ITextComponent displayNameIn, float yOffset, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
        double d0 = renderManager.squareDistanceTo(entityIn);
        if (net.minecraftforge.client.ForgeHooksClient.isNameplateInRenderDistance(entityIn, d0)) {
            boolean flag = !entityIn.isDiscrete();
            float f = entityIn.getHeight() + 0.5F + yOffset;
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.push();
            matrixStackIn.translate(0.0D, (double)f, 0.0D);
            matrixStackIn.rotate(renderManager.getCameraOrientation());
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

    private static String convertSeconds(int seconds) {
        int h = seconds/ 3600;
        int m = (seconds % 3600) / 60;
        int s = seconds % 60;
        String sh = (h > 0 ? String.valueOf(h) + " " + "hours" : "");
        String sm = (m < 10 && m > 0 && h > 0 ? "0" : "") + (m > 0 ? (h > 0 && s == 0 ? String.valueOf(m) : String.valueOf(m) + " " + "minutes") : "");
        String ss = (s == 0 && (h > 0 || m > 0) ? "" : (s < 10 && (h > 0 || m > 0) ? "0" : "") + String.valueOf(s) + " " + "seconds");
        return sh + (h > 0 ? " " : "") + sm + (m > 0 ? " " : "") + ss;
    }
}
