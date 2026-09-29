package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(EffectsInInventory.class)
public class MixinEffectsInInventory {

    /**
     * Mixin to add client side status effects to the inventory UI
     */
    @ModifyExpressionValue(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getActiveEffects()Ljava/util/Collection;"))
    private static Collection<MobEffectInstance> extractClientEffects(Collection<MobEffectInstance> original) {
        return StatusEffectManager.addActiveEffects(original);
    }
}
