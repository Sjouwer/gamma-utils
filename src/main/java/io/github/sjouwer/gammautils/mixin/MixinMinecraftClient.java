package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Shadow
    @Final
    public GameOptions options;

    /**
     * Mixin to make sure everything is properly saved when closing the game
     */
    @Inject(method = "close", at = @At("HEAD"))
    private void saveOnClose(CallbackInfo info) {
        ModConfig config = GammaUtils.getConfig();

        if (config.gamma.isResetOnCloseEnabled()) {
            config.gamma.setValue(config.gamma.getDefaultValue());
        }

        if (config.nightVision.isResetOnCloseEnabled()) {
            config.nightVision.setStatus(false);
        }

        GammaUtils.saveConfig();
    }
}