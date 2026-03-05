package pl.norbit.survivaltweaks.settings;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FurnaceConfig extends ConfigFile {
    private Map<Material, Integer> fuelsMap;

    private List<Material> fuelsBlacklist;

    public Integer getBurnTime(Material material) {
        return fuelsMap.get(material);
    }

    public boolean isFuelBlocked(Material mat) {
        return fuelsBlacklist.contains(mat);
    }

    public FurnaceConfig(JavaPlugin plugin) {
        super(plugin, "furnace.yml");
    }

    public void load(FileConfiguration config) {
        loadFuels(config);
    }

    private void loadFuels(FileConfiguration config) {
        fuelsMap = new EnumMap<>(Material.class);

        ConfigurationSection section = config.getConfigurationSection("fuels");
        if (section == null) {
            warn("Fuels section not found");
            return;
        }

        for (String key : section.getKeys(false)) {
            Material material = Material.matchMaterial(key);
            if (material == null) {
                warn("Material " + key + " not found");
                continue;
            }

            int value = section.getInt(key);
            fuelsMap.put(material, value);
        }

        fuelsBlacklist = config.getStringList("blacklist")
                .stream()
                .map(Material::getMaterial)
                .filter(Objects::nonNull)
                .toList();
    }
}
