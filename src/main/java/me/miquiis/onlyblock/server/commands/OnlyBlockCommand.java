package me.miquiis.onlyblock.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class OnlyBlockCommand {

    public OnlyBlockCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("onlyblock")
                .then(Commands.literal("day"))
                .then(Commands.literal("money")
                        .then(Commands.literal("add").then(Commands.argument("amount", IntegerArgumentType.integer()).executes(context -> {
                            OnlyMoneyBlock.getCapability(context.getSource().asPlayer()).sumBankAccount(IntegerArgumentType.getInteger(context, "amount"));
                            return 1;
                        })))
                        .then(Commands.literal("remove").then(Commands.argument("amount", IntegerArgumentType.integer()).executes(context -> {
                            OnlyMoneyBlock.getCapability(context.getSource().asPlayer()).sumBankAccount(-IntegerArgumentType.getInteger(context, "amount"));
                            return 1;
                        })))
                        .then(Commands.literal("set").then(Commands.argument("amount", IntegerArgumentType.integer()).executes(context -> {
                            OnlyMoneyBlock.getCapability(context.getSource().asPlayer()).setBankAccount(IntegerArgumentType.getInteger(context, "amount"));
                            return 1;
                        })))
                )
        );
    }

}
