package io.github.sjouwer.gammautils.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class DimStatusEffect extends StatusEffect {
    public DimStatusEffect() {
        super(
                StatusEffectType.HARMFUL, 0);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }
}
