package pl.norbit.survivaltweaks.settings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import pl.norbit.survivaltweaks.SurvivalTweaks;

import java.util.List;

public class Config {
    @Getter
    private static String reloadMessage;
    //compass
    @Getter
    private static String compass;
    @Getter
    private static boolean compassEnabled;

    //clock
    @Getter
    private static String clock;

    @Getter
    private static boolean clockEnabled;

    //recoveryCompass
    @Getter
    private static String recoveryCompass;

    @Getter
    private static String noDeathLocation;

    @Getter
    private static boolean recoveryCompassEnabled;

    //spyglass
    @Getter
    private static String spyglass;

    @Getter
    private static boolean spyglassEnabled;

    //size
    @Getter
    private static Material smallSizeItem;

    @Getter
    private static Material normalSizeItem;

    @Getter
    private static boolean sizeEnabled;

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

        //reloadMessage
        reloadMessage = config.getString("reload-message");

        //compass
        compass = config.getString("mechanics.compass.display");
        compassEnabled = config.getBoolean("mechanics.compass.enabled");

        //clock
        clock = config.getString("mechanics.clock.display");
        clockEnabled = config.getBoolean("mechanics.clock.enabled");

        //recoveryCompass
        recoveryCompass = config.getString("mechanics.recovery-compass.display");
        noDeathLocation = config.getString("mechanics.recovery-compass.display-no-death-location");
        recoveryCompassEnabled = config.getBoolean("mechanics.recovery-compass.enabled");

        //spyglass
        spyglass = config.getString("mechanics.spyglass.display");
        spyglassEnabled = config.getBoolean("mechanics.spyglass.enabled");

        //size
        String small = config.getString("mechanics.size.small");
        String normal = config.getString("mechanics.size.normal");

        if(small == null || normal == null) {
            instance.getLogger().severe("Size items are not set in the config file!");
            instance.getServer().getPluginManager().disablePlugin(instance);
            return;
        }

        smallSizeItem = Material.getMaterial(small.toUpperCase());
        normalSizeItem = Material.getMaterial(normal.toUpperCase());

        sizeEnabled = config.getBoolean("mechanics.size.enabled");
    }
}
