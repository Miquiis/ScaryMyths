package me.miquiis.onlyblock.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.miquiis.onlyblock.common.capability.interfaces.IWorldOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.WorldOnlyBlock;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class OnlyBlockCommand {

    public OnlyBlockCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("onlyblock")
                .then(Commands.literal("day")
                        .then(Commands.literal("next").executes(context -> {
                            IWorldOnlyBlock worldOnlyBlock = WorldOnlyBlock.getCapability(context.getSource().getWorld());
                            worldOnlyBlock.skipDay();
                            context.getSource().getWorld().setDayTime(1);
                            return 1;
                        }))
                        .then(Commands.literal("set").then(Commands.argument("days", IntegerArgumentType.integer()).executes(context -> {
                            IWorldOnlyBlock worldOnlyBlock = WorldOnlyBlock.getCapability(context.getSource().getWorld());
                            worldOnlyBlock.setDaysLeft(100 - IntegerArgumentType.getInteger(context, "days"));
                            context.getSource().getWorld().setDayTime(1);
                            return 1;
                        })))
                        .then(Commands.literal("reset").executes(context -> {
                            IWorldOnlyBlock worldOnlyBlock = WorldOnlyBlock.getCapability(context.getSource().getWorld());
                            worldOnlyBlock.reset();
                            worldOnlyBlock.sync();
                            return 1;
                        }))
                        .then(Commands.literal("count").executes(context -> {
                            IWorldOnlyBlock worldOnlyBlock = WorldOnlyBlock.getCapability(context.getSource().getWorld());
                            context.getSource().asPlayer().sendStatusMessage(new StringTextComponent(worldOnlyBlock.getCurrentDays() + " Days"), false);
                            return 1;
                        }))
                )
        );
    }

}
