package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public abstract class ConfigFile {
    private final File file;
    @Getter
    private FileConfiguration config;
    private final JavaPlugin plugin;

    protected ConfigFile(JavaPlugin plugin, String name) {
        this.file = new File(plugin.getDataFolder(), name);
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!file.exists()) {
            plugin.saveResource(name, false);
        }

        this.config = YamlConfiguration.loadConfiguration(file);
        load(config);
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);
        load(config);
    }

    protected void warn(String message){
        plugin.getLogger().warning(message);
    }
    protected void info(String message){
        plugin.getLogger().info(message);
    }

    public abstract void load(FileConfiguration config);
}
