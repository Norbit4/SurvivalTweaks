package pl.norbit.survivaltweaks.utils;

import org.bukkit.World;
import pl.norbit.survivaltweaks.SurvivalTweaks;

import java.util.List;

public class WorldUtils {

    private WorldUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<World> getWorlds() {
        return SurvivalTweaks.getInstance().getServer().getWorlds();
    }
}
