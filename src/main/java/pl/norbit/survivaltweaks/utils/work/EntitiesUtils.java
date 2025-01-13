package pl.norbit.survivaltweaks.utils.work;

import org.bukkit.World;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import pl.norbit.survivaltweaks.utils.WorldUtils;

import java.util.ArrayList;
import java.util.List;

public class EntitiesUtils {

    private EntitiesUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Wolf> getWolfsByOwner(String owner) {
        return getWolfOnAllWorlds()
                .stream()
                .filter(w -> w.getOwner() != null)
                .filter(w -> w.getOwner().getName() != null)
                .filter(w -> w.getOwner().getName().equals(owner))
                .toList();
    }


    public static List<Wolf> getWolfOnAllWorlds() {
        return new ArrayList<>(WorldUtils.getWorlds()
                .stream()
                .map(EntitiesUtils::getWolfsOnWorld)
                .flatMap(List::stream)
                .toList());
    }

    public static List<Wolf> getWolfsOnWorld(World w) {
        return new ArrayList<>(w
                .getEntitiesByClass(Wolf.class)
                .stream()
                .filter(Tameable::isTamed)
                .toList());
    }
}
