package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import pl.norbit.survivaltweaks.SurvivalTweaks;

public class Config {
    @Getter
    private boolean debug;

    public Config() {
        load(false);
    }

    private void load(boolean reload) {
        SurvivalTweaks instance = SurvivalTweaks.getInstance();

        if (!reload) {
            instance.saveDefaultConfig();
        } else {
            instance.reloadConfig();
        }
        FileConfiguration config = instance.getConfig();

        debug = config.getBoolean("debug");
    }

    public void reload() {
        load(true);
    }
}
