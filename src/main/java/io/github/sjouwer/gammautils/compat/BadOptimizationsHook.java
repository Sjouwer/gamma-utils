package io.github.sjouwer.gammautils.compat;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;

import java.util.function.BooleanSupplier;

public class BadOptimizationsHook implements BooleanSupplier {
    private boolean lastNightVisionStatus = false;
    private double lastNightVisionStrength = 0;

    @Override
    public boolean getAsBoolean() {
        ModConfig.NightVisionSettings nightVision = GammaUtils.getConfig().nightVision;

        boolean currentNightVisionStatus = nightVision.isEnabled();
        if (lastNightVisionStatus != currentNightVisionStatus) {
            lastNightVisionStatus = currentNightVisionStatus;
            return true;
        }

        if (currentNightVisionStatus) {
            double currentNightVisionStrength = nightVision.getValue();
            if (lastNightVisionStrength != currentNightVisionStrength) {
                lastNightVisionStrength = currentNightVisionStrength;
                return true;
            }
        }

        return false;
    }
}
