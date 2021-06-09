package gamma.utils;

import net.fabricmc.api.ModInitializer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import gamma.utils.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Formatting;

import static net.minecraft.text.Style.EMPTY;

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
        int gamma = (int)Math.round(minecraft.options.gamma * 100);
        BaseText message = new LiteralText("Gamma: " + gamma + "%");

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

    private void loadConfig() {
        if (config == null) {
            config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        }
    }
}