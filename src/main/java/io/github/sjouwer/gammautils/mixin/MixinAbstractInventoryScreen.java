package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractInventoryScreen.class)
public class MixinAbstractInventoryScreen {

    /**
     * Mixin to show the gamma or night vision percentage instead of the StatusEffect duration
     */
    @ModifyExpressionValue(method = "drawStatusEffectDescriptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/effect/StatusEffectUtil;getDurationText(Lnet/minecraft/entity/effect/StatusEffectInstance;FF)Lnet/minecraft/text/Text;", ordinal = 0))
    private Text getPercentageText(Text original, @Local(ordinal = 0) StatusEffectInstance effect) {
        if (effect.getEffectType().value() instanceof GammaStatusEffect) {
            int gamma = GammaManager.getGammaPercentage();
            return Text.literal(gamma + "%");
        }
        if (effect.getEffectType().equals(StatusEffects.NIGHT_VISION) && GammaUtils.getConfig().nightVision.isEnabled()) {
            int nightVision = NightVisionManager.getNightVisionPercentage();
            return Text.literal(nightVision + "%");
        }

        return original;
    }
}
