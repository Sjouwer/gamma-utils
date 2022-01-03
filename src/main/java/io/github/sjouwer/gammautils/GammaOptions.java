package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.BaseText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static net.minecraft.text.Style.EMPTY;

public class GammaOptions {
    private final ModConfig config;
    private final MinecraftClient minecraft = MinecraftClient.getInstance();

    public GammaOptions() {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public void toggleGamma() {
        double value = minecraft.options.gamma;
        if (value == config.getDefaultGamma()) {
            value = config.getToggledGamma();
        }
        else {
            value = config.getDefaultGamma();
        }
        setGamma(value);
    }

    public void increaseGamma(double value) {
        double newValue = minecraft.options.gamma;
        if (value == 0) {
            newValue += config.getGammaStep();
        }
        else {
            newValue += value;
        }
        setGamma(newValue);
    }

    public void decreaseGamma(double value) {
        double newValue = minecraft.options.gamma;
        if (value == 0) {
            newValue -= config.getGammaStep();
        }
        else {
            newValue -= value;
        }
        setGamma(newValue);
    }

    public void minGamma() {
        setGamma(config.getMinGamma());
    }

    public void maxGamma() {
        setGamma(config.getMaxGamma());
    }

    public void setGamma(double value) {
        if (config.getMaxGamma() > config.getMinGamma() && config.limitCheckEnabled()) {
            value = Math.max(config.getMinGamma(), Math.min(value, config.getMaxGamma()));
        }
        minecraft.options.gamma = value;

        if (config.updateToggleEnabled() && value != config.getDefaultGamma() && value != config.getToggledGamma()) {
            config.setToggledGamma(value);
        }

        sendMessage();
    }

    private void sendMessage() {
        if (!config.gammaMessageEnabled()) {
            return;
        }

        int gamma = (int)Math.round(minecraft.options.gamma * 100);
        BaseText message = new TranslatableText("text.gamma_utils.message.gamma", gamma);

        if (gamma < 0) {
            message.setStyle(EMPTY.withColor(Formatting.DARK_RED));
        }
        else if (gamma > 100) {
            message.setStyle(EMPTY.withColor(Formatting.GOLD));
        }
        else {
            message.setStyle(EMPTY.withColor(Formatting.DARK_GREEN));
        }
        minecraft.inGameHud.setOverlayMessage(message, false);
    }
}