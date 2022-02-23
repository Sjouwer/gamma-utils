package io.github.sjouwer.gammautils.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class BrightStatusEffect extends StatusEffect {
    public BrightStatusEffect() {
        super(
                StatusEffectType.BENEFICIAL, 0);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }
}
