package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.classes.JHTML;
import me.miquiis.onlyblock.common.registries.ItemRegister;
import me.miquiis.onlyblock.common.utils.MathUtils;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.*;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.gui.GuiUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LaptopScreen extends Screen {

    enum LaptopPage {
        LOGIN_PAGE,
        PC,
        AMAZON_PAGE
    }

    private static final int PC_BACKGROUND = new Color(35, 35, 35).getRGB();
    private static final int WINDOW_SEARCH_BAR = new Color(77, 77, 77).getRGB();
    private static final int TOOL_BAR = new Color(54, 54, 54).getRGB();

    private static final int AMAZON_TOP_BAR = new Color(19, 26, 34).getRGB();
    private static final int AMAZON_MID_BAR = new Color(35, 47, 62).getRGB();
    private static final int AMAZON_BOTTOM_BAR = new Color(55, 71, 90).getRGB();

    private static final ResourceLocation FILE_EXPLORER_ICON = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/explorer.png");
    private static final ResourceLocation CHROME_ICON = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/chrome.png");
    private static final ResourceLocation MINECRAFT_ICON = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/minecraft.png");
    private static final ResourceLocation TRASH_ICON = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/trash.png");
    private static final ResourceLocation WINDOWS_ICON = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/windows.png");

    private static final ResourceLocation WINDOW_CLOSE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/window_close.png");
    private static final ResourceLocation WINDOW_MINIMIZE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/window_minimize.png");
    private static final ResourceLocation WINDOW_ADJUST = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/window_adjust.png");

    private static final ResourceLocation LOGIN_AVATAR = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/login_avatar.png");
    private static final ResourceLocation LOGIN_ARROW = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/login_arrow.png");

    public static final int DEFAULT_BACKGROUND_COLOR = 0xF0100010;
    public static final int DEFAULT_BORDER_COLOR_START = 0x505000FF;
    public static final int DEFAULT_BORDER_COLOR_END = (DEFAULT_BORDER_COLOR_START & 0xFEFEFE) >> 1 | DEFAULT_BORDER_COLOR_START & 0xFF000000;

    private LaptopPage currentPage;
    private String currentPassword;

    private Button loginButton;
    private Button chromeButton;
    private Button closeWindowButton;

    public LaptopScreen() {
        super(new StringTextComponent("Laptop"));
        this.currentPage = LaptopPage.LOGIN_PAGE;
        this.currentPassword = "";
        init();
    }

    @Override
    protected void init() {
        super.init();
        this.loginButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("login"), p_onPress_1_ -> {
            loginAttempt();
        }));
        this.chromeButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("chrome"), p_onPress_1_ -> {
            currentPage = LaptopPage.AMAZON_PAGE;
        }));
        this.closeWindowButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("close_window"), p_onPress_1_ -> {
            currentPage = LaptopPage.PC;
        }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        buttons.forEach(widget -> widget.active = false);
        JHTML.Canvas(1920, 1080, true,
                currentPage == LaptopPage.AMAZON_PAGE ? getPCBackground(getAmazonTab()) : currentPage == LaptopPage.PC ? getPCBackground() : getLoginBackground()
        ).render(matrixStack, getMinecraft(), 0, 0);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (currentPage == LaptopPage.LOGIN_PAGE)
        {
            if (keyCode == 259)
            {
                if (currentPassword.length() == 0) return super.keyReleased(keyCode, scanCode, modifiers);
                currentPassword = currentPassword.substring(0, currentPassword.length() - 1);
                minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1f, 0.8f);
                return super.keyReleased(keyCode, scanCode, modifiers);
            }
            if (keyCode == 257)
            {
                loginAttempt();
                return super.keyReleased(keyCode, scanCode, modifiers);
            }
            if (currentPassword.length() >= 17) return super.keyReleased(keyCode, scanCode, modifiers);
            if (KeyEvent.getKeyText(keyCode).contains("Unknown")) return super.keyReleased(keyCode, scanCode, modifiers);
            minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1f, 1.8f);
            currentPassword += KeyEvent.getKeyText(keyCode);
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    private void loginAttempt()
    {
        if (currentPage == LaptopPage.LOGIN_PAGE)
        {
            if (currentPassword.equalsIgnoreCase("richestmanalive"))
            {
                currentPage = LaptopPage.PC;
            }
            currentPassword = "";
        }
    }

    private String getHiddenPassword()
    {
        return currentPassword;
    }

    private JHTML.Canvas getLoginBackground()
    {
        loginButton.active = true;
        return JHTML.Box(1320, 800, PC_BACKGROUND,
                JHTML.Canvas(400, 500,
                        JHTML.Image(200, 200, LOGIN_AVATAR).setCenteredHorizontally(),
                        JHTML.Text(0, 0, 30, 0, 0, "Jeff Bezos", 5f, true, Color.WHITE.getRGB()).setCenteredHorizontally(),
                        JHTML.Canvas(400, 35, 0, 30, 0, 0, false,
                                JHTML.Box(365, 35, Color.GRAY.getRGB(),
                                        JHTML.Box(361, 31, Color.WHITE.getRGB(),
                                                JHTML.Text(0, 10, 9, 0,0, getHiddenPassword(), 2f, false, Color.BLACK.getRGB())
                                        ).setCenteredHorizontally().setCenteredVertically()
                                ),
                                JHTML.Box(35, 35, Color.WHITE.getRGB(),
                                        JHTML.Box(33, 33, new Color(100, 100, 100, 100).getRGB(),
                                                JHTML.Image(30, 30, LOGIN_ARROW)
                                        ).setCenteredHorizontally().setCenteredVertically()
                                ).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.loginButton.x = (int) x;
                                    this.loginButton.y = (int)y;
                                    this.loginButton.setHeight((int)height1);
                                    this.loginButton.setWidth((int)width1);
                                })
                        )
                ).setCenteredHorizontally().setCenteredVertically(),
                JHTML.Canvas(1320, 240),
                JHTML.Box(1320, 60, TOOL_BAR,
                        JHTML.Canvas(1320, 40, 10, 10, 0, 0,
                                JHTML.Image(37, 37, WINDOWS_ICON).setCenteredHorizontally().setCenteredVertically()
                        )
                )
        ).setCenteredHorizontally().setCenteredVertically();
    }

    private JHTML.Canvas getPCBackground(JHTML.Canvas ...windowsOpened)
    {
        chromeButton.active = windowsOpened.length == 0;

        return JHTML.Box(1320, 800, PC_BACKGROUND,
                JHTML.Canvas(1320, 740, 0, 0, 20, 20,
                        JHTML.Canvas(1320, 740, windowsOpened).setAbsolutePosition(),
                        createIcon(FILE_EXPLORER_ICON),
                        createIcon(CHROME_ICON).setOnRenderEvent((x, y, width1, height1) -> {
                            this.chromeButton.x = (int) x;
                            this.chromeButton.y = (int)y;
                            this.chromeButton.setHeight((int)height1);
                            this.chromeButton.setWidth((int)width1);
                        }),
                        createIcon(MINECRAFT_ICON),
                        createIcon(TRASH_ICON)
                ),
                JHTML.Box(1320, 60, TOOL_BAR,
                        JHTML.Canvas(1320, 40, 10, 10, 0, 0,
                                JHTML.Image(37, 37, WINDOWS_ICON).setCenteredHorizontally().setCenteredVertically()
                        )
                )
        ).setCenteredHorizontally().setCenteredVertically();
    }

    private JHTML.Canvas getAmazonTab()
    {
        return createWindow(
                JHTML.Box(1070, 50, AMAZON_TOP_BAR),
                JHTML.Box(1070, 30, AMAZON_MID_BAR),
                JHTML.Box(1070, 30, AMAZON_BOTTOM_BAR),
                JHTML.Box(1070, 440, false, Color.LIGHT_GRAY.getRGB(),
                        createSellWindow(new ItemStack(ItemRegister.GOLDEN_BAZOOKA.get()), "Golden Bazooka", 0),
                        createSellWindow(new ItemStack(ItemRegister.GOLDEN_HELICOPTER.get()), "Golden Helicopter", 0),
                        createSellWindow(new ItemStack(Items.BARRIER), "*OUT OF ORDER*", 0)
                )
        ).setAbsolutePosition();
    }

    private JHTML.Canvas createSellWindow(ItemStack itemIcon, String name, int itemPrice)
    {
        return JHTML.Box(300, 400, 40, 20, 0, 0, Color.WHITE.getRGB(),
                JHTML.Item(200, 200, 0, 20, 0,0, itemIcon, 2f).setCenteredHorizontally(),
                JHTML.Text(0, 0, 20, 0, 0, "\u00A70" + name, 3f).setCenteredHorizontally(),
                JHTML.Text(0, 0, 20, 0, 0, "\u00A72$" + itemPrice, 3f).setCenteredHorizontally(),
                JHTML.Image(280, 40, 0, 30, 0, 0, false, new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/laptop/buy_button.png"),
                        JHTML.Text(0, 0, 0,0, 0, "Buy", 3f, true, Color.WHITE.getRGB()).setCenteredHorizontally().setCenteredVertically()
                ).setCenteredHorizontally()
        );
    }

    private JHTML.Canvas createWindow(JHTML.Canvas... windowContent)
    {
        closeWindowButton.active = true;
        if (windowContent == null)
        {
            return createNewWindow(JHTML.Box(1070, 550, Color.WHITE.getRGB()));
        } else
        {
            return createNewWindow(windowContent);
        }
    }

    private JHTML.Canvas createNewWindow(JHTML.Canvas... windowContent)
    {
        return JHTML.Canvas(1070, 640, 150, 20, 0, 0,
                JHTML.Box(1070, 40, 0, 0, -10, 0, false, TOOL_BAR,
                        JHTML.Image(14, 14, WINDOW_CLOSE).setFloatRight().setCenteredVertically().setOnRenderEvent((x, y, width1, height1) -> {
                            this.closeWindowButton.x = (int) x;
                            this.closeWindowButton.y = (int)y;
                            this.closeWindowButton.setHeight((int)height1);
                            this.closeWindowButton.setWidth((int)width1);
                        }),
                        JHTML.Image(14, 14, -10, 0, 0, 0, WINDOW_ADJUST).setFloatRight().setCenteredVertically(),
                        JHTML.Image(14, 14, -10, 0, 0, 0, WINDOW_MINIMIZE).setFloatRight().setCenteredVertically()
                ),
                JHTML.Box(1070, 50, WINDOW_SEARCH_BAR,
                        JHTML.Box(800, 30, TOOL_BAR).setCenteredHorizontally().setCenteredVertically()
                ),
                JHTML.Canvas(1070, 550,
                        windowContent
                )
        );
    }

    private JHTML.Canvas createIcon(ResourceLocation icon)
    {
        return JHTML.Canvas(100, 150, true,
                JHTML.Image(100, 100, icon),
                JHTML.Box(80, 20, 10, 10, 0, 0, new Color(255, 255, 255).getRGB())
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
