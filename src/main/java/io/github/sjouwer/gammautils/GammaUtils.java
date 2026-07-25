package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.*;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.InteractionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GammaUtils implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Gamma Utils");
    public static final String NAMESPACE = "gammautils";
    private static ConfigHolder<ModConfig> configHolder;

    public static ModConfig getConfig() {
        return configHolder.getConfig();
    }

    public static void saveConfig() {
        configHolder.save();
    }

    @Override
    public void onInitializeClient() {
        loadConfigFile();

        KeyBindings.registerBindings();
        Commands.registerCommands();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            GammaManager.setDynamicGamma();
            NightVisionManager.setDynamicNightVision();
        });
    }

    private static void loadConfigFile() {
        try {
            precheckConfigFile();
            configHolder = AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        }
        catch (Exception e) {
            // Extra message to help the user in case the precheck didn't work.
            LOGGER.error("Failed to load gammautils config file, try manually deleting the file to resolve this issue. Report this issue on the Gamma Utils repository if the game continues to crash.");
            throw e;
        }

        configHolder.registerSaveListener((manager, data) -> {
            StatusEffectManager.updateAllEffects();
            return InteractionResult.SUCCESS;
        });
    }

    // In rare cases the config file might be completely empty, which causes Cloth Config to crash. This should resolve that issue.
    private static void precheckConfigFile() {
        Path configFolder = FabricLoader.getInstance().getConfigDir();
        Path configFile = configFolder.resolve("gammautils.json");

        try {
            if (Files.exists(configFile) && Files.size(configFile) == 0) {
                LOGGER.warn("Config file is empty, deleting it so a new one can be created by Cloth Config");
                Files.delete(configFile);
            }
        } catch (IOException _) {
            LOGGER.warn("Failed precheck of config file");
        }
    }
}
