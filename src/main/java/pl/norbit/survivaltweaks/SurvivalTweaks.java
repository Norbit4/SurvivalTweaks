package pl.norbit.survivaltweaks;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.survivaltweaks.commands.MainCommand;
import pl.norbit.survivaltweaks.commands.TrackCommand;
import pl.norbit.survivaltweaks.mechanics.listeners.*;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.listeners.block.AmethystBreakListener;
import pl.norbit.survivaltweaks.mechanics.listeners.block.BoneMealListener;
import pl.norbit.survivaltweaks.mechanics.listeners.block.SpawnerBreakListener;
import pl.norbit.survivaltweaks.mechanics.listeners.elytra.ElytraMendingPrepare;
import pl.norbit.survivaltweaks.mechanics.listeners.elytra.ElytraGenarateListener;
import pl.norbit.survivaltweaks.mechanics.listeners.entity.*;
import pl.norbit.survivaltweaks.mechanics.listeners.explode.ExplodeListener;
import pl.norbit.survivaltweaks.mechanics.listeners.player.*;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.MythicUtils;
import pl.norbit.survivaltweaks.utils.PlaceholderUtils;
import pl.norbit.survivaltweaks.hooks.SuperVanish;

public final class SurvivalTweaks extends JavaPlugin {
    @Getter
    @Setter(AccessLevel.PRIVATE)
    private static SurvivalTweaks instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        setInstance(this);

        MechanicsLoader.load(false);

        PlaceholderUtils.load();

        SuperVanish.init(this);
        loadListeners();

        getCommand("survivaltweaks").setExecutor(new MainCommand());
        getCommand("track").setExecutor(new TrackCommand());

        checkPlugins();
    }

    @Override
    public void onDisable() {
        MechanicsLoader.unload();
    }

    private void checkPlugins(){
        if(checkPlugin("MythicMobs")){
            Config.setMythicMobsEnabled(true);
            MythicUtils.init();
        }
    }

    private boolean checkPlugin(String pluginName) {
        var pM = getServer().getPluginManager();
        var plugin = pM.getPlugin(pluginName);

        if(plugin != null && plugin.isEnabled()){
            var logger = getServer().getLogger();
            String message = "Hooked to: " + pluginName;

            logger.info(message);
            return true;
        }
        return false;
    }

    private void loadListeners(){
        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerEatListener(), this);
        pluginManager.registerEvents(new PlayerSpyGlassListener(), this);
        pluginManager.registerEvents(new PlayerDeadListener(), this);
        pluginManager.registerEvents(new PlayerFireballListener(), this);
        pluginManager.registerEvents(new PlayerTotemVoidListener(), this);
        pluginManager.registerEvents(new SpawnerBreakListener(), this);
        pluginManager.registerEvents(new PlayerTpListener(), this);
        pluginManager.registerEvents(new AmethystBreakListener(), this);
        pluginManager.registerEvents(new BlazeWaterDeathListener(), this);
        pluginManager.registerEvents(new VillagerChangeListener(), this);

        pluginManager.registerEvents(new ElytraMendingPrepare(), this);
        pluginManager.registerEvents(new ElytraGenarateListener(), this);
        pluginManager.registerEvents(new BoneMealListener(), this);
        pluginManager.registerEvents(new ArmadilloCooldownListener(), this);
        pluginManager.registerEvents(new LootGenerateListener(), this);
        pluginManager.registerEvents(new PlayerFrameInteractListener(), this);
        pluginManager.registerEvents(new PlayerKeepItemsListener(), this);
        pluginManager.registerEvents(new ProjectileDespawnListener(), this);
        pluginManager.registerEvents(new HappyGhastSpawnListener(), this);
        pluginManager.registerEvents(new MaceNerfListener(), this);

        pluginManager.registerEvents(new AnvilTooExpensiveListener(), this);
        pluginManager.registerEvents(new ExplodeListener(), this);
    }
}
