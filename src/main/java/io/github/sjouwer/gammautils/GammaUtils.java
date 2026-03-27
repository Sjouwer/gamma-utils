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
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.slf4j.Logger;

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
        configHolder = AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        configHolder.registerSaveListener((manager, data) -> {
            StatusEffectManager.updateAllEffects();
            return InteractionResult.SUCCESS;
        });

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
}
