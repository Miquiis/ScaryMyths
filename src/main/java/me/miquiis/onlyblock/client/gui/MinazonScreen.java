package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.classes.EasyGUI;
import me.miquiis.onlyblock.common.classes.JHTML;
import me.miquiis.onlyblock.common.classes.OldEasyGUI;
import me.miquiis.onlyblock.common.containers.MinazonContainer;
import me.miquiis.onlyblock.common.utils.MathUtils;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.BuyItemFromShopPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.*;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.swing.text.html.HTML;
import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MinazonScreen extends Screen {

    public static class ItemSlot {
        public static final ItemSlot EMPTY = new ItemSlot(ItemStack.EMPTY, -1, 0);
        public static final ItemSlot NO_STOCK = new ItemSlot(new ItemStack(Items.BARRIER), -1, 0);

        private ItemStack itemStack;
        private int slot;
        private int value;

        public ItemSlot(ItemStack itemStack, int slot, int value)
        {
            this.itemStack = itemStack;
            this.slot = slot;
            this.value = value;
        }
    }

    public static class ItemButton extends Button {

        private ItemSlot itemSlot;

        public ItemButton(int x, int y, int width, int height, ItemSlot itemSlot, IPressable pressedAction) {
            super(x, y, width, height, new StringTextComponent("item"), pressedAction);
            this.itemSlot = itemSlot;
        }
    }

    private Button buyItems;
    private Button sellItems;
    private Button purchaseButton;
    private Button sellButton;
    private Button sellAllButton;
    private boolean isBuyActive;

    private List<ItemSlot> buyStore;

    private List<ItemButton> itemButtons;

    private ItemSlot selectedItem;

    public static final int DEFAULT_BACKGROUND_COLOR = 0xF0100010;
    public static final int DEFAULT_BORDER_COLOR_START = 0x505000FF;
    public static final int DEFAULT_BORDER_COLOR_END = (DEFAULT_BORDER_COLOR_START & 0xFEFEFE) >> 1 | DEFAULT_BORDER_COLOR_START & 0xFF000000;


    public MinazonScreen() {
        super(new StringTextComponent("Minazon"));
        this.isBuyActive = true;
        buyStore = createBuyStore();
        selectedItem = ItemSlot.EMPTY;
        itemButtons = new ArrayList<>();
        init();
    }

    private List<ItemSlot> createBuyStore()
    {
        final List<ItemSlot> store = new ArrayList<>();

        for (int i = 0; i < 30; i++)
        {
            store.add(new ItemSlot(new ItemStack(ForgeRegistries.ITEMS.getValues().stream().collect(Collectors.toList()).get(i)), i, MathUtils.getRandomMinMax(1000, 10000)));
        }

        return store;
    }

    @Override
    protected void init() {
        super.init();
        this.buyItems = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("buy"), p_onPress_1_ -> {
            isBuyActive = true;
            selectedItem = ItemSlot.EMPTY;
        }));
        this.sellItems = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("buy"), p_onPress_1_ -> {
            isBuyActive = false;
            selectedItem = ItemSlot.EMPTY;
        }));
        this.purchaseButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("purchase"), p_onPress_1_ -> {

        }));
        this.sellButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("sell"), p_onPress_1_ -> {

        }));
        this.sellAllButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("sell_all"), p_onPress_1_ -> {

        }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        //this.renderBackground(matrixStack);
        //super.render(matrixStack, mouseX, mouseY, partialTicks);

        JHTML.Canvas(1920, 1080, true,
                JHTML.Box(1920,1080,true, new Color(0,0,0,150).getRGB(),
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
                                JHTML.Canvas(690,550, 0, -10, 0, -50,true,
                                        JHTML.Canvas(250, 310, true,
                                                JHTML.Text(0, 0, 0, 0, 0, "$" + selectedItem.value, 5f, true, new Color(46, 217, 91).getRGB()).setCenteredHorizontally().setActive(selectedItem.itemStack != ItemStack.EMPTY),
                                                JHTML.Canvas(250, 190, false,
                                                    JHTML.Item(128, 128, 0, 0,0, 0, selectedItem.itemStack, 8f).setCenteredHorizontally().setCenteredVertically()
                                                ),
                                                createBuySellButton()
                                        ).setCenteredVertically().setCenteredHorizontally()
                                ),
                                JHTML.Canvas(530,550,true,
                                        createItemRows()
                                )
                        )
                )
        ).render(matrixStack, getMinecraft(), 0, 0);

        itemButtons.forEach(button -> {
            if (mouseX >= button.x + 1 && mouseX <= button.x + (button.getWidth() - 1))
            {
                if (mouseY >= button.y + 1 && mouseY <= button.y + (button.getHeight() - 1))
                {
                    List<ITextComponent> a = button.itemSlot.itemStack.getTooltip(null, ITooltipFlag.TooltipFlags.NORMAL);
                    a.add(1, new StringTextComponent("Cost: \u00A7a\u00A7l$" + button.itemSlot.value));
                    drawHoveringText(button.itemSlot.itemStack, matrixStack, a, mouseX, mouseY, width, height, -1, DEFAULT_BACKGROUND_COLOR, DEFAULT_BORDER_COLOR_START, DEFAULT_BORDER_COLOR_END, minecraft.fontRenderer);
                }
            }
        });
    }

    private JHTML.Canvas createBuySellButton()
    {
        if (isBuyActive) return
            JHTML.Image(250, 60, 0, 0, 0, 0, false, new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/button.png"),
                    JHTML.Text(0, 0, 0,0, 0, "Purchase", 3f, true, Color.WHITE.getRGB()).setCenteredHorizontally().setCenteredVertically().setActive(selectedItem.itemStack != ItemStack.EMPTY)
            ).setActive(selectedItem.itemStack != ItemStack.EMPTY).setOnRenderEvent((x, y, width1, height1) -> {
                this.purchaseButton.x = (int) x;
                this.purchaseButton.y = (int)y;
                this.purchaseButton.setHeight((int)height1);
                this.purchaseButton.setWidth((int)width1);
            });
        else return
            JHTML.Canvas(200, 120, true,
                    JHTML.Image(200, 60, 0, 0, 0, 0, false, new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/button.png"),
                            JHTML.Text(0, 0, 0,0, 0, "Sell", 3f, true, Color.WHITE.getRGB()).setCenteredHorizontally().setCenteredVertically().setActive(selectedItem.itemStack != ItemStack.EMPTY)
                    ).setCenteredHorizontally().setActive(selectedItem.itemStack != ItemStack.EMPTY).setOnRenderEvent((x, y, width1, height1) -> {
                        this.sellButton.x = (int) x;
                        this.sellButton.y = (int)y;
                        this.sellButton.setHeight((int)height1);
                        this.sellButton.setWidth((int)width1);
                    }),
                    JHTML.Image(250, 60, 0, 10, 0, 0, false, new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/button.png"),
                            JHTML.Text(0, 0, 0,0, 0, "Sell All", 3f, true, Color.WHITE.getRGB()).setCenteredHorizontally().setCenteredVertically().setActive(selectedItem.itemStack != ItemStack.EMPTY)
                    ).setCenteredHorizontally().setActive(selectedItem.itemStack != ItemStack.EMPTY).setOnRenderEvent((x, y, width1, height1) -> {
                        this.sellAllButton.x = (int) x;
                        this.sellAllButton.y = (int)y;
                        this.sellAllButton.setHeight((int)height1);
                        this.sellAllButton.setWidth((int)width1);
                    })
            ).setCenteredHorizontally();
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

    private JHTML.Item createItem(ItemSlot itemSlot, ItemStack itemStack)
    {
        return (JHTML.Item) JHTML.Item(90, 90, 0, 0,0, 0, itemStack, 5f).setOnRenderEvent((x, y, width1, height1) -> {
            itemButtons.add(this.addButton(new ItemButton((int)x, (int)y, (int)width1, (int)height1, itemSlot, (p_onPress_1_ -> {
                selectedItem = itemSlot;
            }))));
        });
    }

    private JHTML.Canvas[] createItemRows()
    {
        itemButtons.clear();
        List<JHTML.Canvas> rows = new ArrayList<>();
        List<ItemSlot> items = new ArrayList<>();

        int count = 0;
        int totalCount = 0;
        for (ItemSlot itemSlot : buyStore)
        {
            totalCount++;
            items.add(itemSlot);
            if (count == 5)
            {
                rows.add(createItemRow(items.toArray(new ItemSlot[0])));
                items.clear();
                count = 0;
                continue;
            } else
            {
                if (totalCount == buyStore.size())
                {
                    rows.add(createItemRow(items.toArray(new ItemSlot[0])));
                    items.clear();
                    count = 0;
                    continue;
                }
            }
            count++;
        }

        return rows.toArray(new JHTML.Canvas[0]);
    }

    private JHTML.Canvas createItemRow(ItemSlot[] itemSlots)
    {
        List<JHTML.Item> row = new ArrayList<>();

        for (ItemSlot itemSlot : itemSlots)
        {
            row.add(createItem(itemSlot, itemSlot.itemStack));
        }

        return JHTML.Canvas(980, 90, false,
                row.toArray(new JHTML.Canvas[0])
        );
    }

    private ICurrency getCurrency()
    {
        return getMinecraft().player.getCapability(CurrencyCapability.CURRENCY_CAPABILITY).orElse(null);
    }

    public static void drawHoveringText(@Nonnull final ItemStack stack, MatrixStack mStack, List<? extends ITextProperties> textLines, int mouseX, int mouseY,
                                        int screenWidth, int screenHeight, int maxTextWidth,
                                        int backgroundColor, int borderColorStart, int borderColorEnd, FontRenderer font)
    {
        if (!textLines.isEmpty())
        {
            RenderTooltipEvent.Pre event = new RenderTooltipEvent.Pre(stack, textLines, mStack, mouseX, mouseY, screenWidth, screenHeight, maxTextWidth, font);
            if (MinecraftForge.EVENT_BUS.post(event))
                return;
            mouseX = event.getX();
            mouseY = event.getY();
            screenWidth = event.getScreenWidth();
            screenHeight = event.getScreenHeight();
            maxTextWidth = event.getMaxWidth();
            font = event.getFontRenderer();

            RenderSystem.disableRescaleNormal();
            RenderSystem.disableDepthTest();
            int tooltipTextWidth = 0;

            for (ITextProperties textLine : textLines)
            {
                int textLineWidth = font.getStringPropertyWidth(textLine);
                if (textLineWidth > tooltipTextWidth)
                    tooltipTextWidth = textLineWidth;
            }

            boolean needsWrap = false;

            int titleLinesCount = 1;
            int tooltipX = mouseX + 12;
            if (tooltipX + tooltipTextWidth + 4 > screenWidth)
            {
                tooltipX = mouseX - 16 - tooltipTextWidth;
                if (tooltipX < 4) // if the tooltip doesn't fit on the screen
                {
                    if (mouseX > screenWidth / 2)
                        tooltipTextWidth = mouseX - 12 - 8;
                    else
                        tooltipTextWidth = screenWidth - 16 - mouseX;
                    needsWrap = true;
                }
            }

            if (maxTextWidth > 0 && tooltipTextWidth > maxTextWidth)
            {
                tooltipTextWidth = maxTextWidth;
                needsWrap = true;
            }

            if (needsWrap)
            {
                int wrappedTooltipWidth = 0;
                List<ITextProperties> wrappedTextLines = new ArrayList<>();
                for (int i = 0; i < textLines.size(); i++)
                {
                    ITextProperties textLine = textLines.get(i);
                    List<ITextProperties> wrappedLine = font.getCharacterManager().func_238362_b_(textLine, tooltipTextWidth, Style.EMPTY);
                    if (i == 0)
                        titleLinesCount = wrappedLine.size();

                    for (ITextProperties line : wrappedLine)
                    {
                        int lineWidth = font.getStringPropertyWidth(line);
                        if (lineWidth > wrappedTooltipWidth)
                            wrappedTooltipWidth = lineWidth;
                        wrappedTextLines.add(line);
                    }
                }
                tooltipTextWidth = wrappedTooltipWidth;
                textLines = wrappedTextLines;

                if (mouseX > screenWidth / 2)
                    tooltipX = mouseX - 16 - tooltipTextWidth;
                else
                    tooltipX = mouseX + 12;
            }

            int tooltipY = mouseY - 12;
            int tooltipHeight = 8;

            if (textLines.size() > 1)
            {
                tooltipHeight += (textLines.size() - 1) * 10;
                if (textLines.size() > titleLinesCount)
                    tooltipHeight += 2; // gap between title lines and next lines
            }

            if (tooltipY < 4)
                tooltipY = 4;
            else if (tooltipY + tooltipHeight + 4 > screenHeight)
                tooltipY = screenHeight - tooltipHeight - 4;

            final int zLevel = 400;
            RenderTooltipEvent.Color colorEvent = new RenderTooltipEvent.Color(stack, textLines, mStack, tooltipX, tooltipY, font, backgroundColor, borderColorStart, borderColorEnd);
            MinecraftForge.EVENT_BUS.post(colorEvent);
            backgroundColor = colorEvent.getBackground();
            borderColorStart = colorEvent.getBorderStart();
            borderColorEnd = colorEvent.getBorderEnd();

            mStack.push();
            Matrix4f mat = mStack.getLast().getMatrix();
            //TODO, lots of unnessesary GL calls here, we can buffer all these together.
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 4, tooltipX + tooltipTextWidth + 3, tooltipY - 3, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 4, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 4, tooltipY - 3, tooltipX - 3, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 3, tooltipY - 3, tooltipX + tooltipTextWidth + 4, tooltipY + tooltipHeight + 3, backgroundColor, backgroundColor);
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3 + 1, tooltipX - 3 + 1, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX + tooltipTextWidth + 2, tooltipY - 3 + 1, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3 - 1, borderColorStart, borderColorEnd);
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY - 3, tooltipX + tooltipTextWidth + 3, tooltipY - 3 + 1, borderColorStart, borderColorStart);
            GuiUtils.drawGradientRect(mat, zLevel, tooltipX - 3, tooltipY + tooltipHeight + 2, tooltipX + tooltipTextWidth + 3, tooltipY + tooltipHeight + 3, borderColorEnd, borderColorEnd);

            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostBackground(stack, textLines, mStack, tooltipX, tooltipY, font, tooltipTextWidth, tooltipHeight));

            IRenderTypeBuffer.Impl renderType = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
            mStack.translate(0.0D, 0.0D, zLevel);

            int tooltipTop = tooltipY;

            for (int lineNumber = 0; lineNumber < textLines.size(); ++lineNumber)
            {
                ITextProperties line = textLines.get(lineNumber);
                if (line != null)
                    font.drawEntityText(LanguageMap.getInstance().func_241870_a(line), (float)tooltipX, (float)tooltipY, -1, true, mat, renderType, false, 0, 15728880);

                if (lineNumber + 1 == titleLinesCount)
                    tooltipY += 2;

                tooltipY += 10;
            }

            renderType.finish();
            mStack.pop();

            MinecraftForge.EVENT_BUS.post(new RenderTooltipEvent.PostText(stack, textLines, mStack, tooltipX, tooltipTop, font, tooltipTextWidth, tooltipHeight));

            RenderSystem.enableDepthTest();
            RenderSystem.enableRescaleNormal();
        }
    }
}
