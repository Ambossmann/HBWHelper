package io.github.leo3418.hbwhelper.fabric;

import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import io.github.leo3418.hbwhelper.ConfigManager;
import io.github.leo3418.hbwhelper.HbwHelper;
import net.fabricmc.api.ModInitializer;
import net.neoforged.fml.config.ModConfig;

public class HbwHelperFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(HbwHelper.MOD_ID, ModConfig.Type.CLIENT, ConfigManager.getSpec());
        HbwHelper.init();
    }
}
