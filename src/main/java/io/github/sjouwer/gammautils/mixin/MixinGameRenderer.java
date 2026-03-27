package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
public abstract class MixinGameRenderer {

    /**
     * Mixin to adjust the night vision strength
     */
    @WrapMethod(method = "getNightVisionScale")
    private static float adjustNightVisionStrength(LivingEntity entity, float tickProgress, Operation<Float> original) {
        ModConfig config = GammaUtils.getConfig();
        float strength;

        if (config.nightVision.isEnabled() || config.nightVision.isDynamicEnabled()) {
            strength = (float) (config.nightVision.getValue() / 100f);
        }
        else {
            strength = original.call(entity, tickProgress);
        }

        return strength;
    }
}
