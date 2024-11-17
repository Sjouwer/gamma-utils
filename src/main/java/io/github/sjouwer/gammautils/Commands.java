package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;

public class Commands {
    private static final ModConfig config = GammaUtils.getConfig();

    private Commands() {
    }

    public static void registerCommands() {
        registerGammaCommands();
        registerNightVisionCommands();
    }

    public static void registerGammaCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(literal(config.other.namespacedCommandsEnabled() ? (GammaUtils.NAMESPACE + ":gamma") : "gamma")
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
                        })))
                .then(literal("dynamic")
                    .executes(ctx -> {
                        GammaManager.toggleDynamicGamma();
                        return 1;
                    })
                    .then(literal("toggle")
                        .executes(ctx -> {
                            GammaManager.toggleDynamicGamma();
                            return 1;
                        }))
                    .then(literal("enable")
                        .executes(ctx -> {
                            config.gamma.setDynamicGammaStatus(true);
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.dynamicGammaOn"));
                            return 1;
                        }))
                    .then(literal("disable")
                        .executes(ctx -> {
                            config.gamma.setDynamicGammaStatus(false);
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.dynamicGammaOff"));
                            return 1;
                        })))
                .then(literal("statuseffect")
                    .executes(ctx -> {
                        GammaManager.toggleStatusEffect();
                        return 1;
                    })
                    .then(literal("toggle")
                        .executes(ctx -> {
                            GammaManager.toggleStatusEffect();
                            return 1;
                        }))
                    .then(literal("enable")
                        .executes(ctx -> {
                            config.gamma.setStatusEffectStatus(true);
                            StatusEffectManager.updateGammaStatusEffect();
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.statusEffectGammaOn"));
                            return 1;
                        }))
                    .then(literal("disable")
                        .executes(ctx -> {
                            config.gamma.setStatusEffectStatus(false);
                            StatusEffectManager.updateGammaStatusEffect();
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.statusEffectGammaOff"));
                            return 1;
                        })))
                .then(literal("transition")
                    .executes(ctx -> {
                        GammaManager.toggleSmoothTransition();
                        return 1;
                    })
                    .then(literal("toggle")
                        .executes(ctx -> {
                            GammaManager.toggleSmoothTransition();
                            return 1;
                        }))
                    .then(literal("smooth")
                        .executes(ctx -> {
                            config.gamma.setSmoothTransitionStatus(true);
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.transitionGammaOn"));
                            return 1;
                        }))
                    .then(literal("none")
                        .executes(ctx -> {
                            config.gamma.setSmoothTransitionStatus(false);
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.transitionGammaOff"));
                            return 1;
                        })))));
    }

    public static void registerNightVisionCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(literal(config.other.namespacedCommandsEnabled() ? (GammaUtils.NAMESPACE + ":nightvision") : "nightvision")
                .executes(ctx -> {
                    NightVisionManager.toggleNightVision();
                    return 1;
                })
                .then(literal("enable")
                    .executes(ctx -> {
                        NightVisionManager.enableNightVision();
                        return 1;
                    }))
                .then(literal("disable")
                    .executes(ctx -> {
                        NightVisionManager.disableNightVision();
                        return 1;
                    }))
                .then(argument("value", integer())
                    .executes(ctx -> {
                        NightVisionManager.enableAndOrSetNightVision(getInteger(ctx, "value"));
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
                            NightVisionManager.enableAndOrSetNightVision(getInteger(ctx, "value"));
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
                        })))
                .then(literal("dynamic")
                    .executes(ctx -> {
                        NightVisionManager.toggleDynamicNightVision();
                        return 1;
                    })
                    .then(literal("toggle")
                        .executes(ctx -> {
                            NightVisionManager.toggleDynamicNightVision();
                            return 1;
                        }))
                    .then(literal("enable")
                        .executes(ctx -> {
                            config.nightVision.setDynamicNightVisionStatus(true);
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.dynamicNightVisionOn"));
                            return 1;
                        }))
                    .then(literal("disable")
                        .executes(ctx -> {
                            config.nightVision.setDynamicNightVisionStatus(false);
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.dynamicNightVisionOff"));
                            return 1;
                        })))
                .then(literal("statuseffect")
                    .executes(ctx -> {
                        NightVisionManager.toggleStatusEffect();
                        return 1;
                    })
                    .then(literal("toggle")
                        .executes(ctx -> {
                            NightVisionManager.toggleStatusEffect();
                            return 1;
                        }))
                    .then(literal("enable")
                        .executes(ctx -> {
                            config.nightVision.setStatusEffectStatus(true);
                            StatusEffectManager.updateNightVision();
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.statusEffectNightVisionOn"));
                            return 1;
                        }))
                    .then(literal("disable")
                        .executes(ctx -> {
                            config.nightVision.setStatusEffectStatus(false);
                            StatusEffectManager.updateNightVision();
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.statusEffectNightVisionOff"));
                            return 1;
                        })))
                .then(literal("transition")
                    .executes(ctx -> {
                        NightVisionManager.toggleSmoothTransition();
                        return 1;
                    })
                    .then(literal("toggle")
                        .executes(ctx -> {
                            NightVisionManager.toggleSmoothTransition();
                            return 1;
                        }))
                    .then(literal("smooth")
                        .executes(ctx -> {
                            config.nightVision.setSmoothTransitionStatus(true);
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.transitionNightVisionOn"));
                            return 1;
                        }))
                    .then(literal("none")
                        .executes(ctx -> {
                            config.nightVision.setSmoothTransitionStatus(false);
                            InfoProvider.sendMessage(Text.translatable("text.gammautils.message.transitionNightVisionOff"));
                            return 1;
                        })))));
    }
}
