package pl.norbit.survivaltweaks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.survivaltweaks.mechanics.listeners.PlayerSkyGlassListener;
import pl.norbit.survivaltweaks.mechanics.listeners.PlayerEatListener;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.GlowUtils;

public final class SurvivalTweaks extends JavaPlugin {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static SurvivalTweaks instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        Config.load(false);
        MechanicsLoader.load();

        PluginManager pluginManager = getServer().getPluginManager();

        GlowUtils.init();

        pluginManager.registerEvents(new PlayerEatListener(), this);
        pluginManager.registerEvents(new PlayerSkyGlassListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MechanicsLoader.unload();
    }
}
