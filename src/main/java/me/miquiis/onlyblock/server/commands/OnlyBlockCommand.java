package me.miquiis.onlyblock.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.miquiis.onlyblock.common.capability.models.OnlyMoneyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyMoneyBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class OnlyBlockCommand {

    public OnlyBlockCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("onlyblock")
                .then(Commands.literal("day")
                        .then(Commands.literal("next").executes(context -> {
                            context.getSource().getWorld().getPlayers().forEach(player -> {
                                OnlyMoneyBlock.getCapability(player).sumDays(-1);
                            });
                            context.getSource().getWorld().setDayTime(1);
                            return 1;
                        }))
                        .then(Commands.literal("set").then(Commands.argument("days", IntegerArgumentType.integer()).executes(context -> {
                            OnlyMoneyBlock.getCapability(context.getSource().asPlayer()).setDays(IntegerArgumentType.getInteger(context, "days"));
                            return 1;
                        })))
                        .then(Commands.literal("reset").executes(context -> {
                            context.getSource().getWorld().getPlayers().forEach(player -> {
                                OnlyMoneyBlock.getCapability(player).setDays(100);
                            });
                            context.getSource().getWorld().setDayTime(1);
                            return 1;
                        }))
                )
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
                .then(Commands.literal("reset")
                        .executes(context -> {
                            WorldOnlyMoneyBlock.getCapability(context.getSource().getWorld()).reset();
                            context.getSource().getWorld().getPlayers().forEach(player -> {
                                OnlyMoneyBlock.getCapability(player).reset();
                            });
                            return 1;
                        })
                )
        );
    }

}
