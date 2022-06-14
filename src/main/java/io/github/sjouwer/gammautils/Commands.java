package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;

public class Commands {
    private Commands() {
    }

    public static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(literal("gamma")
                        .then(literal("toggle")
                                .executes(ctx -> {
                                    GammaOptions.toggleGamma();
                                    return 1;
                                }))

                        .then(literal("min")
                                .executes(ctx -> {
                                    GammaOptions.minGamma();
                                    return 1;
                                }))

                        .then(literal("max")
                                .executes(ctx -> {
                                    GammaOptions.maxGamma();
                                    return 1;
                                }))

                        .then(literal("set")
                                .then(argument("value", integer())
                                        .executes(ctx -> {
                                            GammaOptions.setGamma(getInteger(ctx, "value") / 100.0, true);
                                            return 1;
                                        })))

                        .then(literal("increase")
                                .executes(ctx -> {
                                    GammaOptions.increaseGamma(0);
                                    return 1;
                                })
                                .then(argument("value", integer())
                                        .executes(ctx -> {
                                                    GammaOptions.increaseGamma(getInteger(ctx, "value") / 100.0);
                                                    return 1;
                                                }
                                        )))

                        .then(literal("decrease")
                                .executes(ctx -> {
                                    GammaOptions.decreaseGamma(0);
                                    return 1;
                                })
                                .then(argument("value", integer())
                                        .executes(ctx -> {
                                                    GammaOptions.decreaseGamma(getInteger(ctx, "value") / 100.0);
                                                    return 1;
                                                }
                                        )))

                        .then(literal("nightvision")
                                .executes(ctx -> {
                                            StatusEffectManager.toggleNightVision();
                                            return 1;
                                        }
                                ))));
    }
}
