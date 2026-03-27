package io.github.sjouwer.gammautils.statuseffect;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;

public class StatusEffectManager {
    private static final Minecraft client = Minecraft.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();
    public static final Holder.Direct<MobEffect> BRIGHT = new Holder.Direct<>(
            new GammaStatusEffect("bright", MobEffectCategory.BENEFICIAL, 0));
    public static final Holder.Direct<MobEffect> DIM = new Holder.Direct<>(
            new GammaStatusEffect("dim", MobEffectCategory.HARMFUL, 0));
    public static final Holder.Direct<MobEffect> NIGHT_VISION = new Holder.Direct<>(
            new GammaStatusEffect("night_vision", MobEffectCategory.BENEFICIAL, 0));

    private StatusEffectManager() {
    }

    public static void updateAllEffects() {
        updateGammaStatusEffect();
        updateNightVision();
    }

    public static void updateNightVision() {
        LocalPlayer player = client.player;
        if (player == null) {
            return;
        }

        if (config.nightVision.isEnabled() && config.nightVision.isStatusEffectEnabled()) {
            addPermEffect(player, NIGHT_VISION);
        }
        else {
            player.removeEffect(NIGHT_VISION);
        }
    }

    public static void updateGammaStatusEffect() {
        LocalPlayer player = client.player;
        if (player == null) {
            return;
        }

        if (config.gamma.isStatusEffectEnabled()) {
            int gamma = GammaManager.getGammaPercentage();
            if (gamma > 100) {
                if (!player.hasEffect(BRIGHT)) {
                    player.removeEffect(DIM);
                    addPermEffect(player, BRIGHT);
                }
                return;
            }
            else if (gamma < 0) {
                if (!player.hasEffect(DIM)) {
                    player.removeEffect(BRIGHT);
                    addPermEffect(player, DIM);
                }
                return;
            }
        }
        player.removeEffect(DIM);
        player.removeEffect(BRIGHT);
    }

    private static void addPermEffect(LocalPlayer player, Holder<MobEffect> effect) {
        MobEffectInstance statusEffect = new MobEffectInstance(effect, -1);
        player.addEffect(statusEffect);
    }
}
