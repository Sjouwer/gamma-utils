package io.github.sjouwer.gammautils.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Excluded;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;

@SuppressWarnings("FieldMayBeFinal")
@Config(name = "gamma_utils")
public class ModConfig implements ConfigData {
    static class AdvancedOptionsObj {
        @Tooltip
        private int transitionSpeed = 4500;
        @Tooltip
        private boolean limitCheck = true;
        @Tooltip
        private int minGamma = -750;
        @Tooltip
        private int maxGamma = 1500;
        @Tooltip
        private int gammaStep = 10;
        @Tooltip
        private boolean showMessage = true;
        @Tooltip
        private boolean showNightVisionIcon = false;
        @Tooltip
        private boolean resetOnClose = false;
    }

    @Excluded
    private boolean nightVisionEnabled = false;
    @Tooltip
    private int defaultGamma = 100;
    @Tooltip
    private int toggledGamma = 1500;
    @Tooltip
    private boolean updateToggle = false;
    @Tooltip
    private boolean smoothTransition = false;
    @Tooltip
    private boolean showStatusEffect = false;

    @CollapsibleObject
    private AdvancedOptionsObj advancedOptions = new AdvancedOptionsObj();

    public boolean isNightVisionEnabled() {
        return nightVisionEnabled;
    }

    public void setNightVision(boolean status) {
        nightVisionEnabled = status;
    }

    public double getDefaultGamma() {
        return defaultGamma / 100.0;
    }

    public double getToggledGamma() {
        return toggledGamma / 100.0;
    }

    public void setToggledGamma(double gamma) {
        toggledGamma = (int)Math.round(gamma * 100) ;
    }

    public double getGammaStep() {
        return advancedOptions.gammaStep / 100.0;
    }

    public boolean isUpdateToggleEnabled() {
        return updateToggle;
    }

    public boolean isSmoothTransitionEnabled() {
        return smoothTransition;
    }

    public double getTransitionSpeed() {
        return advancedOptions.transitionSpeed / 100.0;
    }

    public boolean isLimitCheckEnabled() {
        return advancedOptions.limitCheck;
    }

    public double getMinGamma() {
        return advancedOptions.minGamma / 100.0;
    }

    public double getMaxGamma() {
        return advancedOptions.maxGamma / 100.0;
    }

    public boolean isGammaMessageEnabled() {
        return advancedOptions.showMessage;
    }

    public boolean isStatusEffectEnabled() {
        return showStatusEffect;
    }

    public boolean isNightVisionIconShown() {
        return advancedOptions.showNightVisionIcon;
    }

    public boolean isResetOnCloseEnabled() {
        return advancedOptions.resetOnClose;
    }
}
