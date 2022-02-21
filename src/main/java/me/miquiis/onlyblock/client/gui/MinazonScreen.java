package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.classes.EasyGUI;
import me.miquiis.onlyblock.common.classes.JHTML;
import me.miquiis.onlyblock.common.classes.OldEasyGUI;
import me.miquiis.onlyblock.common.containers.MinazonContainer;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.BuyItemFromShopPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinazonScreen extends Screen {

    private Button buyItems;
    private Button sellItems;
    private boolean isBuyActive;

    public MinazonScreen() {
        super(new StringTextComponent("Minazon"));
        this.isBuyActive = true;
        init();
    }

    @Override
    protected void init() {
        super.init();
        this.buyItems = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("buy"), p_onPress_1_ -> {
            isBuyActive = true;
        }));
        this.sellItems = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("buy"), p_onPress_1_ -> {
            isBuyActive = false;
            OnlyBlockNetwork.CHANNEL.sendToServer(new BuyItemFromShopPacket(new CompoundNBT()));
        }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //this.renderBackground(matrixStack);
        //super.render(matrixStack, mouseX, mouseY, partialTicks);
        //this.renderHoveredTooltip(matrixStack, mouseX, mouseY);

        JHTML.Canvas(1920, 1080, true,
                JHTML.Box(1920,1080,true, new Color(0,0,0,190).getRGB(),
                        JHTML.Canvas(1920,220,true,
                                JHTML.Text(0, 0, 160, 0, 0, "$" + (getCurrency() != null ? getCurrency().getAmount() : "0"), 5f, true, new Color(46, 217, 91).getRGB()).setCenteredHorizontally()
                        ),
                        JHTML.Box(1920,100,true, new Color(0,0,0,150).getRGB(),
                            JHTML.Text(0, 0, 5, 0, 0, "Minazon", 5f, false, Color.WHITE.getRGB()).setCenteredHorizontally().setCenteredVertically()
                        ),
                        JHTML.Canvas(980,70,false,
                                JHTML.Canvas(490, 65, true,
                                        JHTML.Text(0, -25, 15, 0, 0, "Buy Items", 4f, true, Color.WHITE.getRGB()).setFloatRight().setOnRenderEvent((x, y, w, h) -> {
                                            this.buyItems.x = (int) x;
                                            this.buyItems.y = (int) y;
                                            this.buyItems.setHeight((int)h);
                                            this.buyItems.setWidth((int)w);
                                        }),
                                        getSelection(true)
                                ),
                                JHTML.Canvas(490, 65, true,
                                        JHTML.Text(0, 25, 15, 0, 0, "Sell Items", 4f, true, Color.WHITE.getRGB()).setOnRenderEvent((x, y, w, h) -> {
                                            this.sellItems.x = (int) x;
                                            this.sellItems.y = (int) y;
                                            this.sellItems.setHeight((int)h);
                                            this.sellItems.setWidth((int)w);
                                        }),
                                        getSelection(false)
                                )
                        ).setCenteredHorizontally(),
                        JHTML.Canvas(1920, 550, false,
                                JHTML.Box(470,550,true,  Color.RED.getRGB()),
                                JHTML.Box(980,550,true,  Color.BLUE.getRGB(),
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
                                                new ItemStack(Items.DIAMOND),
                                        })
                                ),
                                JHTML.Box(470,550,true,  Color.GRAY.getRGB())
                        )
                )
        ).render(matrixStack, getMinecraft(), 0, 0);
    }

    private JHTML.Canvas getSelection(boolean isBuy)
    {
        if (isBuy)
        {
            return createSelect(true).setActive(isBuyActive);
        } else
        {
            return createSelect(false).setActive(!isBuyActive);
        }
    }

    private JHTML.Canvas createSelect(boolean isBuy)
    {
        JHTML.Box box = JHTML.Box(195, 5, isBuy ? -25 : 25, 2, 0, 0, Color.WHITE.getRGB());
        return isBuy ? box.setFloatRight() : box;
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
            if (count == 5)
            {
                rows.add(createItemRow(items.toArray(new ItemStack[0])));
                items.clear();
                count = 0;
                continue;
            } else
            {
                if (count + rows.size() * 5 == itemStacks.length)
                {
                    rows.add(createItemRow(items.toArray(new ItemStack[0])));
                    items.clear();
                    count = 0;
                    continue;
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

    private ICurrency getCurrency()
    {
        return getMinecraft().player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
    }
}
