package io.github.sjouwer.gammautils;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;

import static net.fabricmc.fabric.api.client.command.v1.ClientCommandManager.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;

public class Commands {
    private final GammaOptions gammaOptions = new GammaOptions();

    public void registerCommands(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("gamma")
                .then(literal("toggle")
                        .executes(ctx -> {
                                    gammaOptions.toggleGamma();
                                    gammaOptions.saveOptions();
                                    return 1;
                                }
                        ))

                .then(literal("min")
                        .executes(ctx -> {
                                    gammaOptions.minGamma();
                                    gammaOptions.saveOptions();
                                    return 1;
                                }
                        ))

                .then(literal("max")
                        .executes(ctx -> {
                                    gammaOptions.maxGamma();
                                    gammaOptions.saveOptions();
                                    return 1;
                                }
                        ))

                .then(literal("set")
                        .then(argument("value", integer())
                                .executes(ctx -> {
                                            gammaOptions.setGamma(getInteger(ctx, "value") / 100.0);
                                            gammaOptions.saveOptions();
                                            gammaOptions.updateToggledGamma();
                                            return 1;
                                        }
                                )))

                .then(literal("increase")
                        .executes(ctx -> {
                                    gammaOptions.increaseGamma(0);
                                    gammaOptions.saveOptions();
                                    gammaOptions.updateToggledGamma();
                                    return 1;
                                }
                        )
                        .then(argument("value", integer())
                                .executes(ctx -> {
                                            gammaOptions.increaseGamma(getInteger(ctx, "value") / 100.0);
                                            gammaOptions.saveOptions();
                                            gammaOptions.updateToggledGamma();
                                            return 1;
                                        }
                                )))

                .then(literal("decrease")
                        .executes(ctx -> {
                                    gammaOptions.decreaseGamma(0);
                                    gammaOptions.saveOptions();
                                    gammaOptions.updateToggledGamma();
                                    return 1;
                                }
                        )
                        .then(argument("value", integer())
                                .executes(ctx -> {
                                            gammaOptions.decreaseGamma(getInteger(ctx, "value") / 100.0);
                                            gammaOptions.saveOptions();
                                            gammaOptions.updateToggledGamma();
                                            return 1;
                                        }
                                ))));
    }
}
