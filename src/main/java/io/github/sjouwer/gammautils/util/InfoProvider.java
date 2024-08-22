package io.github.sjouwer.gammautils.util;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class InfoProvider {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();

    private InfoProvider() {
    }

    public static void showGammaHudMessage() {
        if (!config.gamma.isMessageEnabled()) {
            return;
        }

        int gamma = GammaManager.getGammaPercentage();
        MutableText message = Text.translatable("text.gammautils.message.gammaPercentage", gamma);

        Formatting format;
        if (gamma < 0) {
            format = Formatting.DARK_RED;
        }
        else if (gamma > 100) {
            format = Formatting.GOLD;
        }
        else {
            format = Formatting.DARK_GREEN;
        }

        message.formatted(format);
        client.inGameHud.setOverlayMessage(message, false);
    }

    public static void showNightVisionStatusHudMessage() {
        if (!config.nightVision.isMessageEnabled()) {
            return;
        }

        if (config.nightVision.isEnabled()) {
            showNightVisionStrengthHudMessage();
        }
        else {
            MutableText message = Text.translatable("text.gammautils.message.nightVisionDisabled");
            message.formatted(Formatting.DARK_RED);
            client.inGameHud.setOverlayMessage(message, false);
        }
    }

    private static void showNightVisionStrengthHudMessage() {
        int nightVision = NightVisionManager.getNightVisionPercentage();
        MutableText message = Text.translatable("text.gammautils.message.nightVisionPercentage", nightVision);

        Formatting format;
        if (nightVision < 0) {
            format = Formatting.DARK_RED;
        }
        else if (nightVision > 100) {
            format = Formatting.GOLD;
        }
        else {
            format = Formatting.DARK_GREEN;
        }

        message.formatted(format);
        client.inGameHud.setOverlayMessage(message, false);
    }
}
