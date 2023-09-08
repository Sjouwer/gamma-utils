package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaUtils;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
    private StatusEffect type;

    @Inject(method = "shouldShowIcon", at = @At("HEAD"), cancellable = true)
    private void hideNightVisionIcon(CallbackInfoReturnable<Boolean> info) {
        if (type.equals(StatusEffects.NIGHT_VISION) && !GammaUtils.getConfig().isNightVisionIconShown() && GammaUtils.getConfig().isNightVisionEnabled()) {
            info.setReturnValue(false);
        }
    }
}
