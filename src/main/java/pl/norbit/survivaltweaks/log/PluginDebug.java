package pl.norbit.survivaltweaks.log;

import pl.norbit.survivaltweaks.SurvivalTweaks;
import pl.norbit.survivaltweaks.settings.ConfigManager;

public class PluginDebug {
    private PluginDebug(){}

    public static void debug(String message){
        if(!ConfigManager.getConfig().isDebug()){
            return;
        }

        SurvivalTweaks.getInstance().getLogger().info("[DEBUG] " + message);
    }
}
