package io.github.sjouwer.gammautils.mixin;

import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SimpleOption.class)
public class MixinSimpleOption<T> {
    @Shadow @Final
    private Text key;

    @Shadow
    T value;

    @Inject(method = "setValue", at = @At("HEAD"), cancellable = true)
    private void setValue(T object, CallbackInfo info) {
        if (key.getString().equals("Brightness")) {
            value = object;
            info.cancel();
        }
    }
}
