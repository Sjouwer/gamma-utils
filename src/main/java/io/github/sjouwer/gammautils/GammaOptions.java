package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.minecraft.client.MinecraftClient;

import java.util.Timer;
import java.util.TimerTask;

public class GammaOptions {
    private final MinecraftClient client = MinecraftClient.getInstance();
    private final ModConfig config = GammaUtils.getConfig();
    private Timer timer = null;

    public void toggleGamma() {
        double value = client.options.getGamma().getValue();
        if (value == config.getDefaultGamma()) {
            value = config.getToggledGamma();
        }
        else {
            value = config.getDefaultGamma();
        }
        setGamma(value, true);
    }

    public void increaseGamma(double value) {
        double newValue = client.options.getGamma().getValue();
        if (value == 0) {
            newValue += config.getGammaStep();
        }
        else {
            newValue += value;
        }
        setGamma(newValue, false);
    }

    public void decreaseGamma(double value) {
        double newValue = client.options.getGamma().getValue();
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
            if (newValue < client.options.getGamma().getValue()) {
                valueChangePerTick *= -1;
            }
            startTimer(newValue, valueChangePerTick);
        }
        else {
            client.options.getGamma().setValue(newValue);
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
                double nextValue = client.options.getGamma().getValue() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    timer.cancel();
                    client.options.getGamma().setValue(newValue);
                    InfoProvider.updateStatusEffect();
                }
                else {
                    client.options.getGamma().setValue(nextValue);
                }
                InfoProvider.showHudMessage();
            }
        }, 0, 10);
    }
}
