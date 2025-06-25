package pl.norbit.survivaltweaks.hooks;

import org.bukkit.entity.Player;
import de.myzelyam.api.vanish.VanishAPI;
import pl.norbit.survivaltweaks.SurvivalTweaks;

public class SuperVanish {
    private static boolean enabled = false;

    public static void init(SurvivalTweaks instance) {
        if (instance.getServer().getPluginManager().isPluginEnabled("SuperVanish") || instance.getServer().getPluginManager().isPluginEnabled("PremiumVanish")) {
            enabled = true;
            instance.getLogger().info("Hook SuperVanish");
        }
    }

    public static boolean isVanished(Player player) {
        return enabled && VanishAPI.isInvisible(player);
    }

    public static boolean canSeeVanished(Player viewer) {
        if (!enabled) return true;

        return viewer.hasPermission("survivaltweaks.see_vanished") ||
               viewer.isOp();
    }

}
