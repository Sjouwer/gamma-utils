package gamma.utils.config;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

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