package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaOptions;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
abstract class MixinLightmapTextureManager {

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 2))
    private float allowNegativeGamma(float a, float b) {
        float gamma = (float)GammaOptions.getGamma();
        if (gamma < 0) {
            return gamma;
        }

        return Math.max(a, b);
    }
}
