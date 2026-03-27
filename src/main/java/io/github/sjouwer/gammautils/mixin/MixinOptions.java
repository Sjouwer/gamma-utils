package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Options;
import net.minecraft.client.OptionInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Options.class)
public class MixinOptions<T> {

    /**
     * Mixin to skip reading and writing the options file for the gamma option
     */
    @WrapOperation(method = "processDumpedOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options$OptionAccess;process(Ljava/lang/String;Lnet/minecraft/client/OptionInstance;)V"))
    private void doNotVisitGamma(Options.OptionAccess instance, String key, OptionInstance<T> option, Operation<Void> original) {
        if (!key.equals("gamma")) {
            original.call(instance, key, option);
        }
    }
}