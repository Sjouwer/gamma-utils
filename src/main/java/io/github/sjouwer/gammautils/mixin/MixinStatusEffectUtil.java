package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaOptions;
import io.github.sjouwer.gammautils.GammaUtils;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectUtil.class)
public class MixinStatusEffectUtil {

    @Inject(method = "getDurationText", at = @At("HEAD"), cancellable = true)
    private static void getGammaText(StatusEffectInstance effect, float multiplier, CallbackInfoReturnable<Text> info) {
        if (effect.getEffectType().equals(GammaUtils.BRIGHT) || effect.getEffectType().equals(GammaUtils.DIM)) {
            int gamma = GammaOptions.getGammaPercentage();
            info.setReturnValue(Text.literal(gamma + "%"));
        }
    }
}
