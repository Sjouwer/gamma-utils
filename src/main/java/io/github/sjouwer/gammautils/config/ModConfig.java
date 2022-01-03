package io.github.sjouwer.gammautils.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;

@Config(name = "gamma_utils")
public class ModConfig implements ConfigData {
    static class AdvancedOptionsObj {
        @Tooltip
        private boolean limitCheck = true;
        @Tooltip
        private int minGamma = -750;
        @Tooltip
        private int maxGamma = 1500;
        @Tooltip
        private boolean saveOptions = true;
    }

    @Tooltip
    private int defaultGamma = 100;
    @Tooltip
    private int toggledGamma = 1500;
    @Tooltip
    private int gammaStep = 10;
    @CollapsibleObject
    private AdvancedOptionsObj advancedOptions = new AdvancedOptionsObj();

    public double defaultGamma() {
        return defaultGamma / 100.0;
    }

    public double toggledGamma() {
        return toggledGamma / 100.0;
    }

    public double gammaStep() {
        return gammaStep / 100.0;
    }

    public boolean limitCheck() {
        return advancedOptions.limitCheck;
    }

    public double minGamma() {
        return advancedOptions.minGamma / 100.0;
    }

    public double maxGamma() {
        return advancedOptions.maxGamma / 100.0;
    }

    public boolean saveEnabled() {
        return advancedOptions.saveOptions;
    }
}