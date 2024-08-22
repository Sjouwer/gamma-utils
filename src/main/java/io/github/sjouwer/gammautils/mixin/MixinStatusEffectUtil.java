package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectUtil.class)
public class MixinStatusEffectUtil {

    /**
     * Mixin to show the gamma or night vision percentage instead of the StatusEffect duration
     */
    @Inject(method = "getDurationText", at = @At("HEAD"), cancellable = true)
    private static void getPercentageText(StatusEffectInstance effect, float multiplier, float tickRate, CallbackInfoReturnable<Text> info) {
        if (effect.getEffectType().value() instanceof GammaStatusEffect) {
            int gamma = GammaManager.getGammaPercentage();
            info.setReturnValue(Text.literal(gamma + "%"));
        }
        if (effect.getEffectType().equals(StatusEffects.NIGHT_VISION)) {
            int nightVision = NightVisionManager.getNightVisionPercentage();
            info.setReturnValue(Text.literal(nightVision + "%"));
        }
    }
}
