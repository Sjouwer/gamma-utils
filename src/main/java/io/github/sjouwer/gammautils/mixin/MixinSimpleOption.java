package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleOption.class)
public class MixinSimpleOption {
    @Unique
    private static final ModConfig.GammaSettings gamma = GammaUtils.getConfig().gamma;

    @Shadow
    @Final
    Text text;

    /**
     * Mixin to return the gamma value of this mod instead of the vanilla one
     */
    @Inject(method = "getValue", at = @At("HEAD"), cancellable = true)
    public void getModValue(CallbackInfoReturnable<Double> info) {
        if (text.getString().equals(I18n.translate("options.gamma"))) {
            info.setReturnValue(gamma.getValue());
        }
    }
}
