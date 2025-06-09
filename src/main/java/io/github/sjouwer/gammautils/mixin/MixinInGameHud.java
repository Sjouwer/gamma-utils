package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.statuseffect.GammaStatusEffect;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.class)
public class MixinInGameHud {

    /**
     * Mixin to provide the gamma StutusEffect Identifier, is needed because they aren't actually registered
     */
    @Inject(method = "getEffectTexture", at = @At("HEAD"), cancellable = true)
    private static void getGammaTexture(RegistryEntry<StatusEffect> effect, CallbackInfoReturnable<Identifier> info) {
        if (effect.value() instanceof GammaStatusEffect gammaEffect) {
            info.setReturnValue(gammaEffect.getIdentifier());
        }
    }
}
