package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightmapTextureManager.class)
abstract class MixinLightmapTextureManager {

    /**
     * Mixin needed to allow negative gamma
     */
    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 2))
    private float allowNegativeGamma(float original) {
        float gamma = (float) GammaManager.getGamma();
        if (gamma < 0) {
            return gamma;
        }

        return original;
    }

    /**
     * Mixin to allow Night Vision without Status Effect
     */
    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;hasStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z", ordinal = 0))
    private boolean hasNightVision(boolean original) {
        return GammaUtils.getConfig().nightVision.isEnabled() || original;
    }
}
