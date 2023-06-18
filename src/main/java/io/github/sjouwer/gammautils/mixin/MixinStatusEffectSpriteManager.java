package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaUtils;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectSpriteManager.class)
public class MixinStatusEffectSpriteManager {

    @Inject(method = "getSprite", at = @At("HEAD"), cancellable = true)
    private void getGammaSprite(StatusEffect effect, CallbackInfoReturnable<Sprite> info) {
        if (effect.equals(GammaUtils.BRIGHT)) {
            info.setReturnValue(((SpriteAtlasHolderInvoker) this).invokeGetSprite(new Identifier("gammautils", "bright")));
        }

        if (effect.equals(GammaUtils.DIM)) {
            info.setReturnValue(((SpriteAtlasHolderInvoker) this).invokeGetSprite(new Identifier("gammautils", "dim")));
        }
    }
}
