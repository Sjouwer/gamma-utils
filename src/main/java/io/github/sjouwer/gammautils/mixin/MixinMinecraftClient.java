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
     * Mixin to make sure everything is properly and neatly saved when closing the game
     */
    @Inject(method = "close", at = @At("HEAD"))
    private void saveOnClose(CallbackInfo info) {
        ModConfig config = GammaUtils.getConfig();
        double gamma = options.getGamma().getValue();

        if (config.gamma.isResetOnCloseEnabled()) {
            gamma = config.gamma.getDefaultStrength();
        }

        if (config.nightVision.isResetOnCloseEnabled()) {
            config.nightVision.setStatus(false);
        }

        options.getGamma().setValue((Math.round(gamma * 100.0) / 100.0));
        options.write();
        GammaUtils.saveConfig();
    }
}