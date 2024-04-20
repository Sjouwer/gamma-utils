package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.sjouwer.gammautils.GammaUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Mixin(AbstractInventoryScreen.class)
abstract class MixinInventoryScreen {

    /**
     * Mixin to hide the night vision status effect bar inside the inventory
     */
    @ModifyExpressionValue(method = "drawStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getStatusEffects()Ljava/util/Collection;", ordinal = 0))
    private Collection<StatusEffectInstance> getFilteredStatusEffects(Collection<StatusEffectInstance> original) {
        Collection<StatusEffectInstance> statusEffects = new ArrayList<>(MinecraftClient.getInstance().player.getStatusEffects());
        Optional<StatusEffectInstance> nightVision = statusEffects.stream().filter(se -> se.equals(StatusEffects.NIGHT_VISION)).findFirst();
        if (nightVision.isPresent() && !GammaUtils.getConfig().isNightVisionIconEnabled() && GammaUtils.getConfig().isNightVisionEnabled()) {
            statusEffects.remove(nightVision.get());
            return statusEffects;
        }

        return original;
    }
}
