package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private static final String CATEGORY = "key.categories.gamma_utils";

    private KeyBindings() {
    }

    public static void registerBindings() {
        registerGammaToggleKey();
        registerIncreaseGammaKey();
        registerDecreaseGammaKey();
        registerNightVisionToggleKey();
    }

    private static void registerGammaToggleKey() {
        KeyBinding gammaToggleKey = new KeyBinding("key.gamma_utils.gamma_toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, CATEGORY);
        KeyBindingHelper.registerKeyBinding(gammaToggleKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (gammaToggleKey.wasPressed()) {
                GammaOptions.toggleGamma();
            }
        });
    }

    private static void registerIncreaseGammaKey() {
        KeyBinding increaseGammaKey = new KeyBinding("key.gamma_utils.increase_gamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, CATEGORY);
        KeyBindingHelper.registerKeyBinding(increaseGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (increaseGammaKey.wasPressed()) {
                GammaOptions.increaseGamma(0);
            }
        });
    }

    private static void registerDecreaseGammaKey() {
        KeyBinding decreaseGammaKey = new KeyBinding("key.gamma_utils.decrease_gamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, CATEGORY);
        KeyBindingHelper.registerKeyBinding(decreaseGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (decreaseGammaKey.wasPressed()) {
                GammaOptions.decreaseGamma(0);
            }
        });
    }

    private static void registerNightVisionToggleKey() {
        KeyBinding nightVisionToggleKey = new KeyBinding("key.gamma_utils.night_vision_toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, CATEGORY);
        KeyBindingHelper.registerKeyBinding(nightVisionToggleKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (nightVisionToggleKey.wasPressed()) {
                StatusEffectManager.toggleNightVision();
            }
        });
    }
}
