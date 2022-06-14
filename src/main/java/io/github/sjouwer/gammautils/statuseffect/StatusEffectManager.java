package io.github.sjouwer.gammautils.statuseffect;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

public class StatusEffectManager {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();

    private StatusEffectManager() {
    }

    public static void toggleNightVision() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
            disableNightVision();
            InfoProvider.showNightVisionHudMessage(false);
        }
        else {
            enableNightVision();
            InfoProvider.showNightVisionHudMessage(true);
        }
    }

    public static void updateNightVision() {
        if (config.isNightVisionEnabled()) {
            enableNightVision();
        }
    }

    private static void enableNightVision() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        addPermEffect(player, StatusEffects.NIGHT_VISION);
        config.setNightVision(true);
    }

    private static void disableNightVision() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        config.setNightVision(false);
    }

    public static void updateGammaStatusEffect() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (config.statusEffectEnabled()) {
            int gamma = (int)Math.round(client.options.getGamma().getValue() * 100);
            if (gamma > 100) {
                if (!player.hasStatusEffect(GammaUtils.BRIGHT)) {
                    player.removeStatusEffect(GammaUtils.DIM);
                    addPermEffect(player, GammaUtils.BRIGHT);
                }
                return;
            }
            else if (gamma < 0) {
                if (!player.hasStatusEffect(GammaUtils.DIM)) {
                    player.removeStatusEffect(GammaUtils.BRIGHT);
                    addPermEffect(player, GammaUtils.DIM);
                }
                return;
            }
        }
        player.removeStatusEffect(GammaUtils.DIM);
        player.removeStatusEffect(GammaUtils.BRIGHT);
    }

    private static void addPermEffect(ClientPlayerEntity player, StatusEffect effect) {
        StatusEffectInstance statusEffect = new StatusEffectInstance(effect, 999999);
        statusEffect.setPermanent(true);
        player.addStatusEffect(statusEffect);
    }
}
