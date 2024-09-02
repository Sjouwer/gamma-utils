package io.github.sjouwer.gammautils.statuseffect;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

public class StatusEffectManager {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();
    public static final RegistryEntry.Direct<StatusEffect> BRIGHT = new RegistryEntry.Direct<>(
            new GammaStatusEffect("bright", StatusEffectCategory.BENEFICIAL, 0));
    public static final RegistryEntry.Direct<StatusEffect> DIM = new RegistryEntry.Direct<>(
            new GammaStatusEffect("dim", StatusEffectCategory.HARMFUL, 0));

    private StatusEffectManager() {
    }

    public static void updateAllEffects() {
        updateGammaStatusEffect();
        updateNightVision();
    }

    public static void updateNightVision() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (config.nightVision.isEnabled() && config.nightVision.isIconEnabled()) {
            addPermEffect(player, StatusEffects.NIGHT_VISION);
        }
        else {
            player.removeStatusEffect(StatusEffects.NIGHT_VISION);
        }
    }

    public static void updateGammaStatusEffect() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (config.gamma.isStatusEffectEnabled()) {
            int gamma = GammaManager.getGammaPercentage();
            if (gamma > 100) {
                if (!player.hasStatusEffect(BRIGHT)) {
                    player.removeStatusEffect(DIM);
                    addPermEffect(player, BRIGHT);
                }
                return;
            }
            else if (gamma < 0) {
                if (!player.hasStatusEffect(DIM)) {
                    player.removeStatusEffect(BRIGHT);
                    addPermEffect(player, DIM);
                }
                return;
            }
        }
        player.removeStatusEffect(DIM);
        player.removeStatusEffect(BRIGHT);
    }

    private static void addPermEffect(ClientPlayerEntity player, RegistryEntry<StatusEffect> effect) {
        StatusEffectInstance statusEffect = new StatusEffectInstance(effect, -1);
        player.addStatusEffect(statusEffect);
    }
}
