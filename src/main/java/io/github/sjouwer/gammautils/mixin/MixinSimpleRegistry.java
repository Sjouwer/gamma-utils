package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.statuseffect.BrightStatusEffect;
import io.github.sjouwer.gammautils.statuseffect.DimStatusEffect;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleRegistry.class)
public class MixinSimpleRegistry<T> {
    @Inject(method = "createEntry", at = @At("HEAD"), cancellable = true)
    private void allowUnregisteredGammaEffects(T value, CallbackInfoReturnable<RegistryEntry.Reference<T>> info) {
        if (value.getClass().equals(BrightStatusEffect.class) || value.getClass().equals(DimStatusEffect.class)) {
            info.setReturnValue(null);
        }
    }
}
