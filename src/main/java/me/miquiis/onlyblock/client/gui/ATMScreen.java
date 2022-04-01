package me.miquiis.onlyblock.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.classes.JHTML;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.ATMPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ATMScreen extends Screen {

    public enum CurrentScreen {
        MAIN,
        DEPOSIT,
        CASHOUT,
        TRANSFER,
        PIN,
        LOADING
    }

    private final ResourceLocation TOP_BAR = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/top_bar.png");
    private final ResourceLocation BALANCE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/balance.png");
    private final ResourceLocation INFO = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/info.png");
    private final ResourceLocation CASH_OUT = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/cash_out.png");
    private final ResourceLocation DEPOSIT = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/deposit_bar.png");
    private final ResourceLocation TRADE = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/trade_bar.png");

    private final ResourceLocation BACK = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/back_bar.png");
    private final ResourceLocation VALID = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/valid_bar.png");
    private final ResourceLocation AMOUNT = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/amount_bar.png");

    private final ResourceLocation PIN_OUTPUT = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/pin_output.png");
    private final ResourceLocation PIN_PAD = new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/pin_pad.png");

    private CurrentScreen currentRendering;

    private PlayerEntity playerATM;
    private IOnlyMoneyBlock moneyCapability;

    private boolean isKeyboardOn;
    private boolean canBeCleared;
    private String keyboardInput;
    private String pinInput;

    private Button depositButton;
    private Button cashoutButton;
    private Button transferButton;

    private Button backButton;
    private Button confirmButton;

    private Button pinOne, pinTwo, pinThree, pinFour, pinFive, pinSix, pinSeven, pinEigth, pinNine, pinZero;

    private int currentLoading;
    private int clientID;

    public ATMScreen(int clientId) {
        super(new StringTextComponent("ATM"));
        this.clientID = clientId;
        System.out.println(clientId);
        init();
    }

    protected void init() {
        super.init();

        this.minecraft = Minecraft.getInstance();
        this.isKeyboardOn = false;
        this.canBeCleared = true;
        this.keyboardInput = "";
        this.pinInput = "";
        this.currentLoading = 0;

        this.depositButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("deposit"), p_onPress_1_ -> {
            this.currentRendering = CurrentScreen.DEPOSIT;
            this.isKeyboardOn = true;
            resetKeyboard();
        }));

        this.cashoutButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("cashout"), p_onPress_1_ -> {
            this.currentRendering = CurrentScreen.CASHOUT;
            this.isKeyboardOn = true;
            resetKeyboard();
        }));

        this.transferButton = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("transfer"), p_onPress_1_ -> {
            this.currentRendering = CurrentScreen.TRANSFER;
            this.isKeyboardOn = true;
            resetKeyboard();
        }));

        this.pinOne = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 1;
            checkPin();
        }));

        this.pinTwo = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 2;
            checkPin();
        }));

        this.pinThree = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 3;
            checkPin();
        }));

        this.pinFour = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 4;
            checkPin();
        }));

        this.pinFive = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 5;
            checkPin();
        }));

        this.pinSix = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 6;
            checkPin();
        }));

        this.pinSeven = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 7;
            checkPin();
        }));

        this.pinEigth = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 8;
            checkPin();
        }));

        this.pinNine = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 9;
            checkPin();
        }));

        this.pinZero = this.addButton(new Button(0, 0, 0, 0, new StringTextComponent("pin"), p_onPress_1_ -> {
            this.pinInput += 0;
            checkPin();
        }));

        currentRendering = CurrentScreen.MAIN;

        if (!(minecraft.world.getEntityByID(clientID) instanceof PlayerEntity))
        {
            currentRendering = CurrentScreen.PIN;
        } else
        {
            this.playerATM = (PlayerEntity)minecraft.world.getEntityByID(clientID);
            this.moneyCapability = OnlyMoneyBlock.getCapability(playerATM);

            if (moneyCapability.getPin().isEmpty())
            {
                currentRendering = CurrentScreen.PIN;
            }
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        buttons.forEach(widget -> widget.active = false);
        JHTML.Canvas(1920, 1080, true,
                JHTML.Canvas(800, 545, true,
                        JHTML.Image(800, 65, TOP_BAR),
                        JHTML.Box(800, 480, new Color(237, 237, 237).getRGB(),
                                currentRendering == CurrentScreen.MAIN ? getMainMenu() : currentRendering == CurrentScreen.LOADING ? getLoadingScreen() : currentRendering == CurrentScreen.PIN ? getPin() : currentRendering == CurrentScreen.DEPOSIT ? getDepositMenu() : currentRendering == CurrentScreen.CASHOUT ? getWithdrawalMenu() : currentRendering == CurrentScreen.TRANSFER ? getTransferMenu() : getMainMenu()
                        )
                ).setCenteredVertically().setCenteredHorizontally()

        ).render(matrixStack, Minecraft.getInstance(), 0, 0);
    }

    private JHTML.Canvas getLoadingScreen() {
        return JHTML.Image(900, 600, 0, -20, 0, 0, new ResourceLocation(OnlyBlock.MOD_ID, "textures/gui/atm/loading/loading-" + currentLoading + ".png")
        ).setCenteredVertically().setCenteredHorizontally();
    }

    @Override
    public void tick() {
        if (currentRendering == CurrentScreen.LOADING)
        {
            currentLoading++;
            if (currentLoading > 29)
            {
                currentLoading = 0;
            }
        }
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (isKeyboardOn)
        {
            if (keyCode == 259)
            {
                if (keyboardInput.length() == 0) return super.keyReleased(keyCode, scanCode, modifiers);
                keyboardInput = keyboardInput.substring(0, keyboardInput.length() - 1);
                minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1f, 0.8f);
                return super.keyReleased(keyCode, scanCode, modifiers);
            }
            if (KeyEvent.getKeyText(keyCode).contains("Unknown")) return super.keyReleased(keyCode, scanCode, modifiers);
            minecraft.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1f, 1.8f);
            keyboardInput += KeyEvent.getKeyText(keyCode);
            canBeCleared = false;
        }
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    private void checkPin()
    {
        if (pinInput.length() == 4)
        {
            // Send to Server.
            String prevPin = pinInput;
            pinInput = "";
            currentRendering = CurrentScreen.LOADING;
            OnlyBlockNetwork.CHANNEL.sendToServer(new ATMPacket(ATMPacket.ATMPacketType.REQUEST_ATM, ATMPacket.createRequestATMData(prevPin)));
        }
    }

    private void resetKeyboard()
    {
        if (keyboardInput.length() > 0)
        keyboardInput = "";
    }


    private String getOnlyDigitKeyboard()
    {
        return keyboardInput.chars().filter(Character::isDigit).mapToObj(c -> (char)c).map(Object::toString).collect(Collectors.joining());
    }

    private JHTML.Canvas getPin()
    {
        return JHTML.Canvas(800, 480, false,
                JHTML.Image(336, 446, 100, 20, 0, 0, PIN_PAD,
                        JHTML.Canvas(320, 100, 8, 8, 0, 0, false,
                                JHTML.Canvas(100, 100, 0, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinOne.active = true;
                                    this.pinOne.x = (int)x;
                                    this.pinOne.y = (int)y;
                                    this.pinOne.setWidth((int)width1);
                                    this.pinOne.setHeight((int)height1);
                                }),
                                JHTML.Canvas(100, 100, 10, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinTwo.active = true;
                                    this.pinTwo.x = (int)x;
                                    this.pinTwo.y = (int)y;
                                    this.pinTwo.setWidth((int)width1);
                                    this.pinTwo.setHeight((int)height1);
                                }),
                                JHTML.Canvas(100, 100, 10, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinThree.active = true;
                                    this.pinThree.x = (int)x;
                                    this.pinThree.y = (int)y;
                                    this.pinThree.setWidth((int)width1);
                                    this.pinThree.setHeight((int)height1);
                                })
                        ),
                        JHTML.Canvas(320, 100, 8, 8, 0, 0, false,
                                JHTML.Canvas(100, 100, 0, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinFour.active = true;
                                    this.pinFour.x = (int)x;
                                    this.pinFour.y = (int)y;
                                    this.pinFour.setWidth((int)width1);
                                    this.pinFour.setHeight((int)height1);
                                }),
                                JHTML.Canvas(100, 100, 10, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinFive.active = true;
                                    this.pinFive.x = (int)x;
                                    this.pinFive.y = (int)y;
                                    this.pinFive.setWidth((int)width1);
                                    this.pinFive.setHeight((int)height1);
                                }),
                                JHTML.Canvas(100, 100, 10, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinSix.active = true;
                                    this.pinSix.x = (int)x;
                                    this.pinSix.y = (int)y;
                                    this.pinSix.setWidth((int)width1);
                                    this.pinSix.setHeight((int)height1);
                                })
                        ),
                        JHTML.Canvas(320, 100, 8, 8, 0, 0, false,
                                JHTML.Canvas(100, 100, 0, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinSeven.active = true;
                                    this.pinSeven.x = (int)x;
                                    this.pinSeven.y = (int)y;
                                    this.pinSeven.setWidth((int)width1);
                                    this.pinSeven.setHeight((int)height1);
                                }),
                                JHTML.Canvas(100, 100, 10, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinEigth.active = true;
                                    this.pinEigth.x = (int)x;
                                    this.pinEigth.y = (int)y;
                                    this.pinEigth.setWidth((int)width1);
                                    this.pinEigth.setHeight((int)height1);
                                }),
                                JHTML.Canvas(100, 100, 10, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinNine.active = true;
                                    this.pinNine.x = (int)x;
                                    this.pinNine.y = (int)y;
                                    this.pinNine.setWidth((int)width1);
                                    this.pinNine.setHeight((int)height1);
                                })
                        ),
                        JHTML.Canvas(320, 100, 8, 8, 0, 0, false,
                                JHTML.Canvas(100, 100, 0, 0, 0, 0),
                                JHTML.Canvas(100, 100, 10, 0, 0, 0).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.pinZero.active = true;
                                    this.pinZero.x = (int)x;
                                    this.pinZero.y = (int)y;
                                    this.pinZero.setWidth((int)width1);
                                    this.pinZero.setHeight((int)height1);
                                }),
                                JHTML.Canvas(100, 100, 10, 0, 0, 0)
                        )
                ),
                JHTML.Image(116, 401, 180, 38, 0, 0, PIN_OUTPUT,
                        JHTML.Text(0, 37, 30, 0, 0, "\u00A70" + (pinInput.length() > 0 ? pinInput.charAt(0) : ""), 8f),
                        JHTML.Text(0, 37, 24, 0, 0, "\u00A70" + (pinInput.length() > 1 ? pinInput.charAt(1) : ""), 8f),
                        JHTML.Text(0, 37, 24, 0, 0, "\u00A70" + (pinInput.length() > 2 ? pinInput.charAt(2) : ""), 8f),
                        JHTML.Text(0, 37, 24, 0, 0, "\u00A70" + (pinInput.length() > 3 ? pinInput.charAt(3) : ""), 8f)
                )
        );
    }

    private JHTML.Canvas getDepositMenu()
    {
        return JHTML.Canvas(800, 480, true,
                JHTML.Canvas(800, 86, 50, 50, 0, 0, false,
                        JHTML.Box(420, 70, 0, 0, 0, 0, new Color(32, 234, 90).getRGB(),
                                JHTML.Text(0, 20, 15, 0, 0, "\u00A7f\u00A7lDeposit your money", 2.5f),
                                JHTML.Text(0, 20, 5, 0, 0, "\u00A70Save your money from other players", 2f)
                        ),
                        JHTML.Image(216, 86, 72, -8, 0, 0, BACK,
                                JHTML.Text(0, 70, 33, 0, 0, "\u00A7f\u00A7lGo Back", 2.5f)
                        ).setOnRenderEvent((x, y, width1, height1) -> {
                            this.backButton = this.addButton(new Button((int)x, (int)y, (int)width1, (int)height1, new StringTextComponent("back"), p_onPress_1_ -> {
                                this.currentRendering = CurrentScreen.MAIN;
                            }));
                            this.backButton.active = true;
                        })
                ),
                JHTML.Image(716, 166, 42, 20, 0, 0, AMOUNT,
                        JHTML.Text(0, 30, 30, 0, 0, "\u00A70How much you want to deposit?", 2f),
                        JHTML.Text(0, 0, 10, 0, 0, "\u00A7a\u00A7l$" + getOnlyDigitKeyboard(), 5f).setAbsolutePosition().setCenteredHorizontally().setCenteredVertically()
                ),
                JHTML.Image(216, 86, 42, 30, 0, 0, VALID,
                        JHTML.Text(0, 70, 33, 0, 0, "\u00A7f\u00A7lConfirm", 2.5f)
                ).setOnRenderEvent((x, y, width1, height1) -> {
                    this.confirmButton = this.addButton(new Button((int)x, (int)y, (int)width1, (int)height1, new StringTextComponent("confirm"), p_onPress_1_ -> {
                        this.currentRendering = CurrentScreen.MAIN;
                        OnlyBlockNetwork.CHANNEL.sendToServer(new ATMPacket(ATMPacket.ATMPacketType.DEPOSIT, ATMPacket.createProcessATMData(null, playerATM.getEntityId(), Integer.parseInt(getOnlyDigitKeyboard()))));
                        System.out.println("Depositing " + getOnlyDigitKeyboard());
                    }));
                    this.confirmButton.active = true;
                })
        );
    }

    private JHTML.Canvas getWithdrawalMenu()
    {
        return JHTML.Canvas(800, 480, true,
                JHTML.Canvas(800, 86, 50, 50, 0, 0, false,
                        JHTML.Box(420, 70, 0, 0, 0, 0, new Color(255, 77, 77).getRGB(),
                                JHTML.Text(0, 20, 15, 0, 0, "\u00A7f\u00A7lWithdrawal", 2.5f),
                                JHTML.Text(0, 20, 5, 0, 0, "\u00A70Grab some cash to buy items", 2f)
                        ),
                        JHTML.Image(216, 86, 72, -8, 0, 0, BACK,
                                JHTML.Text(0, 70, 33, 0, 0, "\u00A7f\u00A7lGo Back", 2.5f)
                        ).setOnRenderEvent((x, y, width1, height1) -> {
                            this.backButton = this.addButton(new Button((int)x, (int)y, (int)width1, (int)height1, new StringTextComponent("back"), p_onPress_1_ -> {
                                this.currentRendering = CurrentScreen.MAIN;
                            }));
                            this.backButton.active = true;
                        })
                ),
                JHTML.Image(716, 166, 42, 20, 0, 0, AMOUNT,
                        JHTML.Text(0, 30, 30, 0, 0, "\u00A70How much you want to withdrawal?", 2f),
                        JHTML.Text(0, 0, 10, 0, 0, "\u00A7a\u00A7l$" + getOnlyDigitKeyboard(), 5f).setAbsolutePosition().setCenteredHorizontally().setCenteredVertically()
                ),
                JHTML.Image(216, 86, 42, 30, 0, 0, VALID,
                        JHTML.Text(0, 70, 33, 0, 0, "\u00A7f\u00A7lConfirm", 2.5f)
                ).setOnRenderEvent((x, y, width1, height1) -> {
                    this.confirmButton = this.addButton(new Button((int)x, (int)y, (int)width1, (int)height1, new StringTextComponent("confirm"), p_onPress_1_ -> {
                        this.currentRendering = CurrentScreen.MAIN;
                        OnlyBlockNetwork.CHANNEL.sendToServer(new ATMPacket(ATMPacket.ATMPacketType.WITHDRAWAL, ATMPacket.createProcessATMData(null, playerATM.getEntityId(), Integer.parseInt(getOnlyDigitKeyboard()))));
                        System.out.println("Withdrawing " + getOnlyDigitKeyboard());
                    }));
                    this.confirmButton.active = true;
                })
        );
    }

    private JHTML.Canvas getTransferMenu()
    {
        return JHTML.Canvas(800, 480, true,
                JHTML.Canvas(800, 86, 50, 50, 0, 0, false,
                        JHTML.Box(420, 70, 0, 0, 0, 0, new Color(255, 121, 12).getRGB(),
                                JHTML.Text(0, 20, 15, 0, 0, "\u00A7f\u00A7lTransfer", 2.5f),
                                JHTML.Text(0, 20, 5, 0, 0, "\u00A70Send money to another player", 2f)
                        ),
                        JHTML.Image(216, 86, 72, -8, 0, 0, BACK,
                                JHTML.Text(0, 70, 33, 0, 0, "\u00A7f\u00A7lGo Back", 2.5f)
                        ).setOnRenderEvent((x, y, width1, height1) -> {
                            this.backButton = this.addButton(new Button((int)x, (int)y, (int)width1, (int)height1, new StringTextComponent("back"), p_onPress_1_ -> {
                                this.currentRendering = CurrentScreen.MAIN;
                            }));
                            this.backButton.active = true;
                        })
                ),
                JHTML.Image(716, 166, 42, 20, 0, 0, AMOUNT,
                        JHTML.Text(0, 30, 30, 0, 0, "\u00A70How much you want to transfer?", 2f),
                        JHTML.Text(0, 0, 10, 0, 0, "\u00A7a\u00A7l$" + getOnlyDigitKeyboard(), 5f).setAbsolutePosition().setCenteredHorizontally().setCenteredVertically()
                ),
                JHTML.Image(216, 86, 42, 30, 0, 0, VALID,
                        JHTML.Text(0, 70, 33, 0, 0, "\u00A7f\u00A7lConfirm", 2.5f)
                ).setOnRenderEvent((x, y, width1, height1) -> {
                    this.confirmButton = this.addButton(new Button((int)x, (int)y, (int)width1, (int)height1, new StringTextComponent("confirm"), p_onPress_1_ -> {
                        this.currentRendering = CurrentScreen.MAIN;
                        System.out.println("Transfering " + getOnlyDigitKeyboard());
                    }));
                    this.confirmButton.active = true;
                })
        );
    }

    private JHTML.Canvas getMainMenu()
    {
        return JHTML.Canvas(800, 480, true,
                JHTML.Canvas(710, 320, 40, 40, 0, 0, false,
                        JHTML.Image(466, 316, 0, 0, 0, 0, BALANCE,
                                JHTML.Text(0, 40, 40, 0, 0, "\u00A70Welcome,", 2f),
                                JHTML.Text(0, 40, 10, 0, 0, "\u00A7a" + playerATM.getName().getString(), 4f),
                                JHTML.Text(0, 40, 50, 0, 0, "\u00A70Balance:", 2f),
                                JHTML.Text(0, 40, 25, 0, 0, "\u00A7a\u00A7l$" + moneyCapability.getBankAccount(), 5f)
                        ),
                        JHTML.Canvas(236, 316, 25, 0, 0, 0,
                                JHTML.Image(236, 96, 0, 0, 0, 0, CASH_OUT,
                                        JHTML.Text(0, 85, 40, 0, 0, "\u00A7lDeposit", 2f)
                                ).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.depositButton.active = true;
                                    this.depositButton.x = (int)x;
                                    this.depositButton.y = (int)y;
                                    this.depositButton.setWidth((int) width1);
                                    this.depositButton.setHeight((int) height1);
                                }),
                                JHTML.Image(236, 96, 0, 14, 0, 0, DEPOSIT,
                                        JHTML.Text(0, 85, 40, 0, 0, "\u00A7lWithdrawal", 2f)
                                ).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.cashoutButton.active = true;
                                    this.cashoutButton.x = (int)x;
                                    this.cashoutButton.y = (int)y;
                                    this.cashoutButton.setWidth((int) width1);
                                    this.cashoutButton.setHeight((int) height1);
                                }),
                                JHTML.Image(236, 96, 0, 14, 0, 0, TRADE,
                                        JHTML.Text(0, 85, 40, 0, 0, "\u00A7lTransfer", 2f)
                                ).setOnRenderEvent((x, y, width1, height1) -> {
                                    this.transferButton.active = true;
                                    this.transferButton.x = (int)x;
                                    this.transferButton.y = (int)y;
                                    this.transferButton.setWidth((int) width1);
                                    this.transferButton.setHeight((int) height1);
                                })
                        )
                ),
                JHTML.Canvas(800, 65, 40, 5, 0, 0, false,
                        JHTML.Image(316, 96, 0, 0, 0, 0, INFO),
                        JHTML.Image(316, 96, 95, 0, 0, 0, INFO)
                )
        );
    }
}
