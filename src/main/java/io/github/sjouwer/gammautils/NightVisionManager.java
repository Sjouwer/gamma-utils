package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import java.util.Timer;
import java.util.TimerTask;

public class NightVisionManager {
    private static final ModConfig config = GammaUtils.getConfig();
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static Timer transitionTimer = null;

    private NightVisionManager() {
    }

    public static int getNightVisionPercentage() {
        return (int)Math.round(config.getNightVisionStrength());
    }

    public static void toggleNightVision() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (config.isNightVisionEnabled()) {
            NightVisionManager.setNightVision(0, true, true);
        }
        else {
            NightVisionManager.setNightVision(0, false, false);
            StatusEffectManager.enableNightVision(player);
            NightVisionManager.setNightVision(config.getToggledNightVision(), true, false);
        }
    }

    public static void increaseNightVision(int value) {
        double newValue = config.getNightVisionStrength();
        newValue += value == 0 ? config.getNightVisionStep() : value;
        setNightVision(newValue, false, false);
    }

    public static void decreaseNightVision(int value) {
        double newValue = config.getNightVisionStrength();
        newValue -= value == 0 ? config.getNightVisionStep() : value;
        setNightVision(newValue, false, false);
    }

    public static void setNightVision(double newValue, boolean smoothTransition, boolean disable) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (config.isNightVisionLimiterEnabled() && config.getMaxNightVisionStrength() > config.getMinNightVisionStrength()) {
            newValue = Math.clamp(newValue, config.getMinNightVisionStrength(), config.getMaxNightVisionStrength());
        }

        if (smoothTransition && config.isSmoothNightVisionEnabled()) {
            double valueChangePerTick = config.getNightVisionTransitionSpeed() / 100;
            if (newValue < config.getNightVisionStrength()) {
                valueChangePerTick *= -1;
            }
            startTransitionTimer(newValue, valueChangePerTick, disable);
        }
        else {
            config.setNightVisionStrength(newValue);
            if (disable) {
                StatusEffectManager.disableNightVision(client.player);
            }
            InfoProvider.showNightVisionStatusHudMessage();
        }

        if (config.isNightVisionToggleUpdateEnabled() && newValue != 0 && newValue != config.getToggledNightVision()) {
            config.setToggledNightVision((int)Math.round(newValue));
        }
    }

    private static void startTransitionTimer(double newValue, double valueChangePerTick, boolean disable) {
        transitionTimer = new Timer();
        transitionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double nextValue = config.getNightVisionStrength() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    transitionTimer.cancel();
                    config.setNightVisionStrength(newValue);
                    if (disable) {
                        StatusEffectManager.disableNightVision(client.player);
                    }
                }
                else {
                    config.setNightVisionStrength(nextValue);
                }
                InfoProvider.showNightVisionStatusHudMessage();
            }
        }, 0, 10);
    }
}
