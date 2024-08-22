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

    @Inject(method = "close", at = @At("HEAD"))
    private void saveOnClose(CallbackInfo info) {
        ModConfig config = GammaUtils.getConfig();
        if (config.isResetGammaOnCloseEnabled()) {
            options.getGamma().setValue(config.getDefaultGamma());
        }
        if (config.isResetNightVisionOnCloseEnabled()) {
            config.setNightVision(false);
        }
        options.write();
        GammaUtils.saveConfig();
    }
}