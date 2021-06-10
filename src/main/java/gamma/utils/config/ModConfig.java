package gamma.utils.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "gamma_utils")
public class ModConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    private int defaultGamma = 100;
    @ConfigEntry.Gui.Tooltip
    private int toggledGamma = 1500;
    @ConfigEntry.Gui.Tooltip
    private int gammaStep = 10;
    @ConfigEntry.Gui.Tooltip
    private boolean saveOptions = true;

    public double defaultGamma() {
        return defaultGamma / 100.0;
    }

    public double toggledGamma() {
        return toggledGamma / 100.0;
    }

    public double gammaStep() {
        return gammaStep / 100.0;
    }

    public boolean saveEnabled() {
        return saveOptions;
    }
}