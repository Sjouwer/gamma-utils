package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class GammaManager {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final SimpleOption<Double> gamma = client.options.getGamma();
    private static final ModConfig.GammaSettings config = GammaUtils.getConfig().gamma;
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
        double newValue = gamma.getValue() == config.getDefaultStrength() ? config.getToggledStrength() : config.getDefaultStrength();
        setGamma(newValue, true, true);
    }

    public static void increaseGamma(double value) {
        double newValue = gamma.getValue();
        newValue += value == 0 ? config.getStepStrength() : value;
        setGamma(newValue, false, true);
    }

    public static void decreaseGamma(double value) {
        double newValue = gamma.getValue();
        newValue -= value == 0 ? config.getStepStrength() : value;
        setGamma(newValue, false, true);
    }

    public static void minGamma() {
        setGamma(config.getMinimumStrength(), true, true);
    }

    public static void maxGamma() {
        setGamma(config.getMaximumStrength(), true, true);
    }

    public static void setDimensionPreference() {
        if (client.world == null || !config.isDimensionPreferenceEnabled()) {
            return;
        }

        RegistryKey<World> dimension = client.world.getRegistryKey();
        if (dimension.equals(World.OVERWORLD)) {
            setGamma(config.getOverworldPreference(), false, false);
        }
        else if (dimension.equals(World.NETHER)) {
            setGamma(config.getNetherPreference(), false, false);
        }
        else if (dimension.equals(World.END)) {
            setGamma(config.getEndPreference(), false, false);
        }
    }

    public static void setGamma(double newValue, boolean smoothTransition, boolean showMessage) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (config.isLimiterEnabled() && config.getMaximumStrength() > config.getMinimumStrength()) {
            newValue = Math.clamp(newValue, config.getMinimumStrength(), config.getMaximumStrength());
        }

        if (smoothTransition && config.isSmoothTransitionEnabled()) {
            double valueChangePerTick = config.getTransitionSpeed() / 100;
            if (newValue < gamma.getValue()) {
                valueChangePerTick *= -1;
            }
            startTransitionTimer(newValue, valueChangePerTick, showMessage);
        }
        else {
            gamma.setValue(newValue);
            StatusEffectManager.updateGammaStatusEffect();
            if (showMessage) {
                InfoProvider.showGammaHudMessage();
            }
        }

        if (config.isToggleUpdateEnabled() && newValue != config.getDefaultStrength() && newValue != config.getToggledStrength()) {
            config.setToggledStrength(newValue);
        }
    }

    private static void startTransitionTimer(double newValue, double valueChangePerTick, boolean showMessage) {
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

                if (showMessage) {
                    InfoProvider.showGammaHudMessage();
                }
            }
        }, 0, 10);
    }
}
