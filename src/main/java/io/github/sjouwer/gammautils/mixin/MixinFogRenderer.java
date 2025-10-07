package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.render.fog.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FogRenderer.class)
abstract class MixinFogRenderer {

    /**
     * Mixin to allow Night Vision fog color without Status Effect
     */
    @ModifyExpressionValue(method = "getFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z", ordinal = 0))
    private boolean hasNightVision(boolean original) {
        ModConfig.NightVisionSettings nightVision = GammaUtils.getConfig().nightVision;
        return  (nightVision.isFogColorBrighteningEnabled() && (nightVision.isEnabled() || nightVision.isDynamicEnabled())) || original;
    }
}
