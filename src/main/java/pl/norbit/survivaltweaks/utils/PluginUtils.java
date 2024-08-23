package pl.norbit.survivaltweaks.utils;

import org.bukkit.Server;
import pl.norbit.survivaltweaks.SurvivalTweaks;

public class PluginUtils {

    private PluginUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean checkPlugin(String pluginName) {
        Server server = SurvivalTweaks.getInstance().getServer();

        var pM = server.getPluginManager();
        var plugin = pM.getPlugin(pluginName);

        if(plugin != null && plugin.isEnabled()){
            var logger = server.getLogger();
            logger.info("Hooked to: " + pluginName);
            return true;
        }
        return false;
    }
}
