package net.pyrolyzed.tweaks.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FarmBlock;
import net.pyrolyzed.tweaks.Config;

@Mixin(FarmBlock.class)
public abstract class FarmlandTweak {
    @Redirect(
        method = "randomTick", 
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/FarmBlock;isNearWater(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    private static boolean redirectIsNearWater(LevelReader levelReader, BlockPos pos) {
        int radius = Config.FARMLAND_RADIUS.getAsInt();
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for (int x = -radius; x <= radius; ++x) {
            for (int z = -radius; z <= radius; ++z) {
                for (int y = 0; y <= 1; ++y) {
                    mutablePos.setWithOffset(pos, x, y, z);
                    if (levelReader.getFluidState(mutablePos).is(FluidTags.WATER)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
