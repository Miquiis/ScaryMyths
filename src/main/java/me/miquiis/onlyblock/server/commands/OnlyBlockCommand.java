package me.miquiis.onlyblock.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.client.gui.AshBookScreen;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.*;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class OnlyBlockCommand {

    public OnlyBlockCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("onlyblock")
                .then(Commands.literal("openbook").then(Commands.argument("book", StringArgumentType.string()).executes(context -> {
                    String book = StringArgumentType.getString(context, "book");
                    Object message;
                    switch (book.toLowerCase())
                    {
                        case "craftbook":
                        {
                            message = new OpenCraftBookMessage();
                            break;
                        }
                        case "frost":
                        {
                            message = new OpenFrostBookMessage();
                            break;
                        }
                        case "lava":
                        {
                            message = new OpenLavaBookMessage();
                            break;
                        }
                        case "ash":
                        {
                            message = new OpenAshBookMessage();
                            break;
                        }
                        default:
                        {
                            message = new OpenCobblestoneBookMessage();
                            break;
                        }
                    }
                    OnlyBlockNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> {
                        try {
                            return context.getSource().asPlayer();
                        } catch (CommandSyntaxException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }), message);
                    return 1;
                })))
                .then(Commands.literal("recipes").executes(this::givePlayerRecipes))
                .then(Commands.literal("multiplier").then(Commands.argument("value", FloatArgumentType.floatArg()).executes(context -> {
                    float m = FloatArgumentType.getFloat(context, "value");
                    OnlyBlock.getInstance().getBlockManager().setGlobalMultiplier(m);
                    context.getSource().asPlayer().sendStatusMessage(new StringTextComponent("\u00A7a\u00A7lMultiplier set."), false);
                    return 1;
                })))
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
