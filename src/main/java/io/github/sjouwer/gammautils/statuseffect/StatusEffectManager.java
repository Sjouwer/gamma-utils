package io.github.sjouwer.gammautils.statuseffect;

import io.github.sjouwer.gammautils.GammaOptions;
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

    public static void updateAllEffects() {
        updateGammaStatusEffect();
        updateNightVision();
    }

    public static void toggleNightVision() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (player.hasStatusEffect(StatusEffects.NIGHT_VISION)) {
            disableNightVision(player);
            InfoProvider.showNightVisionHudMessage(false);
        }
        else {
            enableNightVision(player);
            InfoProvider.showNightVisionHudMessage(true);
        }
    }

    public static void updateNightVision() {
        if (client.player == null) {
            return;
        }

        if (config.isNightVisionEnabled()) {
            enableNightVision(client.player);
        }
    }

    private static void enableNightVision(ClientPlayerEntity player) {
        addPermEffect(player, StatusEffects.NIGHT_VISION);
        config.setNightVision(true);
    }

    private static void disableNightVision(ClientPlayerEntity player) {
        player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        config.setNightVision(false);
    }

    public static void updateGammaStatusEffect() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (config.isStatusEffectEnabled()) {
            int gamma = GammaOptions.getGammaPercentage();
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
        StatusEffectInstance statusEffect = new StatusEffectInstance(effect, -1);
        player.addStatusEffect(statusEffect);
    }
}
