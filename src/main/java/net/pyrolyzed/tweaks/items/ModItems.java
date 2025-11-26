package net.pyrolyzed.tweaks.items;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.pyrolyzed.tweaks.Main;
import net.pyrolyzed.tweaks.grassremover.GrassRemoverItem;

public class ModItems { 
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Main.MODID);
    public static final DeferredItem<Item> GRASS_REMOVER = ITEMS.register("grass_remover", () -> new GrassRemoverItem(new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
