package me.miquiis.onlyblock.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.quests.BreakHundredBlocksQuest;
import me.miquiis.onlyblock.common.utils.WorldEditUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.test.TestFunctionInfo;
import net.minecraft.test.TestRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

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
                .then(Commands.literal("islands").then(Commands.argument("option", StringArgumentType.string()).suggests(this::listIslandOptions).then(Commands.argument("island", StringArgumentType.string()).suggests(this::listIslands).executes(context -> {
                    String option = StringArgumentType.getString(context, "option");
                    String island = StringArgumentType.getString(context, "island");

                    if (option.equals("lock"))
                    {
                        switch (island)
                        {
                            case "stocks":
                            {
                                WorldEditUtils.pasteSchematic("b_stock_no_entity", context.getSource().getWorld(), -7.51, 66, -122.44);
                                break;
                            }
                            case "amazon":
                            {
                                break;
                            }
                            case "rocket":
                            {
                                WorldEditUtils.pasteSchematic("b_rocket_no_entity", context.getSource().getWorld(), 1.51, 65.00, 130.47);
                                break;
                            }
                        }
                    } else
                    {
                        switch (island)
                        {
                            case "stocks":
                            {
                                WorldEditUtils.pasteSchematic("stock_no_entity", context.getSource().getWorld(), -7.51, 66, -122.44);
                                break;
                            }
                            case "amazon":
                            {
                                break;
                            }
                            case "rocket":
                            {
                                WorldEditUtils.pasteSchematic("rocket_no_entity", context.getSource().getWorld(), 1.51, 65.00, 130.47);
                                break;
                            }
                        }
                    }

                    return 1;
                }))))
                .then(Commands.literal("debug").executes(context -> {
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).getAmazonIsland().startMinigame(context.getSource().getWorld());
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).sync(context.getSource().asPlayer());
                    return 1;
                }))
                .then(Commands.literal("reset").executes(context -> {
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).getAmazonIsland().reset();
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).setCurrentQuest(new BreakHundredBlocksQuest());
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).sync(context.getSource().asPlayer());
                    return 1;
                }))
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

    private <S> CompletableFuture<Suggestions> listIslandOptions(CommandContext<S> p_listSuggestions_1_, SuggestionsBuilder p_listSuggestions_2_) {
        Stream<String> stream = new ArrayList<String>(Arrays.asList("unlock", "lock")).stream();
        return ISuggestionProvider.suggest(stream, p_listSuggestions_2_);
    }

    private <S> CompletableFuture<Suggestions> listIslands(CommandContext<S> p_listSuggestions_1_, SuggestionsBuilder p_listSuggestions_2_) {
        Stream<String> stream = new ArrayList<String>(Arrays.asList("amazon", "stocks", "rocket")).stream();
        return ISuggestionProvider.suggest(stream, p_listSuggestions_2_);
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
