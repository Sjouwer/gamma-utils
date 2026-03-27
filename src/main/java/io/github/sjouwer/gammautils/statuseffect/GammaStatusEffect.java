package io.github.sjouwer.gammautils.statuseffect;

import io.github.sjouwer.gammautils.GammaUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.resources.Identifier;

public class GammaStatusEffect extends MobEffect {
    private final String key;

    public GammaStatusEffect(String key, MobEffectCategory category, int color) {
        super(category, color);
        this.key = key;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }

    @Override
    protected String getOrCreateDescriptionId() {
        return "effect." + GammaUtils.NAMESPACE + "." + key;
    }

    public Identifier getIdentifier() {
        return Identifier.fromNamespaceAndPath(GammaUtils.NAMESPACE, "mob_effect/" + key);
    }
}
