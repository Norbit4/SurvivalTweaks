package pl.norbit.survivaltweaks.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CooldownUtils {

    private CooldownUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static boolean isOnCooldown(Player player, Material mat) {
        return player.getCooldown(mat) > 0;
    }

    public static void setCooldown(Player player, Material mat, int seconds) {
        player.setCooldown(mat, seconds * 20);
    }
}
