package pl.norbit.survivaltweaks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.survivaltweaks.commands.MainCommand;
import pl.norbit.survivaltweaks.commands.TrackCommand;
import pl.norbit.survivaltweaks.mechanics.SleepMechanic;
import pl.norbit.survivaltweaks.mechanics.listeners.*;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.utils.GlowUtils;
import pl.norbit.survivaltweaks.utils.PlaceholderUtils;

public final class SurvivalTweaks extends JavaPlugin {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static SurvivalTweaks instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        MechanicsLoader.load(false);

        PluginManager pluginManager = getServer().getPluginManager();

        GlowUtils.init();
        PlaceholderUtils.load();
        SleepMechanic.startTask();

        pluginManager.registerEvents(new PlayerEatListener(), this);
        pluginManager.registerEvents(new PlayerSpyGlassListener(), this);
        pluginManager.registerEvents(new PlayerDeadListener(), this);
        pluginManager.registerEvents(new FireballListener(), this);
        pluginManager.registerEvents(new PlayerTotemVoidListener(), this);
        pluginManager.registerEvents(new SpawnerBreakListener(), this);
        pluginManager.registerEvents(new PlayerTpListener(), this);
        pluginManager.registerEvents(new AmethystBreakListener(), this);
        pluginManager.registerEvents(new BlazeWaterDeathListener(), this);
        pluginManager.registerEvents(new VillagerChangeListener(), this);
        pluginManager.registerEvents(new BedListener(), this);

        getCommand("survivaltweaks").setExecutor(new MainCommand());
        getCommand("track").setExecutor(new TrackCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        MechanicsLoader.unload();
    }
}
