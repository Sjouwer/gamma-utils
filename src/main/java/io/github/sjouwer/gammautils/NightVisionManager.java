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

public class NightVisionManager {
    private static final ModConfig.NightVisionSettings nightVision = GammaUtils.getConfig().nightVision;
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static Timer transitionTimer = null;
    private static double dynamicNightVisionTarget = Double.NaN;

    private NightVisionManager() {
    }

    public static int getNightVisionPercentage() {
        return (int)Math.round(nightVision.getValue());
    }

    public static void toggleNightVision() {
        if (nightVision.isDynamicEnabled()) {
            toggleDynamicPause();
            return;
        }

        if (nightVision.isEnabled()) {
            disableNightVision();
        }
        else {
            enableNightVision();
        }
    }

    public static void toggleDynamicPause() {
        nightVision.toggleDynamicPause();
        InfoProvider.showDynamicNightVisionHudMessage();
        if (nightVision.isDynamicPaused()) {
            dynamicNightVisionTarget = Double.NaN;
            setNightVision(0, true, false, true);
        }
    }

    public static void enableAndOrSetNightVision(int newValue) {
        if (nightVision.isEnabled()) {
            NightVisionManager.setNightVision(newValue, true, true);
        }
        else {
            enableNightVision(newValue);
        }
    }

    public static void enableNightVision() {
        enableNightVision(nightVision.getToggledValue());
    }

    private static void enableNightVision(int newValue) {
        NightVisionManager.setNightVision(0, false, false);
        setNightVisionStatus(true);
        NightVisionManager.setNightVision(newValue, true, true);
    }

    public static void disableNightVision() {
        NightVisionManager.setNightVision(0, true, true);
    }

    public static void increaseNightVision(int value) {
        double newValue = nightVision.getValue();
        newValue += value == 0 ? nightVision.getStepValue() : value;
        setNightVision(newValue, false, true);
    }

    public static void decreaseNightVision(int value) {
        double newValue = nightVision.getValue();
        newValue -= value == 0 ? nightVision.getStepValue() : value;
        setNightVision(newValue, false, true);
    }

    public static void setDimensionPreference() {
        if (client.world == null || !nightVision.isDimensionPreferenceEnabled()) {
            return;
        }

        RegistryKey<World> dimension = client.world.getRegistryKey();
        if (dimension.equals(World.OVERWORLD)) {
            setNightVision(nightVision.getOverworldPreference(), false, false);
        }
        else if (dimension.equals(World.NETHER)) {
            setNightVision(nightVision.getNetherPreference(), false, false);
        }
        else if (dimension.equals(World.END)) {
            setNightVision(nightVision.getEndPreference(), false, false);
        }
    }

    public static void setDynamicNightVision() {
        if (!nightVision.isDynamicEnabled() || nightVision.isDynamicPaused()) {
            return;
        }

        double lightLevel = LightLevelUtil.getAverageLightLevel(nightVision.getDynamicAveragingLightRange(), nightVision.getSkyBrightnessOverride());
        double step = (nightVision.getMaxDynamicStrength() - nightVision.getMinDynamicStrength()) / 15.0;
        double target = (nightVision.getMinDynamicStrength() + step * (15 - lightLevel));
        if (dynamicNightVisionTarget != target) {
            dynamicNightVisionTarget = target;
            setNightVision(target, true, false, true);
        }
    }

    public static void setNightVision(double newValue, boolean smoothTransition, boolean showMessage) {
        if (nightVision.isDynamicEnabled()) {
            if (showMessage) {
                Text message = Text.translatable("text.gammautils.message.incompatibleWithDynamicNightVision");
                InfoProvider.sendMessage(message);
            }
            return;
        }

        setNightVision(newValue, smoothTransition, showMessage, false);
    }

    private static void setNightVision(double newValue, boolean smoothTransition, boolean showMessage, boolean dynamic) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (nightVision.isLimiterEnabled() && nightVision.getMaximumStrength() > nightVision.getMinimumStrength()) {
            newValue = Math.clamp(newValue, nightVision.getMinimumStrength(), nightVision.getMaximumStrength());
        }

        if (smoothTransition && (nightVision.isSmoothTransitionEnabled() || dynamic)) {
            double valueChangePerTick = nightVision.getTransitionSpeed(dynamic) / 100;
            if (newValue < nightVision.getValue()) {
                valueChangePerTick *= -1;
            }
            startTransitionTimer(newValue, valueChangePerTick, showMessage);
        }
        else {
            nightVision.setValue(newValue);
            if (newValue == 0) {
                setNightVisionStatus(false);
            }
            if (showMessage) {
                InfoProvider.showNightVisionStatusHudMessage();
            }
        }

        if (nightVision.isToggleUpdateEnabled() && newValue != 0) {
            nightVision.setToggledValue((int)Math.round(newValue));
        }
    }

    protected static void toggleDynamicNightVision() {
        boolean newStatus = !nightVision.isDynamicEnabled();
        nightVision.setDynamicStatus(newStatus);
        Text message = Text.translatable("text.gammautils.message.dynamicNightVision" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    protected static void toggleStatusEffect() {
        boolean newStatus = !nightVision.isStatusEffectEnabled();
        nightVision.setStatusEffectStatus(newStatus);
        StatusEffectManager.updateNightVision();
        Text message = Text.translatable("text.gammautils.message.statusEffectNightVision" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    protected static void toggleSmoothTransition() {
        boolean newStatus = !nightVision.isSmoothTransitionEnabled();
        nightVision.setSmoothTransitionStatus(newStatus);
        Text message = Text.translatable("text.gammautils.message.transitionNightVision" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    private static void setNightVisionStatus(boolean status) {
        nightVision.setStatus(status);
        StatusEffectManager.updateNightVision();
    }

    private static void startTransitionTimer(double newValue, double valueChangePerTick, boolean showMessage) {
        transitionTimer = new Timer();
        transitionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double nextValue = nightVision.getValue() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    transitionTimer.cancel();
                    nightVision.setValue(newValue);
                    setNightVisionStatus(newValue != 0);
                }
                else {
                    nightVision.setValue(nextValue);
                    setNightVisionStatus(nextValue != 0);
                }

                if (showMessage) {
                    InfoProvider.showNightVisionStatusHudMessage();
                }
            }
        }, 0, 10);
    }
}
