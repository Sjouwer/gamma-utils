package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectSpriteManager.class)
public class MixinStatusEffectSpriteManager {

    /**
     * Mixin to provide the gamma StutusEffect sprites, is needed because they aren't actually registered
     */
    @Inject(method = "getSprite", at = @At("HEAD"), cancellable = true)
    private void getGammaSprite(RegistryEntry<StatusEffect> effect, CallbackInfoReturnable<Sprite> info) {
        if (effect.value() instanceof GammaStatusEffect gammaEffect) {
            info.setReturnValue(((SpriteAtlasHolderInvoker) this).invokeGetSprite(gammaEffect.getIdentifier()));
        }
    }
}
