package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.statuseffect.ClientStatusEffect;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(Hud.class)
public class MixinHud {

    /**
     * Mixin to add client side status effects to the HUD
     */
    @ModifyExpressionValue(method = "extractEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getActiveEffects()Ljava/util/Collection;"))
    private static Collection<MobEffectInstance> extractClientEffects(Collection<MobEffectInstance> original) {
        return StatusEffectManager.addActiveEffects(original);
    }

    /**
     * Mixin to provide the client StutusEffect Identifier, is needed because they aren't actually registered
     */
    @Inject(method = "getMobEffectSprite", at = @At("HEAD"), cancellable = true)
    private static void getGammaTexture(Holder<MobEffect> effect, CallbackInfoReturnable<Identifier> info) {
        if (effect.value() instanceof ClientStatusEffect clientEffect) {
            info.setReturnValue(clientEffect.getIdentifier());
        }
    }
}
