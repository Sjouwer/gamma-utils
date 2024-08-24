package io.github.sjouwer.gammautils;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;

public class Commands {
    private Commands() {
    }

    public static void registerCommands() {
        registerGammaCommands();
        registerNightVisionCommands();
    }

    public static void registerGammaCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(literal("gamma")
                    .executes(ctx -> {
                        GammaManager.toggleGamma();
                        return 1;
                    })
                    .then(argument("value", integer())
                            .executes(ctx -> {
                                GammaManager.setGamma(getInteger(ctx, "value") / 100.0, true, true);
                                return 1;
                            }))
                    .then(literal("toggle")
                        .executes(ctx -> {
                            GammaManager.toggleGamma();
                            return 1;
                        }))
                    .then(literal("min")
                        .executes(ctx -> {
                            GammaManager.minGamma();
                            return 1;
                        }))
                    .then(literal("max")
                        .executes(ctx -> {
                            GammaManager.maxGamma();
                            return 1;
                        }))
                    .then(literal("set")
                        .then(argument("value", integer())
                            .executes(ctx -> {
                                GammaManager.setGamma(getInteger(ctx, "value") / 100.0, true, true);
                                return 1;
                            })))
                    .then(literal("increase")
                        .executes(ctx -> {
                            GammaManager.increaseGamma(0);
                            return 1;
                        })
                        .then(argument("value", integer())
                            .executes(ctx -> {
                                GammaManager.increaseGamma(getInteger(ctx, "value") / 100.0);
                                return 1;
                            })))
                    .then(literal("decrease")
                        .executes(ctx -> {
                            GammaManager.decreaseGamma(0);
                            return 1;
                        })
                        .then(argument("value", integer())
                            .executes(ctx -> {
                                GammaManager.decreaseGamma(getInteger(ctx, "value") / 100.0);
                                return 1;
                            })))));
    }

    public static void registerNightVisionCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(literal("nightvision")
                    .executes(ctx -> {
                        NightVisionManager.toggleNightVision();
                        return 1;
                    })
                    .then(argument("value", integer())
                            .executes(ctx -> {
                                NightVisionManager.toggleNightVision();
                                NightVisionManager.setNightVision(getInteger(ctx, "value"), true, false, true);
                                return 1;
                            }))
                    .then(literal("toggle")
                        .executes(ctx -> {
                            NightVisionManager.toggleNightVision();
                            return 1;
                        }))
                    .then(literal("set")
                        .then(argument("value", integer())
                            .executes(ctx -> {
                                NightVisionManager.toggleNightVision();
                                NightVisionManager.setNightVision(getInteger(ctx, "value"), true, false, true);
                                return 1;
                            })))
                    .then(literal("increase")
                        .executes(ctx -> {
                            NightVisionManager.increaseNightVision(0);
                            return 1;
                        })
                        .then(argument("value", integer())
                            .executes(ctx -> {
                                NightVisionManager.increaseNightVision(getInteger(ctx, "value"));
                                    return 1;
                                })))
                    .then(literal("decrease")
                        .executes(ctx -> {
                            NightVisionManager.decreaseNightVision(0);
                            return 1;
                        })
                        .then(argument("value", integer())
                            .executes(ctx -> {
                                NightVisionManager.decreaseNightVision(getInteger(ctx, "value"));
                                    return 1;
                                })))));
    }
}
