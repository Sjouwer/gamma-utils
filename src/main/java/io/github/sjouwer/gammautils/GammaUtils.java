package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import net.fabricmc.api.ModInitializer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;

public class GammaUtils implements ModInitializer {

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);

        KeyBindings keyBindings = new KeyBindings();
        keyBindings.setKeyBindings();
    }
}