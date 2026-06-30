package io.github.sjouwer.gammautils.compat;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.minecraft.resources.Identifier;

public class SodiumConfigBuilder implements ConfigEntryPoint {
    @Override
    public void registerConfigLate(ConfigBuilder builder) {
        ModConfig.GammaSettings gamma = GammaUtils.getConfig().gamma;

        int min = gamma.getMin();
        int max = gamma.getMax();
        if (min > max) {
            min = -750;
            max = 1500;
        }

        int step = gamma.getStep();
        if (step == 0 || min % step != 0 || max % step != 0) {
            step = 1;
        }

        builder.registerModOptions(GammaUtils.NAMESPACE, "Gamma Utils", "1")
                .registerOptionOverlay(
                        Identifier.parse("sodium:general.gamma"),
                        builder.createIntegerOption(
                                Identifier.parse("sodium:general.gamma")).setRange(min, max, step)
                );
    }
}
