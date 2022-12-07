package io.github.sjouwer.gammautils.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightmapTextureManager.class)
abstract class MixinLightmapTextureManager {
    @Shadow
    @Final
    private MinecraftClient client;

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 2))
    private float max(float a, float b) {
        float gamma = client.options.getGamma().getValue().floatValue();
        if (gamma < 0) {
            return gamma;
        }
        return Math.max(a, b);
    }
}
