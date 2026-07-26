package io.github.sjouwer.gammautils;

import com.mojang.logging.LogUtils;
import io.github.sjouwer.gammautils.config.ModConfig;
import io.github.sjouwer.gammautils.statuseffect.StatusEffectManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.minecraft.world.InteractionResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mod(value = GammaUtils.NAMESPACE, dist = Dist.CLIENT)
@EventBusSubscriber(modid = GammaUtils.NAMESPACE, value = Dist.CLIENT)
public class GammaUtils {
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final String NAMESPACE = "gammautils";
    private static ConfigHolder<ModConfig> configHolder;

    public static ModConfig getConfig() {
        return configHolder.getConfig();
    }

    public static void saveConfig() {
        configHolder.save();
    }

    public GammaUtils(ModContainer container) { }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        loadConfigFile();

        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () ->
                (_, parent) -> AutoConfigClient.getConfigScreen(ModConfig.class, parent).get());
    }

    @SubscribeEvent
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        Commands.registerGammaCommands(event);
        Commands.registerNightVisionCommands(event);
    }

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        KeyBindings.registerBindings(event);
    }

    @SubscribeEvent
    public static void onPostClientTick(ClientTickEvent.Post event) {
        KeyBindings.handleBindings();
    }

    @SubscribeEvent
    public static void onPreClientTick(ClientTickEvent.Pre event) {
        GammaManager.setDynamicGamma();
        NightVisionManager.setDynamicNightVision();
    }

    private static void loadConfigFile() {
        try {
            precheckConfigFile();
            configHolder = AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        }
        catch (Exception e) {
            // Extra message to help the user in case the precheck didn't work.
            LOGGER.error("Failed to load gammautils config file, try manually deleting the file to resolve this issue. Report this issue on the Gamma Utils repository if the game continues to crash.");
            throw e;
        }

        configHolder.registerSaveListener((manager, data) -> {
            StatusEffectManager.updateAllEffects();
            return InteractionResult.SUCCESS;
        });
    }

    // In rare cases the config file might be completely empty, which causes Cloth Config to crash. This should resolve that issue.
    private static void precheckConfigFile() {
        Path configFolder = FMLPaths.CONFIGDIR.get();
        Path configFile = configFolder.resolve("gammautils.json");

        try {
            if (Files.exists(configFile) && Files.size(configFile) == 0) {
                LOGGER.warn("Config file is empty, deleting it so a new one can be created by Cloth Config");
                Files.delete(configFile);
            }
        } catch (IOException _) {
            LOGGER.warn("Failed precheck of config file");
        }
    }
}
