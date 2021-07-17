package io.github.sjouwer.gammautils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private final GammaUtils gammaUtils = new GammaUtils();
    private static final String CATEGORY = "key.categories.gamma_utils";
    private boolean toggleWasPressed = false;
    private boolean increaseWasPressed = false;
    private boolean decreaseWasPressed = false;

    public void setKeyBindings() {
        setKeyBindingGammaToggle();
        setKeyBindingIncreaseGamma();
        setKeyBindingDecreaseGamma();
    }

    private void setKeyBindingGammaToggle() {
        KeyBinding gammaToggleKey = new KeyBinding("key.gamma_utils.gamma_toggle", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, CATEGORY);
        KeyBindingHelper.registerKeyBinding(gammaToggleKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (gammaToggleKey.wasPressed()) {
                gammaUtils.toggleGamma();
                toggleWasPressed = true;
            }
            //only save the options once the key has been released.
            if (toggleWasPressed && !gammaToggleKey.isPressed()) {
                gammaUtils.saveOptions();
                toggleWasPressed = false;
            }
        });
    }

    private void setKeyBindingIncreaseGamma() {
        KeyBinding increaseGammaKey = new KeyBinding("key.gamma_utils.increase_gamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, CATEGORY);
        KeyBindingHelper.registerKeyBinding(increaseGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (increaseGammaKey.wasPressed()) {
                gammaUtils.increaseGamma();
                increaseWasPressed = true;
            }
            //only save the options once the key has been released.
            if (increaseWasPressed && !increaseGammaKey.isPressed()) {
                gammaUtils.saveOptions();
                increaseWasPressed = false;
            }
        });
    }

    private void setKeyBindingDecreaseGamma() {
        KeyBinding decreaseGammaKey = new KeyBinding("key.gamma_utils.decrease_gamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, CATEGORY);
        KeyBindingHelper.registerKeyBinding(decreaseGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (decreaseGammaKey.wasPressed()) {
                gammaUtils.decreaseGamma();
                decreaseWasPressed = true;
            }
            //only save the options once the key has been released.
            if (decreaseWasPressed && !decreaseGammaKey.isPressed()) {
                gammaUtils.saveOptions();
                decreaseWasPressed = false;
            }
        });
    }
}
