package io.github.sjouwer.gammautils.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.LightType;

import java.util.ArrayList;
import java.util.List;

public class LightLevelUtil {
    private LightLevelUtil() {
    }

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static Double getAverageLightLevel(int range) {
        return getAverageLightLevel(range, 0);
    }

    public static Double getAverageLightLevel(int range, float skyBrightnessOverride) {
        if (client.world == null || client.player == null) {
            return 15.0;
        }

        BlockPos playerPos = client.player.getBlockPos().up();
        List<Double> lightLevels = new ArrayList<>();
        lightLevels.add(getLightLevel(playerPos, skyBrightnessOverride));
        if (range > 0) {
            for (Direction direction : Direction.values()) {
                addLightLevelsInDirection(lightLevels, playerPos, direction, range, skyBrightnessOverride);
            }
        }

        return lightLevels.stream().mapToDouble(lvl -> lvl).average().orElse(15.0);
    }

    private static void addLightLevelsInDirection(List<Double> lightLevels, BlockPos blockPos, Direction direction, int range, float skyBrightnessOverride) {
        for (int i = 0; i < range; i++) {
            BlockPos offsetPos = blockPos.offset(direction, i);
            if(!client.world.getBlockState(offsetPos).isAir()) {
                break;
            }

            lightLevels.add(getLightLevel(offsetPos, skyBrightnessOverride));
        }
    }

    public static double getLightLevel(BlockPos blockPos, float skyBrightnessOverride) {
        if (client.world == null) {
            return 15.0;
        }

        int blockLight = client.world.getLightingProvider().get(LightType.BLOCK).getLightLevel(blockPos);
        int skyLight = client.world.getLightingProvider().get(LightType.SKY).getLightLevel(blockPos);
        float skyBrightness = Math.max(client.world.getSkyBrightness(1f), skyBrightnessOverride);
        return Math.max(blockLight, skyLight * skyBrightness);
    }
}
