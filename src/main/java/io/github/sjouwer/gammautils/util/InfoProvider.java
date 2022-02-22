package io.github.sjouwer.gammautils.util;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.BaseText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static net.minecraft.text.Style.EMPTY;

public final class InfoProvider {
    private static final MinecraftClient minecraft = MinecraftClient.getInstance();
    private static final ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

    private InfoProvider() {
    }

    public static void updateStatusEffect() {
        ClientPlayerEntity player = minecraft.player;
        if (player == null) {
            return;
        }

        player.removeStatusEffect(GammaUtils.BRIGHT);
        player.removeStatusEffect(GammaUtils.DIM);
        if (!config.statusEffectEnabled()) {
            return;
        }

        int gamma = (int)Math.round(minecraft.options.gamma * 100);
        if (gamma > 100) {
            StatusEffectInstance brightStatus = new StatusEffectInstance(GammaUtils.BRIGHT, 999999);
            brightStatus.setPermanent(true);
            player.addStatusEffect(brightStatus);
        }
        else if (gamma < 0) {
            StatusEffectInstance dimStatus = new StatusEffectInstance(GammaUtils.DIM, 999999);
            dimStatus.setPermanent(true);
            player.addStatusEffect(dimStatus);
        }
    }

    public static void showHudMessage() {
        if (!config.gammaMessageEnabled()) {
            return;
        }

        int gamma = (int)Math.round(minecraft.options.gamma * 100);
        BaseText message = new TranslatableText("text.gamma_utils.message.gamma", gamma);

        Formatting format;
        if (gamma < 0) {
            format = Formatting.DARK_RED;
        }
        else if (gamma > 100) {
            format = Formatting.GOLD;
        }
        else {
            format = Formatting.DARK_GREEN;
        }

        message.setStyle(EMPTY.withColor(format));
        minecraft.inGameHud.setOverlayMessage(message, false);
    }
}
