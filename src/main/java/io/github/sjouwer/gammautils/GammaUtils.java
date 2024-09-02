package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.*;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        configHolder = AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        configHolder.registerSaveListener((manager, data) -> {
            StatusEffectManager.updateAllEffects();
            return ActionResult.SUCCESS;
        });

        KeyBindings.registerBindings();
        Commands.registerCommands();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            GammaManager.setDynamicGamma();
            NightVisionManager.setDynamicNightVision();
        });
    }
}
