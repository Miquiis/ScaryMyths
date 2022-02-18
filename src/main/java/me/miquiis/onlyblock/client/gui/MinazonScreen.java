package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.common.classes.EasyGUI;
import me.miquiis.onlyblock.common.classes.JHTML;
import me.miquiis.onlyblock.common.classes.OldEasyGUI;
import me.miquiis.onlyblock.common.containers.MinazonContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinazonScreen extends ContainerScreen<MinazonContainer> {
    public MinazonScreen(MinazonContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //this.renderBackground(matrixStack);
        //super.render(matrixStack, mouseX, mouseY, partialTicks);
        //this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

        JHTML.Canvas(1920, 1080, true,
                JHTML.Box(1920,1080,true, new Color(0,0,0,153).getRGB(),
                        JHTML.Canvas(1920,220,true,
                                JHTML.Text(0, 0, 160, 0, 0, "$9999999", 5f, true, new Color(46, 217, 91).getRGB()).setCenteredHorizontally()
                        ),
                        JHTML.Box(1920,100,true, new Color(0,0,0,204).getRGB(),
                            JHTML.Text(0, 0, 5, 0, 0, "Minazon", 5f, false, Color.WHITE.getRGB()).setCenteredHorizontally().setCenteredVertically()
                        ),
                        JHTML.Canvas(980,70,false,
                                JHTML.Canvas(490, 65, true,
                                        JHTML.Text(0, -25, 15, 0, 0, "Buy Items", 4f, true, Color.WHITE.getRGB()).setFloatRight(),
                                        JHTML.Box(195, 5, -25, 2, 0, 0, Color.WHITE.getRGB()).setFloatRight()
                                ),
                                JHTML.Canvas(490, 65, true,
                                        JHTML.Text(0, 25, 15, 0, 0, "Sell Items", 4f, true, Color.WHITE.getRGB())
                                        //JHTML.Box(195, 5, 25, 2, 0, 0, Color.WHITE.getRGB())
                                )
                        ).setCenteredHorizontally(),
                        JHTML.Canvas(1920, 550, false,
                                JHTML.Canvas(470,550,true),
                                JHTML.Canvas(980,550,true,
                                        createItemRows(new ItemStack[]{
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND),
                                                new ItemStack(Items.DIAMOND)
                                        })
                                ),
                                JHTML.Canvas(470,550,true)
                        )
                )
        ).render(matrixStack, getMinecraft(), 0, 0);
    }

    private JHTML.Item createItem(ItemStack itemStack)
    {
        return JHTML.Item(90, 90, 0, 0,0, 0, itemStack, 5f);
    }

    private JHTML.Canvas[] createItemRows(ItemStack[] itemStacks)
    {
        List<JHTML.Canvas> rows = new ArrayList<>();
        List<ItemStack> items = new ArrayList<>();

        int count = 0;
        for (ItemStack itemStack : itemStacks)
        {
            items.add(itemStack);
            if (count == 10)
            {
                rows.add(createItemRow(items.toArray(new ItemStack[0])));
                items.clear();
                count = 0;
            } else
            {
                if (count + rows.size() * 10 == itemStacks.length - 1)
                {
                    rows.add(createItemRow(items.toArray(new ItemStack[0])));
                    items.clear();
                    count = 0;
                }
            }
            count++;
        }

        return rows.toArray(new JHTML.Canvas[0]);
    }

    private JHTML.Canvas createItemRow(ItemStack[] itemStacks)
    {
        List<JHTML.Item> row = new ArrayList<>();

        for (ItemStack itemStack : itemStacks)
        {
            row.add(createItem(itemStack));
        }

        return JHTML.Canvas(980, 90, false,
                row.toArray(new JHTML.Canvas[0])
        );
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        return;
    }
}
