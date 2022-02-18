package me.miquiis.onlyblock.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class OnlyBlockCommand {

    public OnlyBlockCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("onlyblock")
                .then(Commands.literal("recipes").executes(this::givePlayerRecipes))
                .then(Commands.literal("multiplier").then(Commands.argument("value", FloatArgumentType.floatArg()).executes(context -> {
                    float m = FloatArgumentType.getFloat(context, "value");
                    OnlyBlock.getInstance().getBlockManager().setGlobalMultiplier(m);
                    context.getSource().asPlayer().sendStatusMessage(new StringTextComponent("\u00A7a\u00A7lMultiplier set."), false);
                    return 1;
                })))
                .then(Commands.literal("money").then(Commands.argument("option", StringArgumentType.string()).then(Commands.argument("value", IntegerArgumentType.integer()).executes(context -> {
                    String option = StringArgumentType.getString(context, "option");
                    Integer value = IntegerArgumentType.getInteger(context, "value");
                    context.getSource().asPlayer().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(iCurrency -> {
                        switch (option.toLowerCase())
                        {
                            case "add":
                            {
                                iCurrency.addOrSubtractAmount(value);
                                return;
                            }
                            case "remove":
                            {
                                iCurrency.addOrSubtractAmount(-value);
                                return;
                            }
                            case "set":
                            {
                                iCurrency.setAmount(value, true);
                                return;
                            }
                        }
                    });
                    return 1;
                }))))
        );
    }

    private int givePlayerRecipes(CommandContext<CommandSource> context) throws CommandSyntaxException {

        final ServerPlayerEntity player = context.getSource().asPlayer();
        player.getRecipeBook().remove(player.getServer().getRecipeManager().getRecipes(), player);
        final List<IRecipe<?>> onlyBlockRecipes = new ArrayList<>();
        player.getServer().getRecipeManager().getRecipes().forEach(iRecipe -> {
            if (iRecipe.getId().toString().contains("onlyblock"))
            {
                onlyBlockRecipes.add(iRecipe);
            }
        });
        player.getRecipeBook().add(onlyBlockRecipes, player);
        return 1;
    }

}
