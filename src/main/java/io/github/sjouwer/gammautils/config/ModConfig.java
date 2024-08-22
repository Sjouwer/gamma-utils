package io.github.sjouwer.gammautils.config;

import io.github.sjouwer.gammautils.GammaUtils;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Category;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.*;

@SuppressWarnings("FieldMayBeFinal")
@Config(name = GammaUtils.NAMESPACE)
public class ModConfig implements ConfigData {
    @Category("gammaSettings")
    @TransitiveObject
    private GammaSettings gammaSettings = new GammaSettings();

    @Category("nightVisionSettings")
    @TransitiveObject
    private NightVisionSettings nightVisionSettings = new NightVisionSettings();

    static class GammaSettings {
        @Tooltip
        private int defaultGamma = 100;
        @Tooltip
        private int toggledGamma = 1500;
        @Tooltip
        private boolean updateToggle = false;
        @Tooltip
        private int gammaStep = 10;
        @Tooltip
        private boolean showMessage = true;
        @Tooltip
        private boolean showStatusEffect = false;
        @Tooltip
        private boolean resetOnClose = false;
        @CollapsibleObject
        private GammaTransitionSettings transitionSettings = new GammaTransitionSettings();
        @CollapsibleObject
        private GammaLimiterSettings limiterSettings = new GammaLimiterSettings();
    }

    static class GammaTransitionSettings {
        @Tooltip
        private boolean smoothTransition = false;
        @Tooltip
        private int transitionSpeed = 3000;
    }

    static class GammaLimiterSettings {
        @Tooltip
        private boolean limitCheck = true;
        @Tooltip
        private int minGamma = -750;
        @Tooltip
        private int maxGamma = 1500;
    }

    static class NightVisionTransitionSettings {
        @Tooltip
        private boolean smoothTransition = false;
        @Tooltip
        private int transitionSpeed = 200;
    }

    static class NightVisionLimiterSettings {
        @Tooltip
        private boolean limitCheck = true;
        @Tooltip
        private int minNightVision = 0;
        @Tooltip
        private int maxNightVision = 100;
    }

    static class NightVisionSettings {
        @Excluded
        private boolean nightVisionEnabled = false;
        @Excluded
        private double nightVisionStrength = 100;
        @Tooltip
        private int toggledNightVision = 100;
        @Tooltip
        private boolean updateToggle = false;
        @Tooltip
        private int nightVisionStep = 2;
        @Tooltip
        private boolean showNightVisionIcon = false;
        @Tooltip
        private boolean showMessage = true;
        @Tooltip
        private boolean resetOnClose = false;
        @CollapsibleObject
        private NightVisionTransitionSettings transitionSettings = new NightVisionTransitionSettings();
        @CollapsibleObject
        private NightVisionLimiterSettings limiterSettings = new NightVisionLimiterSettings();
    }

    public void setNightVision(boolean status) {
        nightVisionSettings.nightVisionEnabled = status;
    }

    public double getDefaultGamma() {
        return gammaSettings.defaultGamma / 100.0;
    }

    public double getToggledGamma() {
        return gammaSettings.toggledGamma / 100.0;
    }

    public void setToggledGamma(double newValue) {
        gammaSettings.toggledGamma = (int)Math.round(newValue * 100);
    }

    public boolean isGammaToggleUpdateEnabled() {
        return gammaSettings.updateToggle;
    }

    public double getGammaStep() {
        return gammaSettings.gammaStep / 100.0;
    }

    public boolean isSmoothGammaEnabled() {
        return gammaSettings.transitionSettings.smoothTransition;
    }

    public double getGammaTransitionSpeed() {
        return gammaSettings.transitionSettings.transitionSpeed / 100.0;
    }

    public boolean isGammaLimiterEnabled() {
        return gammaSettings.limiterSettings.limitCheck;
    }

    public double getMinGamma() {
        return gammaSettings.limiterSettings.minGamma / 100.0;
    }

    public double getMaxGamma() {
        return gammaSettings.limiterSettings.maxGamma / 100.0;
    }

    public boolean isGammaMessageEnabled() {
        return gammaSettings.showMessage;
    }

    public boolean isStatusEffectEnabled() {
        return gammaSettings.showStatusEffect;
    }

    public boolean isResetGammaOnCloseEnabled() {
        return gammaSettings.resetOnClose;
    }

    public boolean isNightVisionEnabled() {
        return nightVisionSettings.nightVisionEnabled;
    }

    public double getNightVisionStrength() {
        return nightVisionSettings.nightVisionStrength;
    }

    public void setNightVisionStrength(double newValue) {
        nightVisionSettings.nightVisionStrength = newValue;
    }

    public int getToggledNightVision() {
        return nightVisionSettings.toggledNightVision;
    }

    public void setToggledNightVision(int newValue) {
        nightVisionSettings.toggledNightVision = newValue;
    }

    public boolean isNightVisionToggleUpdateEnabled() {
        return nightVisionSettings.updateToggle;
    }

    public int getNightVisionStep() {
        return nightVisionSettings.nightVisionStep;
    }

    public boolean isSmoothNightVisionEnabled() {
        return nightVisionSettings.transitionSettings.smoothTransition;
    }

    public double getNightVisionTransitionSpeed() {
        return nightVisionSettings.transitionSettings.transitionSpeed;
    }

    public boolean isNightVisionLimiterEnabled() {
        return nightVisionSettings.limiterSettings.limitCheck;
    }

    public int getMaxNightVisionStrength() {
        return nightVisionSettings.limiterSettings.maxNightVision;
    }

    public int getMinNightVisionStrength() {
        return nightVisionSettings.limiterSettings.minNightVision;
    }

    public boolean isNightVisionIconEnabled() {
        return nightVisionSettings.showNightVisionIcon;
    }

    public boolean isNightVisionMessageEnabled() {
        return nightVisionSettings.showMessage;
    }

    public boolean isResetNightVisionOnCloseEnabled() {
        return nightVisionSettings.resetOnClose;
    }
}
