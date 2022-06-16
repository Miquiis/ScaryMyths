package me.miquiis.scarymyths.mixin.client;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.screen.IngameMenuScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(IngameMenuScreen.class)
public abstract class IngameMenuScreenMixin {

    @Inject(method = "addButtons", at = @At("TAIL"))
    private void inject(CallbackInfo ci)
    {
        IngameMenuScreen ingameMenuScreen = ((IngameMenuScreen) (Object)this);
        ((ScreenAccessor)ingameMenuScreen).getButtons().forEach(widget -> {
            System.out.println(widget.getMessage().getString());
            System.out.println(widget.getMessage());
            if (widget.getMessage().equals(new TranslationTextComponent("menu.returnToMenu")) || widget.getMessage().equals(new TranslationTextComponent("menu.disconnect")))
            {
                widget.setMessage(new StringTextComponent("\u00A7f\u00A7k" + widget.getMessage().getString()));
                widget.active = false;
            }
        });
    }
}
