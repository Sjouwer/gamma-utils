package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collection;

@Mixin(LivingEntity.class)
abstract class MixinLivingEntity {

    /**
     * Mixin to prevent the game from trying to encode client side status effects
     */
    @ModifyExpressionValue(method = "addAdditionalSaveData", at = @At(value = "INVOKE", target = "Ljava/util/Map;values()Ljava/util/Collection;", ordinal = 0))
    private Collection<MobEffectInstance> values(Collection<MobEffectInstance> original) {
        Collection<MobEffectInstance> statusEffects = new ArrayList<>(original);
        statusEffects.removeIf(i -> i.getEffect().value() instanceof GammaStatusEffect);
        return statusEffects;
    }
}
