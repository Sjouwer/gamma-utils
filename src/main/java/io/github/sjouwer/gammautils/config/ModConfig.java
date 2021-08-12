package io.github.sjouwer.gammautils.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "gamma_utils")
public class ModConfig implements ConfigData {
    static class AdvancedObj {
        @ConfigEntry.Gui.Tooltip
        private boolean limitCheck = true;
        @ConfigEntry.Gui.Tooltip
        private int minGamma = -750;
        @ConfigEntry.Gui.Tooltip
        private int maxGamma = 1500;
        @ConfigEntry.Gui.Tooltip
        private boolean saveOptions = true;
    }

    @ConfigEntry.Gui.Tooltip
    private int defaultGamma = 100;
    @ConfigEntry.Gui.Tooltip
    private int toggledGamma = 1500;
    @ConfigEntry.Gui.Tooltip
    private int gammaStep = 10;
    @ConfigEntry.Gui.CollapsibleObject
    private AdvancedObj advancedOptions = new AdvancedObj();

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