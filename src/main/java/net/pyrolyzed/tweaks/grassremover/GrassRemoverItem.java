package net.pyrolyzed.tweaks.grassremover;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.checkerframework.checker.nullness.qual.NonNull;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.pyrolyzed.tweaks.Config;
import net.pyrolyzed.tweaks.Main;

public class GrassRemoverItem extends Item {

    public GrassRemoverItem(Properties properties) {
        super(properties);
    }

    private static boolean isGrass(BlockState state) {
        return state.is(Blocks.TALL_GRASS) || state.is(Blocks.SHORT_GRASS);
    }

    private static List<BlockPos> getAllGrassInRadius(Level level, BlockPos position, int radius) {
        BlockPos negativeRadiusBorder = position.offset(-radius, -radius, -radius);
        BlockPos positiveRadiusBorder = position.offset(radius, radius, radius);
        List<BlockPos> grassBlocks = new ArrayList<>();
        for (BlockPos blockPos : BlockPos.betweenClosed(negativeRadiusBorder, positiveRadiusBorder)) {
            if (!isGrass(level.getBlockState(blockPos)))
                continue;
            grassBlocks.add(blockPos.immutable());
        }
        return grassBlocks;
    }

    @Override
    public InteractionResult useOn(@Nonnull UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPosition = context.getClickedPos();
        Player player = context.getPlayer();
        int radius = Config.GRASS_REMOVER_RADIUS.getAsInt();

        if (level.isClientSide) return InteractionResult.CONSUME;

        List<BlockPos> grassInRadius = getAllGrassInRadius(level, clickedPosition, radius);
        if (grassInRadius.isEmpty()) {
            player.displayClientMessage(Component.literal("There is no grass in radius"), true);
            return InteractionResult.CONSUME;
        }

        List<ItemStack> allDrops = new ArrayList<>();
        for (BlockPos grassBlockPos : grassInRadius) {
            allDrops.addAll(Block.getDrops(level.getBlockState(grassBlockPos), (ServerLevel) level, clickedPosition, null));
            level.removeBlock(grassBlockPos, false);
        }

        for (ItemStack drop : allDrops) {
            Block.popResource(level, clickedPosition.offset(0, 1, 0), drop);
        }

        context.getItemInHand().shrink(1);

        return InteractionResult.SUCCESS;
    }
}