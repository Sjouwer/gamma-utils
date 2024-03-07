package io.github.sjouwer.gammautils.statuseffect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

public class GammaStatusEffect extends StatusEffect {
    private static final String NAMESPACE = "gammautils";
    private final String key;

    public GammaStatusEffect(String key, StatusEffectCategory category, int color) {
        super(category, color);
        this.key = key;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return false;
    }

    @Override
    protected String loadTranslationKey() {
        return "effect." + NAMESPACE + "." + key;
    }

    public Identifier getIdentifier() {
        return new Identifier(NAMESPACE, key);
    }
}
