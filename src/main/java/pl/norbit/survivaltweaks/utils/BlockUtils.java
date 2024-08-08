package pl.norbit.survivaltweaks.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.IntStream;

public class BlockUtils {
    private BlockUtils() {
        throw new IllegalStateException("Utility class");
    }
    public static List<Block> getNearBlocks(Player p, int range) {
        return getBlocks(p.getLocation().getBlock(), range);
    }

    public static List<Block> getBlocks(Block block, int range) {
        return IntStream.rangeClosed(-range, range)
                .boxed()
                .flatMap(dx -> IntStream.rangeClosed(-range, range)
                        .boxed()
                        .flatMap(dy -> IntStream.rangeClosed(-range, range)
                                .mapToObj(dz -> block.getRelative(dx, dy, dz))))
                .toList();
    }
}
