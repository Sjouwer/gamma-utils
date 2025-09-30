package io.github.sjouwer.gammautils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static final KeyBinding.Category GAMMA_CATEGORY = KeyBinding.Category.create(Identifier.of(GammaUtils.NAMESPACE, "gamma"));
    private static final KeyBinding.Category NIGHT_VISION_CATEGORY = KeyBinding.Category.create(Identifier.of(GammaUtils.NAMESPACE, "nightvision"));
    private static final String BASE_KEY = "key." + GammaUtils.NAMESPACE + ".";

    public static final KeyBinding GAMMA_TOGGLE = new KeyBinding(BASE_KEY + "gammaToggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, GAMMA_CATEGORY);
    public static final KeyBinding GAMMA_INCREASE = new KeyBinding(BASE_KEY + "increaseGamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, GAMMA_CATEGORY);
    public static final KeyBinding GAMMA_DECREASE = new KeyBinding(BASE_KEY + "decreaseGamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, GAMMA_CATEGORY);
    public static final KeyBinding GAMMA_MAX = new KeyBinding(BASE_KEY + "maxGamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, GAMMA_CATEGORY);
    public static final KeyBinding GAMMA_MIN = new KeyBinding(BASE_KEY + "minGamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, GAMMA_CATEGORY);
    public static final KeyBinding NIGHT_VISION_TOGGLE = new KeyBinding(BASE_KEY + "nightVisionToggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, NIGHT_VISION_CATEGORY);
    public static final KeyBinding NIGHT_VISION_INCREASE = new KeyBinding(BASE_KEY + "increaseNightVision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, NIGHT_VISION_CATEGORY);
    public static final KeyBinding NIGHT_VISION_DECREASE = new KeyBinding(BASE_KEY + "decreaseNightVision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, NIGHT_VISION_CATEGORY);

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
            while (GAMMA_TOGGLE.wasPressed()) {
                GammaManager.toggleGamma();
            }
        });
    }

    private static void registerIncreaseGammaKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_INCREASE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_INCREASE.wasPressed()) {
                GammaManager.increaseGamma(0);
            }
        });
    }

    private static void registerDecreaseGammaKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_DECREASE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_DECREASE.wasPressed()) {
                GammaManager.decreaseGamma(0);
            }
        });
    }

    private static void registerMaxGammaKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_MAX);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_MAX.wasPressed()) {
                GammaManager.maxGamma();
            }
        });
    }

    private static void registerMinGammaKey() {
        KeyBindingHelper.registerKeyBinding(GAMMA_MIN);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (GAMMA_MIN.wasPressed()) {
                GammaManager.minGamma();
            }
        });
    }

    private static void registerNightVisionToggleKey() {
        KeyBindingHelper.registerKeyBinding(NIGHT_VISION_TOGGLE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (NIGHT_VISION_TOGGLE.wasPressed()) {
                NightVisionManager.toggleNightVision();
            }
        });
    }

    private static void registerIncreaseNightVisionKey() {
        KeyBindingHelper.registerKeyBinding(NIGHT_VISION_INCREASE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (NIGHT_VISION_INCREASE.wasPressed()) {
                NightVisionManager.increaseNightVision(0);
            }
        });
    }

    private static void registerDecreaseNightVisionKey() {
        KeyBindingHelper.registerKeyBinding(NIGHT_VISION_DECREASE);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (NIGHT_VISION_DECREASE.wasPressed()) {
                NightVisionManager.decreaseNightVision(0);
            }
        });
    }
}
