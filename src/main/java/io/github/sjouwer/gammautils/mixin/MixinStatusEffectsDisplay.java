package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StatusEffectsDisplay.class)
public class MixinStatusEffectsDisplay {

    /**
     * Mixin to show the gamma or night vision percentage instead of the StatusEffect duration
     */
    @ModifyExpressionValue(method = "drawStatusEffectDescriptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectUtil;getDurationText(Lnet/minecraft/entity/effect/StatusEffectInstance;FF)Lnet/minecraft/text/Text;", ordinal = 0))
    private Text getPercentageText(Text original, @Local(ordinal = 0) StatusEffectInstance effect) {
        RegistryEntry<StatusEffect> type = effect.getEffectType();
        if (!(type.value() instanceof GammaStatusEffect)) {
            return original;
        }

        int percentage = type.equals(StatusEffectManager.NIGHT_VISION)
                ? NightVisionManager.getNightVisionPercentage()
                : GammaManager.getGammaPercentage();

        return Text.literal(percentage + "%");
    }
}
