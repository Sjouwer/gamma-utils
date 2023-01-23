package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.util.ISimpleOption;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Shadow
    private GameOptions options;

    @Inject(method = "close", at = @At("HEAD"))
    private void close(CallbackInfo info) {
        ModConfig config = GammaUtils.getConfig();
        if (config.isResetOnCloseEnabled()) {
            ISimpleOption.setGamma(config.getDefaultGamma());
            config.setNightVision(false);
        }
        options.write();
        AutoConfig.getConfigHolder(ModConfig.class).save();
    }
}