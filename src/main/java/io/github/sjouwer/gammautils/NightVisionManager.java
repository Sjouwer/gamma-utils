package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Timer;
import java.util.TimerTask;

public class NightVisionManager {
    private static final ModConfig.NightVisionSettings config = GammaUtils.getConfig().nightVision;
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static Timer transitionTimer = null;

    private NightVisionManager() {
    }

    public static int getNightVisionPercentage() {
        return (int)Math.round(config.getStrength());
    }

    public static void toggleNightVision() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (config.isEnabled()) {
            NightVisionManager.setNightVision(0, true, true);
        }
        else {
            NightVisionManager.setNightVision(0, false, false);
            StatusEffectManager.enableNightVision(player);
            NightVisionManager.setNightVision(config.getToggledStrength(), true, false);
        }
    }

    public static void increaseNightVision(int value) {
        double newValue = config.getStrength();
        newValue += value == 0 ? config.getStepStrength() : value;
        setNightVision(newValue, false, false);
    }

    public static void decreaseNightVision(int value) {
        double newValue = config.getStrength();
        newValue -= value == 0 ? config.getStepStrength() : value;
        setNightVision(newValue, false, false);
    }

    public static void setDimensionPreference() {
        if (client.world == null || !config.isDimensionPreferenceEnabled()) {
            return;
        }

        RegistryKey<World> dimension = client.world.getRegistryKey();
        if (dimension.equals(World.OVERWORLD)) {
            setNightVision(config.getOverworldPreference(), false, false);
        }
        else if (dimension.equals(World.NETHER)) {
            setNightVision(config.getNetherPreference(), false, false);
        }
        else if (dimension.equals(World.END)) {
            setNightVision(config.getEndPreference(), false, false);
        }
    }

    public static void setNightVision(double newValue, boolean smoothTransition, boolean disable) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (config.isLimiterEnabled() && config.getMaximumStrength() > config.getMinimumStrength()) {
            newValue = Math.clamp(newValue, config.getMinimumStrength(), config.getMaximumStrength());
        }

        if (smoothTransition && config.isSmoothTransitionEnabled()) {
            double valueChangePerTick = config.getTransitionSpeed() / 100;
            if (newValue < config.getStrength()) {
                valueChangePerTick *= -1;
            }
            startTransitionTimer(newValue, valueChangePerTick, disable);
        }
        else {
            config.setStrength(newValue);
            if (disable) {
                StatusEffectManager.disableNightVision(client.player);
            }
            InfoProvider.showNightVisionStatusHudMessage();
        }

        if (config.isToggleUpdateEnabled() && newValue != 0 && newValue != config.getToggledStrength()) {
            config.setToggledStrength((int)Math.round(newValue));
        }
    }

    private static void startTransitionTimer(double newValue, double valueChangePerTick, boolean disable) {
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
                        StatusEffectManager.disableNightVision(client.player);
                    }
                }
                else {
                    config.setStrength(nextValue);
                }
                InfoProvider.showNightVisionStatusHudMessage();
            }
        }, 0, 10);
    }
}
