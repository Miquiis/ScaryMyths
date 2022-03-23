package me.miquiis.onlyblock.server.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.miquiis.onlyblock.OnlyBlock;
import me.miquiis.onlyblock.common.capability.CurrencyCapability;
import me.miquiis.onlyblock.common.capability.interfaces.IOnlyBlock;
import me.miquiis.onlyblock.common.capability.models.OnlyBlockModel;
import me.miquiis.onlyblock.common.entities.AsteroidEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.Arrays;
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
                    IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(context.getSource().asPlayer());
                    if (option.equals("lock"))
                    {
                        switch (island)
                        {
                            case "stocks":
                            {
                                onlyBlock.getStockIsland().lock(context.getSource().asPlayer());
                                break;
                            }
                            case "amazon":
                            {
                                onlyBlock.getAmazonIsland().lock(context.getSource().asPlayer());
                                break;
                            }
                            case "rocket":
                            {
                                onlyBlock.getBillionaireIsland().lock(context.getSource().asPlayer());
                                break;
                            }
                        }
                    } else
                    {
                        switch (island)
                        {
                            case "stocks":
                            {
                                onlyBlock.getStockIsland().unlock(context.getSource().asPlayer());
                                break;
                            }
                            case "amazon":
                            {
                                onlyBlock.getAmazonIsland().unlock(context.getSource().asPlayer());
                                break;
                            }
                            case "rocket":
                            {
                                onlyBlock.getBillionaireIsland().unlock(context.getSource().asPlayer());
                                break;
                            }
                        }
                    }

                    return 1;
                }))))
                .then(Commands.literal("debug")
                        .then(Commands.literal("createAsteroids").then(Commands.argument("gap", DoubleArgumentType.doubleArg()).then(Commands.argument("radius", DoubleArgumentType.doubleArg()).executes(context -> {

                            final ServerPlayerEntity player = context.getSource().asPlayer();
                            final double wideAngle = 360;
                            final double gap = DoubleArgumentType.getDouble(context, "gap");
                            final double radius = DoubleArgumentType.getDouble(context, "radius");

                            for (int pitch = -90; pitch < 90; pitch+= gap)
                            {
                                for (double i = -wideAngle/2; i <= wideAngle/2; i+= gap)
                                {
                                    final double angle = i;
                                    Vector3d lookVec = getVectorForRotation(pitch, 0, (float)angle);
                                    double x = radius * lookVec.getX();
                                    double z = radius * lookVec.getZ();
                                    double y = radius * lookVec.getY();

                                    Vector3d vec = new Vector3d(player.getPosX() + x, (player.getPosY() + 2f) + y, player.getPosZ() + z);
                                    AsteroidEntity asteroidEntity = new AsteroidEntity(player.world, angle, pitch, radius, player.getPositionVec());
                                    asteroidEntity.setPosition(vec.getX(), vec.getY(), vec.getZ());
                                    player.world.addEntity(asteroidEntity);
                                }
                            }

                            return 1;
                        }))))
                        .then(Commands.literal("startMinigame").executes(context -> {
                            final ServerPlayerEntity player = context.getSource().asPlayer();
                            IOnlyBlock onlyBlock = OnlyBlockModel.getCapability(player);
                            onlyBlock.getBillionaireIsland().startMinigame(player);
                            return 1;
                        }))
                )
                .then(Commands.literal("reset").executes(context -> {
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).reset();
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).getStockIsland().lock(context.getSource().asPlayer());
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).getAmazonIsland().lock(context.getSource().asPlayer());
                    OnlyBlockModel.getCapability(context.getSource().asPlayer()).getBillionaireIsland().lock(context.getSource().asPlayer());
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

    private final Vector3d getVectorForRotation(float pitch, float yaw, float angle) {
        float f = (pitch) * ((float)Math.PI / 180F);
        float f1 = (-yaw + angle) * ((float)Math.PI / 180F);
        float f2 = MathHelper.cos(f1);
        float f3 = MathHelper.sin(f1);
        float f4 = MathHelper.cos(f);
        float f5 = MathHelper.sin(f);
        return new Vector3d((double)(f3 * f4), (double)(-f5), (double)(f2 * f4));
    }

}
