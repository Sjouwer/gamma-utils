package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static final String CATEGORY = "key.categories." + GammaUtils.NAMESPACE;
    private static final String BASE_KEY = "key." + GammaUtils.NAMESPACE;

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
        KeyBinding gammaToggleKey = new KeyBinding(BASE_KEY + ".gammaToggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, CATEGORY);
        KeyBindingHelper.registerKeyBinding(gammaToggleKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (gammaToggleKey.wasPressed()) {
                GammaManager.toggleGamma();
            }
        });
    }

    private static void registerIncreaseGammaKey() {
        KeyBinding increaseGammaKey = new KeyBinding(BASE_KEY + ".increaseGamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, CATEGORY);
        KeyBindingHelper.registerKeyBinding(increaseGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (increaseGammaKey.wasPressed()) {
                GammaManager.increaseGamma(0);
            }
        });
    }

    private static void registerDecreaseGammaKey() {
        KeyBinding decreaseGammaKey = new KeyBinding(BASE_KEY + ".decreaseGamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, CATEGORY);
        KeyBindingHelper.registerKeyBinding(decreaseGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (decreaseGammaKey.wasPressed()) {
                GammaManager.decreaseGamma(0);
            }
        });
    }

    private static void registerMaxGammaKey() {
        KeyBinding maxGammaKey = new KeyBinding(BASE_KEY + ".maxGamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY);
        KeyBindingHelper.registerKeyBinding(maxGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (maxGammaKey.wasPressed()) {
                GammaManager.maxGamma();
            }
        });
    }

    private static void registerMinGammaKey() {
        KeyBinding minGammaKey = new KeyBinding(BASE_KEY + ".minGamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY);
        KeyBindingHelper.registerKeyBinding(minGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (minGammaKey.wasPressed()) {
                GammaManager.minGamma();
            }
        });
    }

    private static void registerNightVisionToggleKey() {
        KeyBinding nightVisionToggleKey = new KeyBinding(BASE_KEY + ".nightVisionToggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, CATEGORY);
        KeyBindingHelper.registerKeyBinding(nightVisionToggleKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (nightVisionToggleKey.wasPressed()) {
                NightVisionManager.toggleNightVision();
            }
        });
    }

    private static void registerIncreaseNightVisionKey() {
        KeyBinding increaseNightVisionKey = new KeyBinding(BASE_KEY + ".increaseNightVision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, CATEGORY);
        KeyBindingHelper.registerKeyBinding(increaseNightVisionKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (increaseNightVisionKey.wasPressed()) {
                NightVisionManager.increaseNightVision(0);
            }
        });
    }

    private static void registerDecreaseNightVisionKey() {
        KeyBinding decreaseNightVisionKey = new KeyBinding(BASE_KEY + ".decreaseNightVision", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, CATEGORY);
        KeyBindingHelper.registerKeyBinding(decreaseNightVisionKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (decreaseNightVisionKey.wasPressed()) {
                NightVisionManager.decreaseNightVision(0);
            }
        });
    }
}
