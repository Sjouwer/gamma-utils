package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.util.InfoProvider;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;

import java.util.Timer;
import java.util.TimerTask;

public class GammaOptions {
    private final ModConfig config;
    private final MinecraftClient minecraft = MinecraftClient.getInstance();
    private Timer timer = null;

    public GammaOptions() {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public void toggleGamma() {
        double value = minecraft.options.gamma;
        if (value == config.getDefaultGamma()) {
            value = config.getToggledGamma();
        }
        else {
            value = config.getDefaultGamma();
        }
        setGamma(value, true);
    }

    public void increaseGamma(double value) {
        double newValue = minecraft.options.gamma;
        if (value == 0) {
            newValue += config.getGammaStep();
        }
        else {
            newValue += value;
        }
        setGamma(newValue, false);
    }

    public void decreaseGamma(double value) {
        double newValue = minecraft.options.gamma;
        if (value == 0) {
            newValue -= config.getGammaStep();
        }
        else {
            newValue -= value;
        }
        setGamma(newValue, false);
    }

    public void minGamma() {
        setGamma(config.getMinGamma(), true);
    }

    public void maxGamma() {
        setGamma(config.getMaxGamma(), true);
    }

    public void setGamma(double newValue, boolean smoothTransition) {
        if (timer != null) {
            timer.cancel();
        }

        if (config.getMaxGamma() > config.getMinGamma() && config.limitCheckEnabled()) {
            newValue = Math.max(config.getMinGamma(), Math.min(newValue, config.getMaxGamma()));
        }

        if (smoothTransition && config.smoothTransitionEnabled()) {
            double valueChangePerTick = config.getTransitionSpeed() / 100;
            if (newValue < minecraft.options.gamma) {
                valueChangePerTick *= -1;
            }
            startTimer(newValue, valueChangePerTick);
        }
        else {
            minecraft.options.gamma = newValue;
            InfoProvider.updateStatusEffect();
            InfoProvider.showHudMessage();
        }

        if (config.updateToggleEnabled() && newValue != config.getDefaultGamma() && newValue != config.getToggledGamma()) {
            config.setToggledGamma(newValue);
        }
    }

    private void startTimer(double newValue, double valueChangePerTick) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double nextValue = minecraft.options.gamma + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    timer.cancel();
                    minecraft.options.gamma = newValue;
                    InfoProvider.updateStatusEffect();
                }
                else {
                    minecraft.options.gamma = nextValue;
                }
                InfoProvider.showHudMessage();
            }
        }, 0, 10);
    }
}
