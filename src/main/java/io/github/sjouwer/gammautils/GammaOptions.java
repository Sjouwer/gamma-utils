package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.ISimpleOption;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.minecraft.client.MinecraftClient;

import java.util.Timer;
import java.util.TimerTask;

public class GammaOptions {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();
    private static Timer timer = null;

    private GammaOptions() {
    }

    public static void toggleGamma() {
        double value = client.options.getGamma().getValue();
        if (value == config.getDefaultGamma()) {
            value = config.getToggledGamma();
        }
        else {
            value = config.getDefaultGamma();
        }
        setGamma(value, true);
    }

    public static void increaseGamma(double value) {
        double newValue = client.options.getGamma().getValue();
        if (value == 0) {
            newValue += config.getGammaStep();
        }
        else {
            newValue += value;
        }
        setGamma(newValue, false);
    }

    public static void decreaseGamma(double value) {
        double newValue = client.options.getGamma().getValue();
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
            if (newValue < client.options.getGamma().getValue()) {
                valueChangePerTick *= -1;
            }
            startTimer(newValue, valueChangePerTick);
        }
        else {
            ISimpleOption.setGamma(newValue);
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
                double nextValue = client.options.getGamma().getValue() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    timer.cancel();
                    ISimpleOption.setGamma(newValue);
                    StatusEffectManager.updateGammaStatusEffect();
                }
                else {
                    ISimpleOption.setGamma(nextValue);
                }
                InfoProvider.showGammaHudMessage();
            }
        }, 0, 10);
    }
}
