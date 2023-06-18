package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;

import java.util.Timer;
import java.util.TimerTask;

public class GammaOptions {
    private static final SimpleOption<Double> gamma = MinecraftClient.getInstance().options.getGamma();
    private static final ModConfig config = GammaUtils.getConfig();
    private static Timer timer = null;

    private GammaOptions() {
    }

    public static double getGamma() {
        return gamma.getValue();
    }

    public static int getGammaPercentage() {
        return (int)Math.round(gamma.getValue() * 100);
    }

    public static void toggleGamma() {
        double value = gamma.getValue();
        if (value == config.getDefaultGamma()) {
            value = config.getToggledGamma();
        }
        else {
            value = config.getDefaultGamma();
        }
        setGamma(value, true);
    }

    public static void increaseGamma(double value) {
        double newValue = gamma.getValue();
        if (value == 0) {
            newValue += config.getGammaStep();
        }
        else {
            newValue += value;
        }
        setGamma(newValue, false);
    }

    public static void decreaseGamma(double value) {
        double newValue = gamma.getValue();
        if (value == 0) {
            newValue -= config.getGammaStep();
        }
        else {
            newValue -= value;
        }
        setGamma(newValue, false);
    }

    public static void minGamma() {
        setGamma(config.getMinGamma(), true);
    }

    public static void maxGamma() {
        setGamma(config.getMaxGamma(), true);
    }

    public static void setGamma(double newValue, boolean smoothTransition) {
        if (timer != null) {
            timer.cancel();
        }

        if (config.getMaxGamma() > config.getMinGamma() && config.isLimitCheckEnabled()) {
            newValue = Math.max(config.getMinGamma(), Math.min(newValue, config.getMaxGamma()));
        }

        if (smoothTransition && config.isSmoothTransitionEnabled()) {
            double valueChangePerTick = config.getTransitionSpeed() / 100;
            if (newValue < gamma.getValue()) {
                valueChangePerTick *= -1;
            }
            startTimer(newValue, valueChangePerTick);
        }
        else {
            gamma.setValue(newValue);
            StatusEffectManager.updateGammaStatusEffect();
            InfoProvider.showGammaHudMessage();
        }

        if (config.isUpdateToggleEnabled() && newValue != config.getDefaultGamma() && newValue != config.getToggledGamma()) {
            config.setToggledGamma(newValue);
        }
    }

    private static void startTimer(double newValue, double valueChangePerTick) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double nextValue = gamma.getValue() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    timer.cancel();
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
