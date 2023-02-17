package io.github.sjouwer.gammautils.mixin;

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

    @Inject(method = "onPlayerAbilities", at = @At("TAIL"))
    private void onPlayerAbilities(PlayerAbilitiesS2CPacket packet, CallbackInfo info) {
        StatusEffectManager.updateAllEffects();
    }

    @Inject(method = "onPlayerRespawn", at = @At("TAIL"))
    private void onPlayerRespawn(PlayerRespawnS2CPacket packet, CallbackInfo info) {
        StatusEffectManager.updateAllEffects();
    }
}
