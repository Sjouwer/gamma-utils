package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaOptions;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
abstract class MixinLightmapTextureManager {

    /**
     * Mixin needed for allowing negative gamma
     * Value a is always 0
     * Value b is a combination of Gamma with a possible Darkness effect
     * To prevent any Darkness issues, negative gamma is only returned when gamma (and not b) is below 0
     */
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 2))
    private float allowNegativeGamma(float a, float b) {
        float gamma = (float)GammaOptions.getGamma();
        if (gamma < 0) {
            return gamma;
        }

        return Math.max(a, b);
    }
}
