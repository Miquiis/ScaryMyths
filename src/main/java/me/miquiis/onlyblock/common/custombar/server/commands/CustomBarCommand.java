package me.miquiis.onlyblock.common.custombar.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.miquiis.onlyblock.common.custombar.common.BarManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class CustomBarCommand {

    public CustomBarCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(
                Commands.literal("custombar")
                        .then(Commands.literal("add").then(Commands.argument("name", StringArgumentType.string()).executes(context -> {
                            return addCustomBar(new StringTextComponent(StringArgumentType.getString(context, "name")));
                        }).then(Commands.argument("percent", FloatArgumentType.floatArg()).executes(context -> {
                            return addCustomBar(new StringTextComponent(StringArgumentType.getString(context, "name")), FloatArgumentType.getFloat(context, "percent"));
                        }))))
                        .then(Commands.literal("remove").then(Commands.argument("name", StringArgumentType.string()).executes(context -> {
                            return removeCustomBar(StringArgumentType.getString(context, "name"));
                        })))
                        .then(Commands.literal("clear").executes(context -> {
                            return clearBars();
                        }))
                        .then(Commands.literal("update").then(Commands.argument("name", StringArgumentType.string()).then(Commands.argument("percent", FloatArgumentType.floatArg()).executes(context -> {
                            return updateBar(StringArgumentType.getString(context, "name"), FloatArgumentType.getFloat(context, "percent"));
                        }))))
        );
    }

    private int updateBar(String text, float percent) {
        BarManager.updateBar(text, percent);
        return 1;
    }

    private int addCustomBar(ITextComponent text, float percent)
    {
        BarManager.addBar(text, percent);
        return 1;
    }

    private int addCustomBar(ITextComponent text)
    {
        return addCustomBar(text, 0f);
    }

    private int removeCustomBar(String name)
    {
        BarManager.removeBar(name);
        return 1;
    }

    private int clearBars()
    {
        BarManager.clearBars();
        return 1;
    }

}
