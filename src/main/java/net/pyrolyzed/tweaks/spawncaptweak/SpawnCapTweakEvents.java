package net.pyrolyzed.tweaks.spawncaptweak;

import java.lang.reflect.Field;

import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.coremod.api.ASMAPI;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.pyrolyzed.tweaks.Config;
import net.pyrolyzed.tweaks.Main;

public class SpawnCapTweakEvents {
    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        Field field = null;
        String max = ASMAPI.mapField("max");
        try {
            field = MobCategory.class.getDeclaredField(max);
            field.setAccessible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        for (MobCategory category : MobCategory.values()) {
            String categoryName = category.getName().toUpperCase();
            if (categoryName.equals("MONSTER")) {
                try {
                    field.setInt(category, Config.MAX_HOSTILE.getAsInt());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                continue;
            } else if (categoryName.contains("CREATURE") || categoryName.equals("AXOLOTLS")) {
                try {
                    field.setInt(category, Config.MAX_CREATURE.getAsInt());
                } catch(Exception exception) {
                    exception.printStackTrace();
                }
            } else if (categoryName.contains("AMBIENT")) {
                try {
                    field.setInt(category, Config.MAX_AMBIENT.getAsInt());
                } catch(Exception exception) {
                    exception.printStackTrace();
                }
            }
        }
    }    
}
