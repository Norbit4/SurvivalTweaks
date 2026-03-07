package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import pl.norbit.survivaltweaks.SurvivalTweaks;

public class ConfigManager {
    @Getter
    private static Config config;

    @Getter
    private static BlockerConfig blockerConfig;

    @Getter
    private static MechanicsConfig mechanicsConfig;

    @Getter
    private static MessagesConfig messagesConfig;

    @Getter
    private static FurnaceConfig furnaceConfig;

    private ConfigManager() {}

    public static void load(){
        SurvivalTweaks instance = SurvivalTweaks.getInstance();

        blockerConfig = new BlockerConfig(instance);
        mechanicsConfig = new MechanicsConfig(instance);
        messagesConfig = new MessagesConfig(instance);
        furnaceConfig = new FurnaceConfig(instance);
        config = new Config();
    }

    public static void reload(){
        blockerConfig.reload();
        mechanicsConfig.reload();
        messagesConfig.reload();
        furnaceConfig.reload();
        config.reload();
    }
}
