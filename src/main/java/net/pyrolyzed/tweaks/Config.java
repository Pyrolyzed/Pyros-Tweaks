package net.pyrolyzed.tweaks;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.IntValue GRASS_REMOVER_RADIUS = BUILDER
            .comment("Radius of blocks in each direction to remove grass.")
            .defineInRange("grassRemoverRadius", 128, 0, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();
}
