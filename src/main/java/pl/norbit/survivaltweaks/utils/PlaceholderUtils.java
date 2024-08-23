package pl.norbit.survivaltweaks.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class PlaceholderUtils {

    private static boolean enabled;

    private PlaceholderUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void load() {
        if(PluginUtils.checkPlugin("PlaceholderAPI")){
            enabled = true;
        }
    }

    public static String setPlaceholders(String message, Player p){
        if(enabled){
            return PlaceholderAPI.setPlaceholders(p, message);
        }
        return message;
    }
}
