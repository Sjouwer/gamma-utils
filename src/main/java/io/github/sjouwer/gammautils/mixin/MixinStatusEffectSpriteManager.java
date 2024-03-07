package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectSpriteManager.class)
public class MixinStatusEffectSpriteManager {

    @Inject(method = "getSprite", at = @At("HEAD"), cancellable = true)
    private void getGammaSprite(RegistryEntry<StatusEffect> effect, CallbackInfoReturnable<Sprite> info) {
        if (effect.equals(StatusEffectManager.BRIGHT)) {
            info.setReturnValue(((SpriteAtlasHolderInvoker) this).invokeGetSprite(new Identifier("gammautils", "bright")));
        }

        if (effect.equals(StatusEffectManager.DIM)) {
            info.setReturnValue(((SpriteAtlasHolderInvoker) this).invokeGetSprite(new Identifier("gammautils", "dim")));
        }
    }
}
