package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectUtil.class)
public class MixinMobEffectUtil {

    /**
     * Mixin to show the gamma or night vision percentage instead of the StatusEffect duration
     */
    @Inject(method = "formatDuration", at = @At(value = "HEAD"), cancellable = true)
    private static void getPercentageText(MobEffectInstance effect, float multiplier, float tickRate, CallbackInfoReturnable<Component> info) {
        Holder<MobEffect> type = effect.getEffect();
        if ((type.value() instanceof GammaStatusEffect)) {
            int percentage = type.equals(StatusEffectManager.NIGHT_VISION)
                    ? NightVisionManager.getNightVisionPercentage()
                    : GammaManager.getGammaPercentage();

            info.setReturnValue(Component.literal(percentage + "%"));
        }
    }
}
