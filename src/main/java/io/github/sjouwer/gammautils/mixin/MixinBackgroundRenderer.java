package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BackgroundRenderer.class)
abstract class MixinBackgroundRenderer {

    /**
     * Mixin to allow Night Vision fog color without Status Effect
     */
    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z", ordinal = 0))
    private static boolean hasNightVision(boolean original) {
        ModConfig.NightVisionSettings nightVision = GammaUtils.getConfig().nightVision;
        return  (nightVision.isFogColorBrighteningEnabled() && (nightVision.isEnabled() || nightVision.isDynamicEnabled())) || original;
    }
}
