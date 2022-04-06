package me.miquiis.onlyblock.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.gui.ATMScreen;
import me.miquiis.onlyblock.client.gui.LeaderboardInventoryScreen;
import me.miquiis.onlyblock.client.gui.ShopScreen;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyMoneyBlock;
import me.miquiis.onlyblock.common.classes.OldEasyGUI;
import me.miquiis.onlyblock.common.entities.*;
import me.miquiis.onlyblock.common.registries.BlockRegister;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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
    public static void onDayChange(TickEvent.WorldTickEvent event)
    {
        if (event.phase == TickEvent.Phase.START && event.world.getDimensionKey().getLocation().toString().contains("overworld"))
        {
            if (event.world.getDayTime() % 24000 == 0)
            {
                event.world.getServer().getPlayerList().getPlayers().forEach(player -> {
                    OnlyMoneyBlock.getCapability(player).sumDays(-1);
                    OnlyMoneyBlock.getCapability(player).setFrozen(-1, false);
                });
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event)
    {
        IWorldOnlyMoneyBlock worldOnlyMoneyBlock = WorldOnlyMoneyBlock.getCapability(event.getWorld());
        if (event.getTarget() instanceof NoobEntity && event.getHand() == Hand.MAIN_HAND && event.getWorld().isRemote)
        {
            if (worldOnlyMoneyBlock.getMcDonaldsBusiness().getBusinessOwner() != null)
            {
                Minecraft.getInstance().displayGuiScreen(new ShopScreen("McDonald's", new ArrayList<>(Arrays.asList(
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.BIG_MAC.get()), 0, worldOnlyMoneyBlock.getMcDonaldsBusiness().getBusinessOwner().equals(event.getPlayer().getUniqueID()) ? 0 : 500)
                ))));
            } else
            {
                Minecraft.getInstance().displayGuiScreen(new ShopScreen("McDonald's", new ArrayList<>(Arrays.asList(
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.MCDONALDS.get()), 0, 20000)
                ))));
            }
        }

        if (event.getTarget() instanceof ElonMuskEntity && event.getHand() == Hand.MAIN_HAND && event.getWorld().isRemote)
        {
            if (worldOnlyMoneyBlock.getTeslaBusiness().getBusinessOwner() != null)
            {
                if (worldOnlyMoneyBlock.getTeslaBusiness().getBusinessOwner().equals(event.getPlayer().getUniqueID()))
                {
                    Minecraft.getInstance().displayGuiScreen(new ShopScreen("Tesla", new ArrayList<>(Arrays.asList(
                            new ShopScreen.ItemSlot(new ItemStack(ItemRegister.FLYING_TESLA.get()), 0, 0)
                    ))));
                }
            } else
            {
                Minecraft.getInstance().displayGuiScreen(new ShopScreen("Tesla", new ArrayList<>(Arrays.asList(
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.TESLA.get()), 0, 20000)
                ))));
            }
        }

        if (event.getTarget() instanceof JeffBezosEntity && event.getHand() == Hand.MAIN_HAND && event.getWorld().isRemote)
        {
            if (worldOnlyMoneyBlock.getAmazonBusiness().getBusinessOwner() != null)
            {
                if (worldOnlyMoneyBlock.getAmazonBusiness().getBusinessOwner().equals(event.getPlayer().getUniqueID()))
                {
                    Minecraft.getInstance().displayGuiScreen(new ShopScreen("Amazon", new ArrayList<>(Arrays.asList(
                            new ShopScreen.ItemSlot(new ItemStack(ItemRegister.TNT_BAZOOKA.get()), 0, 0)
                    ))));
                }
            } else
            {
                Minecraft.getInstance().displayGuiScreen(new ShopScreen("Amazon", new ArrayList<>(Arrays.asList(
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.AMAZON.get()), 0, 20000)
                ))));
            }
        }

        if (event.getTarget() instanceof BobEntity && event.getHand() == Hand.MAIN_HAND && event.getWorld().isRemote)
        {
            Minecraft.getInstance().displayGuiScreen(new ShopScreen("Bob The Builder", new ArrayList<>(Arrays.asList(
                    new ShopScreen.ItemSlot(new ItemStack(Items.DIRT, 64), 0, 5000),
                    new ShopScreen.ItemSlot(new ItemStack(Items.COBBLESTONE, 10), 1, 1000),
                    new ShopScreen.ItemSlot(new ItemStack(Items.OAK_PLANKS, 12), 2, 2000),
                    new ShopScreen.ItemSlot(new ItemStack(ItemRegister.BRIDGER.get()), 3, 10000),
                    new ShopScreen.ItemSlot(new ItemStack(BlockRegister.SPEED_BOOST.get(), 4), 2, 1000)
            ))));
        }

        if (event.getTarget() instanceof DealerEntity && event.getHand() == Hand.MAIN_HAND && event.getWorld().isRemote)
        {
            Minecraft.getInstance().displayGuiScreen(new ShopScreen("Dealer", new ArrayList<>(Arrays.asList(
                    new ShopScreen.ItemSlot(new ItemStack(ItemRegister.DEBIT_CARD_SWORD.get()), 1, 5000),
                    new ShopScreen.ItemSlot(new ItemStack(ItemRegister.CRYPTO_MINER.get()), 0, 1000)
            ))));
        }

        if (event.getTarget() instanceof AlfredEntity && event.getHand() == Hand.MAIN_HAND && event.getWorld().isRemote)
        {
            Minecraft.getInstance().displayGuiScreen(new ShopScreen("Alfred", new ArrayList<>(Arrays.asList(
                    new ShopScreen.ItemSlot(new ItemStack(BlockRegister.MONEY_PRINTER.get()), 0, 5000),
                    new ShopScreen.ItemSlot(new ItemStack(ItemRegister.MONEY_HELMET.get()), 1, 3000),
                    new ShopScreen.ItemSlot(new ItemStack(ItemRegister.MONEY_CHESTPLATE.get()), 2, 4000),
                    new ShopScreen.ItemSlot(new ItemStack(ItemRegister.MONEY_LEGGINGS.get()), 3, 3500),
                    new ShopScreen.ItemSlot(new ItemStack(ItemRegister.MONEY_BOOTS.get()), 4, 2000)
            ))));
        }

        if (event.getTarget() instanceof HackerEntity && event.getHand() == Hand.MAIN_HAND && event.getWorld().isRemote)
        {
            if (worldOnlyMoneyBlock.hasReachedHalfGoal())
            {
                Minecraft.getInstance().displayGuiScreen(new ShopScreen("Hacker", new ArrayList<>(Arrays.asList(
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.SABOTAGE.get()), 0, 50000),
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.BALACLAVA.get()), 1, 100000),
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.MILLIONAIRE_NUKE.get()), 3, 100000),
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.FROZEN_CLOCK.get()), 2, 200000)
                ))));
            } else
            {
                Minecraft.getInstance().displayGuiScreen(new ShopScreen("Hacker", new ArrayList<>(Arrays.asList(
                        new ShopScreen.ItemSlot(new ItemStack(ItemRegister.SABOTAGE.get()), 0, 50000),
                        new ShopScreen.ItemSlot(createOutOfOrderItem(), 1, 999999999),
                        new ShopScreen.ItemSlot(createOutOfOrderItem(), 2, 999999999),
                        new ShopScreen.ItemSlot(createOutOfOrderItem(), 3, 999999999)
                ))));
            }
        }
    }

    private static ItemStack createOutOfOrderItem()
    {
        ItemStack itemStack = new ItemStack(Items.BARRIER);
        return itemStack.setDisplayName(new StringTextComponent("\u00A7c\u00A7l** OUT OF ORDER **"));
    }

    @SubscribeEvent
    public static void onGUIRender(RenderGameOverlayEvent event)
    {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            event.setCanceled(true);

            final Minecraft minecraft = Minecraft.getInstance();

            IOnlyMoneyBlock currency = OnlyMoneyBlock.getCapability(minecraft.player);
            if (currency == null) return;

            OldEasyGUI.StringGUIElement stringGUIElement = new OldEasyGUI.StringGUIElement(
                    new OldEasyGUI.Anchor(OldEasyGUI.VAnchor.BOTTOM, OldEasyGUI.HAnchor.CENTER),
                    event.getMatrixStack(), event.getWindow(), Minecraft.getInstance().fontRenderer,
                    "$" + currency.getCash(),
                    true,
                    0f, 22f, 0.80f,
                    true,
                    new Color(46, 217, 91).getRGB());
            stringGUIElement.render(null);
        }
    }

    @SubscribeEvent
    public static void onPlayerRender(RenderLivingEvent.Pre<?,?> event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity)event.getEntity();
            if (player.getRidingEntity() != null && player.getRidingEntity() instanceof FlyingTeslaEntity)
            {
                event.setCanceled(true);
                return;
            }

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
