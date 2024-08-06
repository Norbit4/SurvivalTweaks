package pl.norbit.survivaltweaks.settings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import pl.norbit.survivaltweaks.SurvivalTweaks;

public class Config {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static String compass;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static String clock;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static String recoveryCompass;
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static String noDeathLocation;

    private Config() {
        throw new IllegalStateException("Utility class");
    }

    public static void load(boolean reload) {
        SurvivalTweaks instance = SurvivalTweaks.getInstance();

        if (!reload) {
            instance.saveDefaultConfig();
        }

        if (reload) {
            instance.reloadConfig();
        }

        FileConfiguration config = instance.getConfig();

        compass = config.getString("compass");
        clock = config.getString("clock");
        recoveryCompass = config.getString("recovery-compass");
        noDeathLocation = config.getString("no-death-location");
    }
}
