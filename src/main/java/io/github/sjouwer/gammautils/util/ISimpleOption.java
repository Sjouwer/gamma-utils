package io.github.sjouwer.gammautils.util;

import net.minecraft.client.MinecraftClient;

public interface ISimpleOption {
    void set(Object value);
    static void setGamma(double gamma) {
        ((ISimpleOption) (Object) MinecraftClient.getInstance().options.getGamma()).set(gamma);
    }
}
