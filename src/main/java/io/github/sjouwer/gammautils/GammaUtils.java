package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.JanksonConfigSerializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;

public class GammaUtils implements ModInitializer {
    private ModConfig config;
    private final MinecraftClient minecraft = MinecraftClient.getInstance();
    private final double maxGamma = 15;
    private final double minGamma = -7.5;

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);

        KeyBindings keyBindings = new KeyBindings();
        keyBindings.setKeyBindings();
    }

    public void toggleGamma() {
        loadConfig();

        if (minecraft.options.gamma == config.defaultGamma()) {
            minecraft.options.gamma = config.toggledGamma();
        }
        else {
            minecraft.options.gamma = config.defaultGamma();
        }
        sendMessage();
    }

    public void increaseGamma() {
        loadConfig();

        if (minecraft.options.gamma < maxGamma) {
            minecraft.options.gamma += config.gammaStep();
        }
        if (minecraft.options.gamma > maxGamma) {
            minecraft.options.gamma = maxGamma;
        }
        sendMessage();
    }

    public void decreaseGamma() {
        loadConfig();

        if (minecraft.options.gamma > minGamma) {
            minecraft.options.gamma -= config.gammaStep();
        }
        if (minecraft.options.gamma < minGamma) {
            minecraft.options.gamma = minGamma;
        }
        sendMessage();
    }

    private void sendMessage() {
        BaseText message = new LiteralText("Gamma: " + Math.round(minecraft.options.gamma * 100) + "%");
        minecraft.inGameHud.setOverlayMessage(message, false);
    }

    private void loadConfig() {
        if (config == null) {
            config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        }
    }

    public void saveOptions() {
        if (config.saveEnabled()) {
            minecraft.options.write();
        }
    }
}