package io.github.sjouwer.gammautils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static final KeyMapping.Category GAMMA_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(GammaUtils.NAMESPACE, "gamma"));
    private static final KeyMapping.Category NIGHT_VISION_CATEGORY = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(GammaUtils.NAMESPACE, "nightvision"));
    private static final String BASE_KEY = "key." + GammaUtils.NAMESPACE + ".";

    public static final KeyMapping GAMMA_TOGGLE = new KeyMapping(BASE_KEY + "gammaToggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, GAMMA_CATEGORY);
    public static final KeyMapping GAMMA_INCREASE = new KeyMapping(BASE_KEY + "increaseGamma", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UP, GAMMA_CATEGORY);
    public static final KeyMapping GAMMA_DECREASE = new KeyMapping(BASE_KEY + "decreaseGamma", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, GAMMA_CATEGORY);
    public static final KeyMapping GAMMA_MAX = new KeyMapping(BASE_KEY + "maxGamma", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, GAMMA_CATEGORY);
    public static final KeyMapping GAMMA_MIN = new KeyMapping(BASE_KEY + "minGamma", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, GAMMA_CATEGORY);
    public static final KeyMapping NIGHT_VISION_TOGGLE = new KeyMapping(BASE_KEY + "nightVisionToggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, NIGHT_VISION_CATEGORY);
    public static final KeyMapping NIGHT_VISION_INCREASE = new KeyMapping(BASE_KEY + "increaseNightVision", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, NIGHT_VISION_CATEGORY);
    public static final KeyMapping NIGHT_VISION_DECREASE = new KeyMapping(BASE_KEY + "decreaseNightVision", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, NIGHT_VISION_CATEGORY);

    private KeyBindings() {
    }

    public static void registerBindings() {
        registerGammaToggleKey();
        registerIncreaseGammaKey();
        registerDecreaseGammaKey();
        registerMaxGammaKey();
        registerMinGammaKey();
        registerNightVisionToggleKey();
        registerIncreaseNightVisionKey();
        registerDecreaseNightVisionKey();
    }

    private static void registerGammaToggleKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_TOGGLE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_TOGGLE.consumeClick()) {
                GammaManager.toggleGamma();
            }
        });
    }

    private static void registerIncreaseGammaKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_INCREASE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_INCREASE.consumeClick()) {
                GammaManager.increaseGamma(0);
            }
        });
    }

    private static void registerDecreaseGammaKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_DECREASE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_DECREASE.consumeClick()) {
                GammaManager.decreaseGamma(0);
            }
        });
    }

    private static void registerMaxGammaKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_MAX);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_MAX.consumeClick()) {
                GammaManager.maxGamma();
            }
        });
    }

    private static void registerMinGammaKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_MIN);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_MIN.consumeClick()) {
                GammaManager.minGamma();
            }
        });
    }

    private static void registerNightVisionToggleKey() {
        KeyBindingHelper.registerKeyBinding(NIGHT_VISION_TOGGLE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (NIGHT_VISION_TOGGLE.consumeClick()) {
                NightVisionManager.toggleNightVision();
            }
        });
    }

    private static void registerIncreaseNightVisionKey() {
        KeyBindingHelper.registerKeyBinding(NIGHT_VISION_INCREASE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (NIGHT_VISION_INCREASE.consumeClick()) {
                NightVisionManager.increaseNightVision(0);
            }
        });
    }

    private static void registerDecreaseNightVisionKey() {
        KeyBindingHelper.registerKeyBinding(NIGHT_VISION_DECREASE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (NIGHT_VISION_DECREASE.consumeClick()) {
                NightVisionManager.decreaseNightVision(0);
            }
        });
    }
}
