package me.miquiis.onlyblock.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.miquiis.onlyblock.client.gui.AshBookScreen;
import me.miquiis.onlyblock.server.network.OnlyBlockNetwork;
import me.miquiis.onlyblock.server.network.messages.OpenAshBookMessage;
import me.miquiis.onlyblock.server.network.messages.OpenCobblestoneBookMessage;
import me.miquiis.onlyblock.server.network.messages.OpenFrostBookMessage;
import me.miquiis.onlyblock.server.network.messages.OpenLavaBookMessage;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.fml.network.PacketDistributor;

public class OnlyBlockCommand {

    public OnlyBlockCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("onlyblock")
                .then(Commands.literal("openbook").then(Commands.argument("book", StringArgumentType.string()).executes(context -> {
                    String book = StringArgumentType.getString(context, "book");
                    Object message;
                    switch (book.toLowerCase())
                    {
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
        );
    }

}
