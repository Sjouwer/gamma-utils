package gamma.utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    private final GammaUtils gammaUtils = new GammaUtils();
    private static final String CATEGORY = "key.categories.gamma_utils";

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
            }
        });
    }

    private void setKeyBindingIncreaseGamma() {
        KeyBinding increaseGammaKey = new KeyBinding("key.gamma_utils.increase_gamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UP, CATEGORY);
        KeyBindingHelper.registerKeyBinding(increaseGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (increaseGammaKey.wasPressed()) {
                gammaUtils.increaseGamma();
            }
        });
    }

    private void setKeyBindingDecreaseGamma() {
        KeyBinding decreaseGammaKey = new KeyBinding("key.gamma_utils.decrease_gamma", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_DOWN, CATEGORY);
        KeyBindingHelper.registerKeyBinding(decreaseGammaKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (decreaseGammaKey.wasPressed()) {
                gammaUtils.decreaseGamma();
            }
        });
    }
}
