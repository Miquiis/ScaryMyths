package me.miquiis.onlyblock.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = OnlyBlock.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onLastWorldRender(RenderWorldLastEvent event)
    {
        IWorldOnlyBlock worldOnlyBlock = WorldOnlyBlock.getCapability(Minecraft.getInstance().world);
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();

        renderGenerator(event.getMatrixStack(), new StringTextComponent("\u00A7fIron Generator"), new StringTextComponent(convertSeconds(worldOnlyBlock.getNextIronDropTime() / 20)), new ItemStack(Items.IRON_BLOCK), worldOnlyBlock.getIronGenerator());
        renderGenerator(event.getMatrixStack(), new StringTextComponent("\u00A76Gold Generator"), new StringTextComponent(convertSeconds(worldOnlyBlock.getNextGoldDropTime() / 20)), new ItemStack(Items.GOLD_BLOCK), worldOnlyBlock.getGoldGenerator());
        renderGenerator(event.getMatrixStack(), new StringTextComponent("\u00A7bDiamond Generator"), new StringTextComponent(convertSeconds(worldOnlyBlock.getNextDiamondDropTime() / 20)), new ItemStack(Items.DIAMOND_BLOCK), worldOnlyBlock.getDiamondGenerator());
        renderGenerator(event.getMatrixStack(), new StringTextComponent("\u00A72Emerald Generator"), new StringTextComponent(convertSeconds(worldOnlyBlock.getNextEmeraldDropTime() / 20)), new ItemStack(Items.EMERALD_BLOCK), worldOnlyBlock.getEmeraldGenerator());

        buffer.finish();
    }

    private static void renderGenerator(MatrixStack matrixStack, StringTextComponent text, StringTextComponent timeLeft, ItemStack item, Vector3d position)
    {
        renderTextInWorld(matrixStack, text, position.getX() + 0.5, position.getY() + 1.8, position.getZ() + 0.5, 0, 0.05f, Color.WHITE.getRGB(), true);
        renderTextInWorld(matrixStack, new StringTextComponent("\u00A7aGenerating in: \u00a7e" + timeLeft.getText()), position.getX() + 0.5, position.getY() + 1, position.getZ() + 0.5, 0, 0.03f, Color.WHITE.getRGB(), true);
        renderItemInWorld(matrixStack, item, position.getX() + 0.5, position.getY() + 2, position.getZ() + 0.5, 0, 3f);
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
        //matrixStack.translate(0, -scale / 2, 0);
        //matrixStack.translate(0, -0.5, 0);

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
