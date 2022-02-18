package me.miquiis.onlyblock.client.events;

import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.ICurrency;
import me.miquiis.onlyblock.common.classes.EasyGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

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

            EasyGUI.StringGUIElement stringGUIElement = new EasyGUI.StringGUIElement(
                    new EasyGUI.Anchor(EasyGUI.VAnchor.BOTTOM, EasyGUI.HAnchor.CENTER),
                    event.getMatrixStack(), event.getWindow(), Minecraft.getInstance().fontRenderer,
                    "$" + amount,
                    true,
                    0f, 22f, 0.80f,
                    true,
                    new Color(46, 217, 91).getRGB());
            stringGUIElement.render(null);
        }
    }

}
