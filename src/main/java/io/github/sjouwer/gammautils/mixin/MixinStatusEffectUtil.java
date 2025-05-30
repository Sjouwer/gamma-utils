package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.registry.entry.RegistryEntry;
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
    @Inject(method = "getDurationText", at = @At(value = "HEAD"), cancellable = true)
    private static void getPercentageText(StatusEffectInstance effect, float multiplier, float tickRate, CallbackInfoReturnable<Text> info) {
        RegistryEntry<StatusEffect> type = effect.getEffectType();
        if ((type.value() instanceof GammaStatusEffect)) {
            int percentage = type.equals(StatusEffectManager.NIGHT_VISION)
                    ? NightVisionManager.getNightVisionPercentage()
                    : GammaManager.getGammaPercentage();

            info.setReturnValue(Text.literal(percentage + "%"));
        }
    }
}
