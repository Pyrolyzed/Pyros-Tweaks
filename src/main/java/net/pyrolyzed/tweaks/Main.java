package net.pyrolyzed.tweaks;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.pyrolyzed.tweaks.items.ModItems;


@Mod(Main.MODID)
public class Main {
    public static final String MODID = "pyrostweaks";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public Main(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModItems.register(modEventBus);
        modEventBus.addListener(this::addCreative);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModItems.GRASS_REMOVER);
        }
    }

}
