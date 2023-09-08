package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.*;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GammaUtils implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Gamma Utils");
    private static ConfigHolder<ModConfig> configHolder;

    public static ModConfig getConfig() {
        return configHolder.getConfig();
    }

    public static void saveConfig() {
        configHolder.save();
    }

    @Override
    public void onInitializeClient() {
        configHolder = AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        configHolder.registerSaveListener((manager, data) -> {
            StatusEffectManager.updateGammaStatusEffect();
            return ActionResult.SUCCESS;
        });

        KeyBindings.registerBindings();
        Commands.registerCommands();
    }
}
