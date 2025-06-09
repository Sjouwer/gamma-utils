package io.github.sjouwer.gammautils.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GameOptions.class)
public class MixinGameOptions<T> {

    /**
     * Mixin to skip reading and writing the options file for the gamma option
     */
    @WrapOperation(method = "acceptProfiledOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/GameOptions$OptionVisitor;accept(Ljava/lang/String;Lnet/minecraft/client/option/SimpleOption;)V"))
    private void doNotVisitGamma(GameOptions.OptionVisitor instance, String key, SimpleOption<T> option, Operation<Void> original) {
        if (!key.equals("gamma")) {
            original.call(instance, key, option);
        }
    }
}