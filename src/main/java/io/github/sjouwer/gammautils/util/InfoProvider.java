package io.github.sjouwer.gammautils.util;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public final class InfoProvider {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();

    private InfoProvider() {
    }

    public static void showGammaHudMessage() {
        if (!config.gamma.isHudMessageEnabled()) {
            return;
        }

        int gamma = GammaManager.getGammaPercentage();
        MutableText message = Text.translatable("text.gammautils.message.gammaPercentage", gamma);

        int color;
        if (gamma < 0) {
            color = config.gamma.getNegativeHudColor();
        }
        else if (gamma > 100) {
            color = config.gamma.getPositiveHudColor();
        }
        else {
            color = config.gamma.getDefaultHudColor();
        }

        message.withColor(color);
        client.inGameHud.setOverlayMessage(message, false);
    }

    public static void showDynamicNightVisionHudMessage(boolean enabled) {
        MutableText message;
        if (enabled) {
            message = Text.translatable("text.gammautils.message.dynamicNightVisionEnabled");
            message.withColor(config.nightVision.getEnabledHudColor());
        }
        else {
            message = Text.translatable("text.gammautils.message.dynamicNightVisionDisabled");
            message.withColor(config.nightVision.getDisabledHudColor());
        }

        client.inGameHud.setOverlayMessage(message, false);
    }

    public static void showNightVisionStatusHudMessage() {
        if (!config.nightVision.isHudMessageEnabled()) {
            return;
        }

        if (config.nightVision.isEnabled()) {
            showNightVisionStrengthHudMessage();
        }
        else {
            MutableText message = Text.translatable("text.gammautils.message.nightVisionDisabled");
            message.withColor(config.nightVision.getDisabledHudColor());
            client.inGameHud.setOverlayMessage(message, false);
        }
    }

    private static void showNightVisionStrengthHudMessage() {
        int nightVision = NightVisionManager.getNightVisionPercentage();
        MutableText message = Text.translatable("text.gammautils.message.nightVisionPercentage", nightVision);

        int color;
        if (nightVision < 0) {
            color = config.nightVision.getNegativeHudColor();
        }
        else if (nightVision > 100) {
            color = config.nightVision.getPositiveHudColor();
        }
        else {
            color = config.nightVision.getDefaultHudColor();
        }

        message.withColor(color);
        client.inGameHud.setOverlayMessage(message, false);
    }
}
