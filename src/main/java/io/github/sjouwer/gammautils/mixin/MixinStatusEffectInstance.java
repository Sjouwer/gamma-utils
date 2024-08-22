package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public class MixinStatusEffectInstance {

    @Shadow
    @Final
    private RegistryEntry<StatusEffect> type;

    /**
     * Mixin to hide the night vision status icon in the upper right corner of the HUD
     */
    @Inject(method = "shouldShowIcon", at = @At("HEAD"), cancellable = true)
    private void hideNightVisionIcon(CallbackInfoReturnable<Boolean> info) {
        ModConfig config = GammaUtils.getConfig();
        if (type.equals(StatusEffects.NIGHT_VISION) && !config.nightVision.isIconEnabled() && config.nightVision.isEnabled()) {
            info.setReturnValue(false);
        }
    }
}
