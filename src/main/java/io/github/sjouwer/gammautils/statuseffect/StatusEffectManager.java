package io.github.sjouwer.gammautils.statuseffect;

import io.github.sjouwer.gammautils.GammaManager;
import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.core.Holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StatusEffectManager {
    private static final ModConfig config = GammaUtils.getConfig();

    public static final Holder.Direct<MobEffect> BRIGHT = new Holder.Direct<>(
            new ClientStatusEffect("bright", MobEffectCategory.BENEFICIAL, 0), DataComponentMap.EMPTY);

    public static final Holder.Direct<MobEffect> DIM = new Holder.Direct<>(
            new ClientStatusEffect("dim", MobEffectCategory.HARMFUL, 0), DataComponentMap.EMPTY);

    public static final Holder.Direct<MobEffect> NIGHT_VISION = new Holder.Direct<>(
            new ClientStatusEffect("night_vision", MobEffectCategory.BENEFICIAL, 0), DataComponentMap.EMPTY);

    private static final MobEffectInstance BRIGHT_INSTANCE = new MobEffectInstance(BRIGHT, -1);
    private static final MobEffectInstance DIM_INSTANCE = new MobEffectInstance(DIM, -1);
    private static final MobEffectInstance NIGHT_VISION_INSTANCE = new MobEffectInstance(NIGHT_VISION, -1);
    private static final List<MobEffectInstance> ACTIVE_EFFECTS = new ArrayList<>();

    private StatusEffectManager() {
    }

    public static void updateAllEffects() {
        updateGammaEffect();
        updateNightVisionEffect();
    }

    public static Collection<MobEffectInstance> addActiveEffects(Collection<MobEffectInstance> statusEffects) {
        if (ACTIVE_EFFECTS.isEmpty()) {
            return statusEffects;
        }

        List<MobEffectInstance> copy = new ArrayList<>(statusEffects);
        copy.addAll(ACTIVE_EFFECTS);
        return copy;
    }

    public static void updateNightVisionEffect() {
        if (config.nightVision.isEnabled() && config.nightVision.isStatusEffectEnabled()) {
            if (!ACTIVE_EFFECTS.contains(NIGHT_VISION_INSTANCE)) {
                ACTIVE_EFFECTS.add(NIGHT_VISION_INSTANCE);
            }
        }
        else {
            ACTIVE_EFFECTS.remove(NIGHT_VISION_INSTANCE);
        }
    }

    public static void updateGammaEffect() {
        if (!config.gamma.isStatusEffectEnabled()) {
            ACTIVE_EFFECTS.remove(BRIGHT_INSTANCE);
            ACTIVE_EFFECTS.remove(DIM_INSTANCE);
            return;
        }

        int gamma = GammaManager.getGammaPercentage();

        if (gamma > 100) {
            if (!ACTIVE_EFFECTS.contains(BRIGHT_INSTANCE)) {
                ACTIVE_EFFECTS.add(BRIGHT_INSTANCE);
            }
        } else {
            ACTIVE_EFFECTS.remove(BRIGHT_INSTANCE);
        }

        if (gamma < 0) {
            if (!ACTIVE_EFFECTS.contains(DIM_INSTANCE)) {
                ACTIVE_EFFECTS.add(DIM_INSTANCE);
            }
        } else {
            ACTIVE_EFFECTS.remove(DIM_INSTANCE);
        }
    }
}
