package io.github.sjouwer.gammautils.config;

import io.github.sjouwer.gammautils.GammaUtils;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.*;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.*;

@SuppressWarnings("FieldMayBeFinal")
@Config(name = GammaUtils.NAMESPACE)
public class ModConfig implements ConfigData {
    @Category("gammaSettings")
    @TransitiveObject
    public GammaSettings gamma = new GammaSettings();

    public static class GammaSettings {
        @Excluded
        private double value = 1;
        @Tooltip
        private int defaultGamma = 100;
        @Tooltip
        private int toggledGamma = 1500;
        @Tooltip
        private boolean updateToggle = false;
        @Tooltip
        private int gammaStep = 10;
        @Tooltip
        private boolean showStatusEffect = false;
        @Tooltip
        private boolean resetOnClose = false;
        @CollapsibleObject
        private Transition transition = new Transition();
        @CollapsibleObject
        private DynamicGamma dynamic = new DynamicGamma();
        @CollapsibleObject
        private DimensionPreference dimensionPreference = new DimensionPreference();
        @CollapsibleObject
        private Limiter limiter = new Limiter();
        @CollapsibleObject
        private HudMessage hudMessage = new HudMessage();

        static class Transition {
            @Tooltip
            private boolean smoothTransition = false;
            @Tooltip
            private int transitionSpeed = 3000;
        }

        static class DynamicGamma {
            @Tooltip
            private boolean enabled = false;
            @Excluded
            private boolean paused = false;
            @Tooltip
            private int minGamma = 100;
            @Tooltip
            private int maxGamma = 1000;
            @Tooltip
            private int transitionSpeed = 200;
            @Tooltip
            @BoundedDiscrete(max=16)
            private int averagingLightRange = 8;
            @Tooltip
            @BoundedDiscrete(min=0, max=100)
            private int skyBrightnessOverride = 0;
        }

        static class DimensionPreference {
            @Tooltip
            private boolean enabled = false;
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

        static class HudMessage {
            @Tooltip
            private boolean showMessage = true;
            @ColorPicker
            private int defaultColor = 43520;
            @ColorPicker
            private int positiveColor = 0xFFAA00;
            @ColorPicker
            private int negativeColor = 0xAA0000;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double newValue) {
            value = newValue;
        }

        public double getDefaultValue() {
            return defaultGamma / 100.0;
        }

        public double getToggledValue() {
            return toggledGamma / 100.0;
        }

        public void setToggledValue(double newValue) {
            toggledGamma = (int)Math.round(newValue * 100);
        }

        public boolean isToggleUpdateEnabled() {
            return updateToggle;
        }

        public double getStepValue() {
            return gammaStep / 100.0;
        }

        public boolean isSmoothTransitionEnabled() {
            return transition.smoothTransition;
        }

        public void setSmoothTransitionStatus(boolean status) {
            transition.smoothTransition = status;
        }

        public double getTransitionSpeed(boolean dynamic) {
            return (dynamic ? this.dynamic.transitionSpeed : transition.transitionSpeed) / 100.0;
        }

        public boolean isStatusEffectEnabled() {
            return showStatusEffect;
        }

