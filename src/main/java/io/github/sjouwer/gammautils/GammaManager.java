package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;

import java.util.Timer;
import java.util.TimerTask;

public class GammaManager {
    private static final SimpleOption<Double> gamma = MinecraftClient.getInstance().options.getGamma();
    private static final ModConfig config = GammaUtils.getConfig();
    private static Timer transitionTimer = null;

    private GammaManager() {
    }

    public static double getGamma() {
        return gamma.getValue();
    }

    public static int getGammaPercentage() {
        return (int)Math.round(gamma.getValue() * 100);
    }

    public static void toggleGamma() {
        double newValue = gamma.getValue() == config.getDefaultGamma() ? config.getToggledGamma() : config.getDefaultGamma();
        setGamma(newValue, true);
    }

    public static void increaseGamma(double value) {
        double newValue = gamma.getValue();
        newValue += value == 0 ? config.getGammaStep() : value;
        setGamma(newValue, false);
    }

    public static void decreaseGamma(double value) {
        double newValue = gamma.getValue();
        newValue -= value == 0 ? config.getGammaStep() : value;
        setGamma(newValue, false);
    }

    public static void minGamma() {
        setGamma(config.getMinGamma(), true);
    }

    public static void maxGamma() {
        setGamma(config.getMaxGamma(), true);
    }

    public static void setGamma(double newValue, boolean smoothTransition) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (config.isGammaLimiterEnabled() && config.getMaxGamma() > config.getMinGamma()) {
            newValue = Math.clamp(newValue, config.getMinGamma(), config.getMaxGamma());
        }

        if (smoothTransition && config.isSmoothGammaEnabled()) {
            double valueChangePerTick = config.getGammaTransitionSpeed() / 100;
            if (newValue < gamma.getValue()) {
                valueChangePerTick *= -1;
            }
            startTransitionTimer(newValue, valueChangePerTick);
        }
        else {
            gamma.setValue(newValue);
            StatusEffectManager.updateGammaStatusEffect();
            InfoProvider.showGammaHudMessage();
        }

        if (config.isGammaToggleUpdateEnabled() && newValue != config.getDefaultGamma() && newValue != config.getToggledGamma()) {
            config.setToggledGamma(newValue);
        }
    }

    private static void startTransitionTimer(double newValue, double valueChangePerTick) {
        transitionTimer = new Timer();
        transitionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double nextValue = gamma.getValue() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    transitionTimer.cancel();
                    gamma.setValue(newValue);
                    StatusEffectManager.updateGammaStatusEffect();
                }
                else {
                    gamma.setValue(nextValue);
                }
                InfoProvider.showGammaHudMessage();
            }
        }, 0, 10);
    }
}
