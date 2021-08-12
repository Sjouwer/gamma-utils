package io.github.sjouwer.gammautils;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.getInteger;

public class Commands {
    private final GammaOptions gammaOptions = new GammaOptions();

    public void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("gamma")
                .then(literal("toggle")
                        .executes(ctx -> {
                                    gammaOptions.toggleGamma();
                                    return 1;
                                }
                        ))

                .then(literal("min")
                        .executes(ctx -> {
                                    gammaOptions.minGamma();
                                    return 1;
                                }
                        ))

                .then(literal("max")
                        .executes(ctx -> {
                                    gammaOptions.maxGamma();
                                    return 1;
                                }
                        ))

                .then(literal("set")
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> {
                                            gammaOptions.setGamma(getInteger(ctx, "value") / 100.0);
                                            return 1;
                                        }
                                )))

                .then(literal("increase")
                        .executes(ctx -> {
                                    gammaOptions.increaseGamma(0);
                                    return 1;
                                }
                        )
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> {
                                            gammaOptions.increaseGamma(getInteger(ctx, "value") / 100.0);
                                            return 1;
                                        }
                                )))

                .then(literal("decrease")
                        .executes(ctx -> {
                                    gammaOptions.decreaseGamma(0);
                                    return 1;
                                }
                        )
                        .then(argument("value", IntegerArgumentType.integer())
                                .executes(ctx -> {
                                            gammaOptions.decreaseGamma(getInteger(ctx, "value") / 100.0);
                                            return 1;
                                        }
                                ))));
    }
}
