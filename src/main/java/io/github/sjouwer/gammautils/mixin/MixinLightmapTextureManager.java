package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightmapTextureManager.class)
abstract class MixinLightmapTextureManager {
    @Unique
    private static final ModConfig config = GammaUtils.getConfig();

    /**
     * Mixin to use mod gamma value
     */
    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
    private float getModGammaValue(float original) {
        return (float) config.gamma.getValue();
    }

    /**
     * Mixin needed to allow negative gamma
     */
    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 0))
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
        return config.nightVision.isEnabled() || config.nightVision.isDynamicEnabled() || original;
    }
}
