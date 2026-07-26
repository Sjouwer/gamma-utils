package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(LivingEntity.class)
abstract class MixinLivingEntity {

    /**
     * Mixin to prevent the game from trying to encode client side status effects
     */
    @ModifyExpressionValue(method = "writeCustomDataToNbt", at = @At(value = "INVOKE", target = "Ljava/util/Map;values()Ljava/util/Collection;", ordinal = 0))
    private Collection<StatusEffectInstance> values(Collection<StatusEffectInstance> original) {
        Collection<StatusEffectInstance> statusEffects = new ArrayList<>(original);
        statusEffects.removeIf(i -> i.getEffectType().value() instanceof GammaStatusEffect);
        return statusEffects;
    }
}
