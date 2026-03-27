package io.github.sjouwer.gammautils.util;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;

public final class InfoProvider {
    private static final Minecraft client = Minecraft.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();

    private InfoProvider() {
    }

    public static void sendMessage(Component message) {
        if (client.player == null) {
            GammaUtils.LOGGER.info(message.getString());
            return;
        }

        client.player.sendSystemMessage(message);
    }

    public static void showGammaHudMessage() {
        if (!config.gamma.isHudMessageEnabled()) {
            return;
        }

        int gamma = GammaManager.getGammaPercentage();
        MutableComponent message = Component.translatable("text.gammautils.message.gammaPercentage", gamma);

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
        client.gui.setOverlayMessage(message, false);
    }

    public static void showDynamicGammaHudMessage() {
        if (!config.gamma.isHudMessageEnabled()) {
            return;
        }

        MutableComponent message;
        if (config.gamma.isDynamicPaused()) {
            message = Component.translatable("text.gammautils.message.dynamicGammaDisabled");
            message.withColor(config.nightVision.getDisabledHudColor());
        }
        else {
            message = Component.translatable("text.gammautils.message.dynamicGammaEnabled");
            message.withColor(config.nightVision.getEnabledHudColor());
        }

        client.gui.setOverlayMessage(message, false);
    }

    public static void showDynamicNightVisionHudMessage() {
        if (!config.nightVision.isHudMessageEnabled()) {
            return;
        }

        MutableComponent message;
        if (config.nightVision.isDynamicPaused()) {
            message = Component.translatable("text.gammautils.message.dynamicNightVisionDisabled");
            message.withColor(config.nightVision.getDisabledHudColor());
        }
        else {
            message = Component.translatable("text.gammautils.message.dynamicNightVisionEnabled");
            message.withColor(config.nightVision.getEnabledHudColor());
        }

        client.gui.setOverlayMessage(message, false);
    }

    public static void showNightVisionStatusHudMessage() {
        if (!config.nightVision.isHudMessageEnabled()) {
            return;
        }

        if (config.nightVision.isEnabled()) {
            showNightVisionStrengthHudMessage();
        }
        else {
            MutableComponent message = Component.translatable("text.gammautils.message.nightVisionDisabled");
            message.withColor(config.nightVision.getDisabledHudColor());
            client.gui.setOverlayMessage(message, false);
        }
    }

    private static void showNightVisionStrengthHudMessage() {
        int nightVision = NightVisionManager.getNightVisionPercentage();
        MutableComponent message = Component.translatable("text.gammautils.message.nightVisionPercentage", nightVision);

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
        client.gui.setOverlayMessage(message, false);
    }
}
