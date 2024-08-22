package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    /**
     * Mixin to adjust the night vision strength
     */
    @Inject(method = "getNightVisionStrength", at = @At("HEAD"), cancellable = true)
    private static void adjustNightVisionStrength(LivingEntity entity, float tickDelta, CallbackInfoReturnable<Float> info) {
        ModConfig config = GammaUtils.getConfig();
        if (config.nightVision.isEnabled()) {
            info.setReturnValue((float) (config.nightVision.getStrength() / 100f));
        }
    }
}
