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
    public GammaSettings gamma = new GammaSettings();

    public static class GammaSettings {
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
        private Transition transition = new Transition();
        @CollapsibleObject
        private DimensionPreference dimensionPreference = new DimensionPreference();
        @CollapsibleObject
        private Limiter limiter = new Limiter();

        static class Transition {
            @Tooltip
            private boolean smoothTransition = false;
            @Tooltip
            private int transitionSpeed = 3000;
        }

        static class DimensionPreference {
            @Tooltip
            private boolean enableDimensionPreference = false;
            @Tooltip
            private int overworldPreference = 1500;
            @Tooltip
            private int netherPreference = 1500;
            @Tooltip
            private int endPreference = 1500;
        }

        static class Limiter {
            @Tooltip
            private boolean limitCheck = true;
            @Tooltip
            private int minGamma = -750;
            @Tooltip
            private int maxGamma = 1500;
        }

        public double getDefaultStrength() {
            return defaultGamma / 100.0;
        }

        public double getToggledStrength() {
            return toggledGamma / 100.0;
        }

        public void setToggledStrength(double newValue) {
            toggledGamma = (int)Math.round(newValue * 100);
        }

        public boolean isToggleUpdateEnabled() {
            return updateToggle;
        }

        public double getStepStrength() {
            return gammaStep / 100.0;
        }

        public boolean isSmoothTransitionEnabled() {
            return transition.smoothTransition;
        }

        public double getTransitionSpeed() {
            return transition.transitionSpeed / 100.0;
        }

        public boolean isStatusEffectEnabled() {
            return showStatusEffect;
        }

        public boolean isResetOnCloseEnabled() {
            return resetOnClose;
        }

        public boolean isLimiterEnabled() {
            return limiter.limitCheck;
        }

        public double getMinimumStrength() {
            return limiter.minGamma / 100.0;
        }

        public double getMaximumStrength() {
            return limiter.maxGamma / 100.0;
        }

        public boolean isMessageEnabled() {
            return showMessage;
        }

        public boolean isDimensionPreferenceEnabled() {
            return dimensionPreference.enableDimensionPreference;
        }

        public double getOverworldPreference() {
            return dimensionPreference.overworldPreference / 100.0;
        }

        public double getNetherPreference() {
            return dimensionPreference.netherPreference / 100.0;
        }

        public double getEndPreference() {
            return dimensionPreference.endPreference / 100.0;
        }
    }

    @Category("nightVisionSettings")
    @TransitiveObject
    public NightVisionSettings nightVision = new NightVisionSettings();

    public static class NightVisionSettings {
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
        private Transition transition = new Transition();
        @CollapsibleObject
        private DimensionPreference dimensionPreference = new DimensionPreference();
        @CollapsibleObject
        private Limiter limiter = new Limiter();

        static class Transition {
            @Tooltip
            private boolean smoothTransition = false;
            @Tooltip
            private int transitionSpeed = 200;
        }

        static class DimensionPreference {
            @Tooltip
            private boolean enableDimensionPreference = false;
            @Tooltip
            private int overworldPreference = 100;
            @Tooltip
            private int netherPreference = 100;
            @Tooltip
            private int endPreference = 100;
        }

        static class Limiter {
            @Tooltip
            private boolean limitCheck = true;
            @Tooltip
            private int minNightVision = 0;
            @Tooltip
            private int maxNightVision = 100;
        }

        public void setStatus(boolean status) {
            nightVisionEnabled = status;
        }

        public boolean isEnabled() {
            return nightVisionEnabled;
        }

        public double getStrength() {
            return nightVisionStrength;
        }

        public void setStrength(double newValue) {
            nightVisionStrength = newValue;
        }

        public int getToggledStrength() {
            return toggledNightVision;
        }

        public void setToggledStrength(int newValue) {
            toggledNightVision = newValue;
        }

        public boolean isToggleUpdateEnabled() {
            return updateToggle;
        }

        public int getStepStrength() {
            return nightVisionStep;
        }

        public boolean isSmoothTransitionEnabled() {
            return transition.smoothTransition;
        }

        public double getTransitionSpeed() {
            return transition.transitionSpeed;
        }

        public boolean isLimiterEnabled() {
            return limiter.limitCheck;
        }

        public int getMaximumStrength() {
            return limiter.maxNightVision;
        }

        public int getMinimumStrength() {
            return limiter.minNightVision;
        }

        public boolean isIconEnabled() {
            return showNightVisionIcon;
        }

        public boolean isMessageEnabled() {
            return showMessage;
        }

        public boolean isResetOnCloseEnabled() {
            return resetOnClose;
        }

        public boolean isDimensionPreferenceEnabled() {
            return dimensionPreference.enableDimensionPreference;
        }

        public double getOverworldPreference() {
            return dimensionPreference.overworldPreference;
        }

        public double getNetherPreference() {
            return dimensionPreference.netherPreference;
        }

        public double getEndPreference() {
            return dimensionPreference.endPreference;
        }
    }
}
