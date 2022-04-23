package io.github.sjouwer.gammautils.util;

import io.github.sjouwer.gammautils.GammaUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.MutableText;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;

import static net.minecraft.text.Style.EMPTY;

public final class InfoProvider {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final ModConfig config = GammaUtils.getConfig();

    private InfoProvider() {
    }

    public static void updateStatusEffect() {
        ClientPlayerEntity player = client.player;
        if (player == null) {
            return;
        }

        if (config.statusEffectEnabled()) {
            int gamma = (int)Math.round(client.options.getGamma().getValue() * 100);
            if (gamma > 100) {
                if (!player.hasStatusEffect(GammaUtils.BRIGHT)) {
                    player.removeStatusEffect(GammaUtils.DIM);
                    addPermEffect(player, GammaUtils.BRIGHT);
                }
                return;
            }
            else if (gamma < 0) {
                if (!player.hasStatusEffect(GammaUtils.DIM)) {
                    player.removeStatusEffect(GammaUtils.BRIGHT);
                    addPermEffect(player, GammaUtils.DIM);
                }
                return;
            }
        }
        player.removeStatusEffect(GammaUtils.DIM);
        player.removeStatusEffect(GammaUtils.BRIGHT);
    }

    private static void addPermEffect(ClientPlayerEntity player, StatusEffect effect) {
        StatusEffectInstance brightStatus = new StatusEffectInstance(effect, 999999);
        brightStatus.setPermanent(true);
        player.addStatusEffect(brightStatus);
    }

    public static void showHudMessage() {
        if (!config.gammaMessageEnabled()) {
            return;
        }

        int gamma = (int)Math.round(client.options.getGamma().getValue() * 100);
        TranslatableTextContent translatableMessage = new TranslatableTextContent("text.gamma_utils.message.gamma", gamma);
        MutableText message = MutableText.of(translatableMessage);

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
        client.inGameHud.setOverlayMessage(message, false);
    }
}
