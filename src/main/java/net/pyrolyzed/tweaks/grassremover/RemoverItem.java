package net.pyrolyzed.tweaks.grassremover;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.pyrolyzed.tweaks.Config;
import net.pyrolyzed.tweaks.items.ModItems;

public class RemoverItem {
    private static boolean isGrass(BlockState state) {
        return state.is(Blocks.TALL_GRASS) || state.is(Blocks.SHORT_GRASS);
    }

    private static List<BlockPos> getAllGrassInRadius(Level level, BlockPos position, int radius) {
        BlockPos negativeRadiusBorder = position.offset(-radius, -radius, -radius);
        BlockPos positiveRadiusBorder = position.offset(radius, radius, radius);
        return BlockPos.betweenClosedStream(negativeRadiusBorder, positiveRadiusBorder)
            .filter(pos -> isGrass(level.getBlockState(pos)))
            .map(BlockPos::immutable)
            .collect(Collectors.toList());
    }
    @SubscribeEvent
    private static void onUseRemover(PlayerInteractEvent.RightClickBlock event) {
        if (event.getItemStack().getItem() != null && event.getItemStack().getItem() != ModItems.GRASS_REMOVER.get()) {
            return;
        }

        Level level = event.getLevel();
        if (level.isClientSide) return;

        BlockPos position = event.getPos();
        int radius = Config.GRASS_REMOVER_RADIUS.getAsInt(); 

        List<BlockPos> grassInRadius = getAllGrassInRadius(level, position, radius);
        if (grassInRadius.isEmpty()) {
            event.getEntity().displayClientMessage(Component.literal("There is no grass in radius"), true);
            return;
        }

        List<ItemStack> allDrops = new ArrayList<>();
        for (BlockPos pos : grassInRadius) {
            List<ItemStack> drops = Block.getDrops(level.getBlockState(pos), (ServerLevel) level, pos, null);
            allDrops.addAll(drops);
            level.removeBlock(pos, false);
        }

        for (ItemStack drop : allDrops) {
            Block.popResource(level, position.offset(0, 1, 0), drop);
        }

        event.getItemStack().shrink(1);
    }
}