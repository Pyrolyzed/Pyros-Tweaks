package net.pyrolyzed.tweaks;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue GRASS_REMOVER_RADIUS = BUILDER
            .comment("Radius of blocks in each direction to remove grass.")
            .defineInRange("grassRemoverRadius", 32, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_HOSTILE = BUILDER
            .comment("Max amount of naturally spawned hostile mobs")
            .defineInRange("maxHostileMobs", 70, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_CREATURE = BUILDER
            .comment("Max amount of all naturally spawned creatures")
            .defineInRange("maxCreatures", 20, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue MAX_AMBIENT = BUILDER
            .comment("Max amount of all naturally spawned ambient creatures")
            .defineInRange("maxAmbient", 30, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue FARMLAND_RADIUS = BUILDER
            .comment("The radius in every direction water irrigates farmland")
            .defineInRange("farmlandRadius", 10, 0, Integer.MAX_VALUE);
    public static final ModConfigSpec.IntValue HATCH_CHANCE = BUILDER
            .comment("The chance a thrown egg hatches into a chick, If the value is 4 that means a 1 in 4 chance")
            .defineInRange("eggHatchChance", 4, 1, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