        public void setStatusEffectStatus(boolean status) {
            showStatusEffect = status;
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

        public boolean isDimensionPreferenceEnabled() {
            return dimensionPreference.enabled;
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

        public boolean isDynamicEnabled() {
            return dynamic.enabled;
        }

        public void setDynamicStatus(boolean status) {
            dynamic.enabled = status;
        }

        public boolean isDynamicPaused() {
            return dynamic.paused;
        }

        public void toggleDynamicPause() {
            dynamic.paused = !dynamic.paused;
        }

        public double getMinDynamicStrength() {
            return dynamic.minGamma / 100.0;
        }

        public double getMaxDynamicStrength() {
            return dynamic.maxGamma / 100.0;
        }

        public int getDynamicAveragingLightRange() {
            return dynamic.averagingLightRange;
        }

        public float getSkyBrightnessOverride() {
            return dynamic.skyBrightnessOverride / 100f;
        }

        public boolean isHudMessageEnabled() {
            return hudMessage.showMessage;
        }

        public int getDefaultHudColor() {
            return hudMessage.defaultColor;
        }

        public int getPositiveHudColor() {
            return hudMessage.positiveColor;
        }

        public int getNegativeHudColor() {
            return hudMessage.negativeColor;
        }
    }

    @Category("nightVisionSettings")
    @TransitiveObject
    public NightVisionSettings nightVision = new NightVisionSettings();

    public static class NightVisionSettings {
        @Excluded
        private boolean enabled = false;
        @Excluded
        private double value = 100;
        @Tooltip
        private int toggledNightVision = 100;
        @Tooltip
        private boolean updateToggle = false;
        @Tooltip
        private int nightVisionStep = 2;
        @Tooltip
        private boolean brightenFogColor = true;
        @Tooltip
        private boolean showStatusEffect = false;
        @Tooltip
        private boolean resetOnClose = false;
        @CollapsibleObject
        private Transition transition = new Transition();
        @CollapsibleObject
        private DynamicNightVision dynamic = new DynamicNightVision();
        @CollapsibleObject
        private DimensionPreference dimensionPreference = new DimensionPreference();
        @CollapsibleObject
        private Limiter limiter = new Limiter();
        @CollapsibleObject
        private HudMessage hudMessage = new HudMessage();

        static class Transition {
            @Tooltip
            private boolean smoothTransition = false;
            @Tooltip
            private int transitionSpeed = 200;
        }

        static class DynamicNightVision {
            @Tooltip
            private boolean enabled = false;
            @Excluded
            private boolean paused = false;
            @Tooltip
            private int minNightVision = 0;
            @Tooltip
            private int maxNightVision = 100;
            @Tooltip
            private int transitionSpeed = 15;
            @Tooltip
            @BoundedDiscrete(max=16)
            private int averagingLightRange = 8;
            @Tooltip
            @BoundedDiscrete(min=0, max=100)
            private int skyBrightnessOverride = 0;
        }

        static class DimensionPreference {
            @Tooltip
            private boolean enabled = false;
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
            @Tooltip
            private boolean gammaCompatibilityLimiter = false;
        }

        static class HudMessage {
            @Tooltip
            private boolean showMessage = true;
            @ColorPicker
            private int defaultColor = 43520;
            @ColorPicker
            private int positiveColor = 0xFFAA00;
            @ColorPicker
            private int negativeColor = 0xAA0000;
            @ColorPicker
            private int enabledColor = 43520;
            @ColorPicker
            private int disabledColor = 0xAA0000;
        }

        public void setStatus(boolean status) {
            enabled = status;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double newValue) {
            value = newValue;
        }

        public int getToggledValue() {
            return toggledNightVision;
        }

        public void setToggledValue(int newValue) {
            toggledNightVision = newValue;
        }

        public boolean isToggleUpdateEnabled() {
            return updateToggle;
        }

        public int getStepValue() {
            return nightVisionStep;
        }

        public boolean isFogColorBrighteningEnabled() {
            return brightenFogColor;
        }

        public boolean isSmoothTransitionEnabled() {
            return transition.smoothTransition;
        }

        public void setSmoothTransitionStatus(boolean status) {
            transition.smoothTransition = status;
        }

        public double getTransitionSpeed(boolean dynamic) {
            return dynamic ? this.dynamic.transitionSpeed : transition.transitionSpeed;
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

        public boolean isGammaCompatibilityLimiterEnabled() {
            return limiter.gammaCompatibilityLimiter;
        }

        public boolean isStatusEffectEnabled() {
            return showStatusEffect;
        }

        public void setStatusEffectStatus(boolean status) {
            showStatusEffect = status;
        }

        public boolean isResetOnCloseEnabled() {
            return resetOnClose;
        }

        public boolean isDimensionPreferenceEnabled() {
            return dimensionPreference.enabled;
        }

        public int getOverworldPreference() {
            return dimensionPreference.overworldPreference;
        }

        public int getNetherPreference() {
            return dimensionPreference.netherPreference;
        }

        public int getEndPreference() {
            return dimensionPreference.endPreference;
        }

        public boolean isDynamicEnabled() {
            return dynamic.enabled;
        }

        public void setDynamicStatus(boolean status) {
            dynamic.enabled = status;
        }

        public boolean isDynamicPaused() {
            return dynamic.paused;
        }

        public void toggleDynamicPause() {
            dynamic.paused = !dynamic.paused;
        }

        public int getMinDynamicStrength() {
            return dynamic.minNightVision;
        }

        public int getMaxDynamicStrength() {
            return dynamic.maxNightVision;
        }

        public int getDynamicAveragingLightRange() {
            return dynamic.averagingLightRange;
        }

        public float getSkyBrightnessOverride() {
            return dynamic.skyBrightnessOverride / 100f;
        }

        public boolean isHudMessageEnabled() {
            return hudMessage.showMessage;
        }

        public int getDefaultHudColor() {
            return hudMessage.defaultColor;
        }

        public int getPositiveHudColor() {
            return hudMessage.positiveColor;
        }

        public int getNegativeHudColor() {
            return hudMessage.negativeColor;
        }

        public int getEnabledHudColor() {
            return hudMessage.enabledColor;
        }

        public int getDisabledHudColor() {
            return hudMessage.disabledColor;
        }
    }

    @Category("otherSettings")
    @TransitiveObject
    public OtherSettings other = new OtherSettings();

    public static class OtherSettings {
        @RequiresRestart
        @Tooltip
        private boolean namespacedCommands = false;

        public boolean namespacedCommandsEnabled() {
            return namespacedCommands;
        }
    }
}
