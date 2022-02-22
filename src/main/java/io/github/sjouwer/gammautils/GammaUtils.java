package io.github.sjouwer.gammautils;

import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.*;
import io.github.sjouwer.gammautils.util.InfoProvider;
import net.fabricmc.api.ModInitializer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class GammaUtils implements ModInitializer {
    public static final StatusEffect BRIGHT = new BrightStatusEffect();
    public static final StatusEffect DIM = new DimStatusEffect();

    @Override
    public void onInitialize() {
        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        AutoConfig.getConfigHolder(ModConfig.class).registerSaveListener((manager, data) -> {
            InfoProvider.updateStatusEffect();
            return ActionResult.SUCCESS;
        });

        GammaOptions gammaOptions = new GammaOptions();

        KeyBindings keyBindings = new KeyBindings(gammaOptions);
        keyBindings.setKeyBindings();

        Commands commands = new Commands(gammaOptions);
        commands.registerCommands(ClientCommandManager.DISPATCHER);

        Registry.register(Registry.STATUS_EFFECT, new Identifier("gammautils", "bright"), BRIGHT);
        Registry.register(Registry.STATUS_EFFECT, new Identifier("gammautils", "dim"), DIM);
    }
}
