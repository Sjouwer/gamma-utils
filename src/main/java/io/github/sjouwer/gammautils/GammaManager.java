package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.InfoProvider;
import io.github.sjouwer.gammautils.util.LightLevelUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class GammaManager {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final ModConfig.GammaSettings gamma = GammaUtils.getConfig().gamma;
    private static Timer transitionTimer = null;
    private static double dynamicGammaTarget = Double.NaN;

    private GammaManager() {
    }

    public static double getGamma() {
        return gamma.getValue();
    }

    public static int getGammaPercentage() {
        return (int)Math.round(gamma.getValue() * 100);
    }

    public static void toggleGamma() {
        if (gamma.isDynamicEnabled()) {
            toggleDynamicPause();
            return;
        }

        double newValue = gamma.getValue() == gamma.getDefaultValue() ? gamma.getToggledValue() : gamma.getDefaultValue();
        dynamicGammaTarget = Double.NaN;
        setGamma(newValue, true, true);
    }

    public static void toggleDynamicPause() {
        gamma.toggleDynamicPause();
        InfoProvider.showDynamicGammaHudMessage();
        if (gamma.isDynamicPaused()) {
            dynamicGammaTarget = Double.NaN;
            setGamma(gamma.getDefaultValue(), true, false, true);
        }
    }

    public static void increaseGamma(double value) {
        double newValue = gamma.getValue();
        newValue += value == 0 ? gamma.getStepValue() : value;
        setGamma(newValue, false, true);
    }

    public static void decreaseGamma(double value) {
        double newValue = gamma.getValue();
        newValue -= value == 0 ? gamma.getStepValue() : value;
        setGamma(newValue, false, true);
    }

    public static void minGamma() {
        setGamma(gamma.getMinimumStrength(), true, true);
    }

    public static void maxGamma() {
        setGamma(gamma.getMaximumStrength(), true, true);
    }

    public static void setDimensionPreference() {
        if (client.world == null || !gamma.isDimensionPreferenceEnabled()) {
            return;
        }

        RegistryKey<World> dimension = client.world.getRegistryKey();
        if (dimension.equals(World.OVERWORLD)) {
            setGamma(gamma.getOverworldPreference(), false, false);
        }
        else if (dimension.equals(World.NETHER)) {
            setGamma(gamma.getNetherPreference(), false, false);
        }
        else if (dimension.equals(World.END)) {
            setGamma(gamma.getEndPreference(), false, false);
        }
    }

    public static void setDynamicGamma() {
        if (!gamma.isDynamicEnabled() || gamma.isDynamicPaused()) {
            return;
        }

        double lightLevel = LightLevelUtil.getAverageLightLevel(gamma.getDynamicAveragingLightRange(), gamma.getSkyBrightnessOverride());
        double step = (gamma.getMaxDynamicStrength() - gamma.getMinDynamicStrength()) / 15.0;
        double target = (gamma.getMinDynamicStrength() + step * (15 - lightLevel));
        if (dynamicGammaTarget != target) {
            dynamicGammaTarget = target;
            setGamma(target, true, false, true);
        }
    }

    public static void setGamma(double newValue, boolean smoothTransition, boolean showMessage) {
        if (gamma.isDynamicEnabled()) {
            if (showMessage) {
                Text message = Text.translatable("text.gammautils.message.incompatibleWithDynamicGamma");
                InfoProvider.sendMessage(message);
            }
            return;
        }

        setGamma(newValue, smoothTransition, showMessage, false);
    }

    private static void setGamma(double newValue, boolean smoothTransition, boolean showMessage, boolean dynamic) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (gamma.isLimiterEnabled() && gamma.getMaximumStrength() > gamma.getMinimumStrength()) {
            newValue = Math.clamp(newValue, gamma.getMinimumStrength(), gamma.getMaximumStrength());
        }

        if (smoothTransition && (gamma.isSmoothTransitionEnabled() || dynamic)) {
            double valueChangePerTick = gamma.getTransitionSpeed(dynamic) / 100;
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

        if (gamma.isToggleUpdateEnabled() && newValue != gamma.getDefaultValue()) {
            gamma.setToggledValue(newValue);
        }
    }

    protected static void toggleDynamicGamma() {
        boolean newStatus = !gamma.isDynamicEnabled();
        gamma.setDynamicStatus(newStatus);
        Text message = Text.translatable("text.gammautils.message.dynamicGamma" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    protected static void toggleStatusEffect() {
        boolean newStatus = !gamma.isStatusEffectEnabled();
        gamma.setStatusEffectStatus(newStatus);
        StatusEffectManager.updateGammaStatusEffect();
        Text message = Text.translatable("text.gammautils.message.statusEffectGamma" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    protected static void toggleSmoothTransition() {
        boolean newStatus = !gamma.isSmoothTransitionEnabled();
        gamma.setSmoothTransitionStatus(newStatus);
        Text message = Text.translatable("text.gammautils.message.transitionGamma" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
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
                }
                else {
                    gamma.setValue(nextValue);
                }

                StatusEffectManager.updateGammaStatusEffect();
                if (showMessage) {
                    InfoProvider.showGammaHudMessage();
                }
            }
        }, 0, 10);
    }
}
