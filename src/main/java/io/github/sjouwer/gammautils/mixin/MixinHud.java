package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import net.minecraft.client.gui.Hud;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Hud.class)
public class MixinHud {

    /**
     * Mixin to provide the gamma StutusEffect Identifier, is needed because they aren't actually registered
     */
    @Inject(method = "getMobEffectSprite", at = @At("HEAD"), cancellable = true)
    private static void getGammaTexture(Holder<MobEffect> effect, CallbackInfoReturnable<Identifier> info) {
        if (effect.value() instanceof GammaStatusEffect gammaEffect) {
            info.setReturnValue(gammaEffect.getIdentifier());
        }
    }
}
