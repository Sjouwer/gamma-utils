package io.github.sjouwer.gammautils.statuseffect;

import com.mojang.datafixers.util.Either;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

//Fake StatusEffect RegistryEntry to make sure it stays fully client side
public class StatusEffectRegistryEntry implements RegistryEntry<StatusEffect> {
    private final StatusEffect statusEffect;

    public StatusEffectRegistryEntry(StatusEffect statusEffect) {
        this.statusEffect = statusEffect;
    }

    @Override
    public StatusEffect value() {
        return statusEffect;
    }

    @Override
    public boolean hasKeyAndValue() {
        return false;
    }

    @Override
    public boolean matchesId(Identifier id) {
        return false;
    }

    @Override
    public boolean matchesKey(RegistryKey key) {
        return false;
    }

    @Override
    public boolean isIn(TagKey tag) {
        return false;
    }

    @Override
    public boolean matches(RegistryEntry entry) {
        return false;
    }

    @Override
    public Stream<TagKey<StatusEffect>> streamTags() {
        return Stream.empty();
    }

    @Override
    public Either<RegistryKey<StatusEffect>, StatusEffect> getKeyOrValue() {
        return Either.right(statusEffect);
    }

    @Override
    public Optional<RegistryKey<StatusEffect>> getKey() {
        return Optional.empty();
    }

    @Override
    public Type getType() {
        return Type.DIRECT;
    }

    @Override
    public boolean ownerEquals(RegistryEntryOwner owner) {
        return false;
    }

    @Override
    public boolean matches(Predicate predicate) {
        return false;
    }
}
