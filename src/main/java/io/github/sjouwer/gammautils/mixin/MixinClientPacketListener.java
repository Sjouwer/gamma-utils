package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.NightVisionManager;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {

    /**
     * Mixin to set dimension preferences on world load etc
     */
    @Inject(method = "handlePlayerAbilities", at = @At("TAIL"))
    private void setDimensionPreferences(ClientboundPlayerAbilitiesPacket packet, CallbackInfo info) {
        GammaManager.setDimensionPreference();
        NightVisionManager.setDimensionPreference();
    }
}
