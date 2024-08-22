package io.github.sjouwer.gammautils.mixin;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.NightVisionManager;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class MixinClientPlayNetworkHandler {

    /**
     * Mixin to enable client side status effects on world load etc
     */
    @Inject(method = "onPlayerAbilities", at = @At("TAIL"))
    private void onPlayerAbilities(PlayerAbilitiesS2CPacket packet, CallbackInfo info) {
        StatusEffectManager.updateAllEffects();
        GammaManager.setDimensionPreference();
        NightVisionManager.setDimensionPreference();
    }

    /**
     * Mixin to make sure the client side status effects remain enabled after death
     */
    @Inject(method = "onPlayerRespawn", at = @At("TAIL"))
    private void onPlayerRespawn(PlayerRespawnS2CPacket packet, CallbackInfo info) {
        StatusEffectManager.updateAllEffects();
    }
}
