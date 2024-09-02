package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.InfoProvider;
import io.github.sjouwer.gammautils.util.LightLevelUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class NightVisionManager {
    private static final ModConfig.NightVisionSettings config = GammaUtils.getConfig().nightVision;
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static Timer transitionTimer = null;
    private static double dynamicNightVisionTarget = Double.NaN;

    private NightVisionManager() {
    }

    public static int getNightVisionPercentage() {
        return (int)Math.round(config.getStrength());
    }

    public static void toggleNightVision() {
        if (config.isEnabled()) {
            if (config.isDynamicNightVisionEnabled()) {
                InfoProvider.showDynamicNightVisionHudMessage(false);
                NightVisionManager.setNightVision(0, true, true, false, true);
            }
            else {
                NightVisionManager.setNightVision(0, true, true, true);
            }
        }
        else {
            NightVisionManager.setNightVision(0, false, false, false);
            enableNightVision(true);
            if (config.isDynamicNightVisionEnabled()) {
                InfoProvider.showDynamicNightVisionHudMessage(true);
                dynamicNightVisionTarget = Double.NaN;
                setDynamicNightVision();
            }
            else {
                NightVisionManager.setNightVision(config.getToggledStrength(), true, false, true);
            }
        }
    }

    private static void enableNightVision(boolean status) {
        config.setStatus(status);
        StatusEffectManager.updateNightVision();
    }

    public static void increaseNightVision(int value) {
        double newValue = config.getStrength();
        newValue += value == 0 ? config.getStepStrength() : value;
        setNightVision(newValue, false, false, true);
    }

    public static void decreaseNightVision(int value) {
        double newValue = config.getStrength();
        newValue -= value == 0 ? config.getStepStrength() : value;
        setNightVision(newValue, false, false, true);
    }

    public static void setDimensionPreference() {
        if (client.world == null || !config.isDimensionPreferenceEnabled()) {
            return;
        }

        RegistryKey<World> dimension = client.world.getRegistryKey();
        if (dimension.equals(World.OVERWORLD)) {
            setNightVision(config.getOverworldPreference(), false, false, false);
        }
        else if (dimension.equals(World.NETHER)) {
            setNightVision(config.getNetherPreference(), false, false, false);
        }
        else if (dimension.equals(World.END)) {
            setNightVision(config.getEndPreference(), false, false, false);
        }
    }

    public static void setDynamicNightVision() {
        if (!config.isDynamicNightVisionEnabled()) {
            return;
        }

        double lightLevel = LightLevelUtil.getAverageLightLevel(config.getDynamicAveragingLightRange());
        double step = (config.getMaxDynamicStrength() - config.getMinDynamicStrength()) / 15.0;
        double target = (config.getMinDynamicStrength() + step * (15 - lightLevel));
        if (dynamicNightVisionTarget != target) {
            dynamicNightVisionTarget = target;
            setNightVision(target, true, false, false, true);
        }
    }

    public static void setNightVision(double newValue, boolean smoothTransition, boolean disable, boolean showMessage) {
        setNightVision(newValue, smoothTransition, disable, showMessage, false);
    }

    public static void setNightVision(double newValue, boolean smoothTransition, boolean disable, boolean showMessage, boolean dynamic) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (config.isLimiterEnabled() && config.getMaximumStrength() > config.getMinimumStrength()) {
            newValue = Math.clamp(newValue, config.getMinimumStrength(), config.getMaximumStrength());
        }

        if (smoothTransition && (config.isSmoothTransitionEnabled() || dynamic)) {
            double valueChangePerTick = config.getTransitionSpeed(dynamic) / 100;
            if (newValue < config.getStrength()) {
                valueChangePerTick *= -1;
            }
            startTransitionTimer(newValue, valueChangePerTick, disable, showMessage);
        }
        else {
            config.setStrength(newValue);
            if (disable) {
                enableNightVision(false);
            }
            if (showMessage) {
                InfoProvider.showNightVisionStatusHudMessage();
            }
        }

        if (config.isToggleUpdateEnabled() && newValue != 0) {
            config.setToggledStrength((int)Math.round(newValue));
        }
    }

    private static void startTransitionTimer(double newValue, double valueChangePerTick, boolean disable, boolean showMessage) {
        transitionTimer = new Timer();
        transitionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double nextValue = config.getStrength() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    transitionTimer.cancel();
                    config.setStrength(newValue);
                    if (disable) {
                        enableNightVision(false);
                    }
                }
                else {
                    config.setStrength(nextValue);
                }

                if (showMessage) {
                    InfoProvider.showNightVisionStatusHudMessage();
                }
            }
        }, 0, 10);
    }
}
