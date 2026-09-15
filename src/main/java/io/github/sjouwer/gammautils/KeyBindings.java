package io.github.sjouwer.gammautils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

public class KeyBindings {
    private static final KeyMapping.Category GAMMA_CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(GammaUtils.NAMESPACE, "gamma"));
    private static final KeyMapping.Category NIGHT_VISION_CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(GammaUtils.NAMESPACE, "nightvision"));
    private static final String BASE_KEY = "key." + GammaUtils.NAMESPACE + ".";

    public static final KeyMapping GAMMA_TOGGLE = new KeyMapping(BASE_KEY + "gammaToggle", InputConstants.Type.KEYBOARD, InputConstants.KEY_G, GAMMA_CATEGORY);
    public static final KeyMapping GAMMA_INCREASE = new KeyMapping(BASE_KEY + "increaseGamma", InputConstants.Type.KEYBOARD, InputConstants.KEY_UP, GAMMA_CATEGORY);
    public static final KeyMapping GAMMA_DECREASE = new KeyMapping(BASE_KEY + "decreaseGamma", InputConstants.Type.KEYBOARD, InputConstants.KEY_DOWN, GAMMA_CATEGORY);
    public static final KeyMapping GAMMA_MAX = new KeyMapping(BASE_KEY + "maxGamma", InputConstants.Type.KEYBOARD, InputConstants.UNKNOWN.getValue(), GAMMA_CATEGORY);
    public static final KeyMapping GAMMA_MIN = new KeyMapping(BASE_KEY + "minGamma", InputConstants.Type.KEYBOARD, InputConstants.UNKNOWN.getValue(), GAMMA_CATEGORY);
    public static final KeyMapping NIGHT_VISION_TOGGLE = new KeyMapping(BASE_KEY + "nightVisionToggle", InputConstants.Type.KEYBOARD, InputConstants.KEY_H, NIGHT_VISION_CATEGORY);
    public static final KeyMapping NIGHT_VISION_INCREASE = new KeyMapping(BASE_KEY + "increaseNightVision", InputConstants.Type.KEYBOARD, InputConstants.KEY_RIGHT, NIGHT_VISION_CATEGORY);
    public static final KeyMapping NIGHT_VISION_DECREASE = new KeyMapping(BASE_KEY + "decreaseNightVision", InputConstants.Type.KEYBOARD, InputConstants.KEY_LEFT, NIGHT_VISION_CATEGORY);

    private KeyBindings() {
    }

    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.registerCategory(GAMMA_CATEGORY);
        event.registerCategory(NIGHT_VISION_CATEGORY);

        event.register(GAMMA_TOGGLE);
        event.register(GAMMA_INCREASE);
        event.register(GAMMA_DECREASE);
        event.register(GAMMA_MAX);
        event.register(GAMMA_MIN);
        event.register(NIGHT_VISION_TOGGLE);
        event.register(NIGHT_VISION_INCREASE);
        event.register(NIGHT_VISION_DECREASE);
    }

    public static void handleBindings() {
        while (GAMMA_TOGGLE.consumeClick()) {
            GammaManager.toggleGamma();
        }

        while (GAMMA_INCREASE.consumeClick()) {
            GammaManager.increaseGamma(0);
        }

        while (GAMMA_DECREASE.consumeClick()) {
            GammaManager.decreaseGamma(0);
        }

        while (GAMMA_MAX.consumeClick()) {
            GammaManager.maxGamma();
        }

        while (GAMMA_MIN.consumeClick()) {
            GammaManager.minGamma();
        }

        while (NIGHT_VISION_TOGGLE.consumeClick()) {
            NightVisionManager.toggleNightVision();
        }

        while (NIGHT_VISION_INCREASE.consumeClick()) {
            NightVisionManager.increaseNightVision(0);
        }

        while (NIGHT_VISION_DECREASE.consumeClick()) {
            NightVisionManager.decreaseNightVision(0);
        }
    }
}
