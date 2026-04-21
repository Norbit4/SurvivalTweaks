package pl.norbit.survivaltweaks.settings;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.survivaltweaks.utils.items.ItemsUtils;

import java.util.*;

public class FurnaceConfig extends ConfigFile {
    private Map<String, Integer> fuelsMap;

    private List<Material> fuelsBlacklist;

    public Integer getBurnTime(ItemStack stack) {
        String id = ItemsUtils.getId(stack);
        return fuelsMap.get(id);
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
        fuelsMap = new HashMap<>();

        ConfigurationSection section = config.getConfigurationSection("fuels");
        if (section == null) {
            warn("Fuels section not found");
            return;
        }

        for (String key : section.getKeys(false)) {
            int value = section.getInt(key);
            fuelsMap.put(key, value);
        }

        fuelsBlacklist = config.getStringList("blacklist")
                .stream()
                .map(Material::getMaterial)
                .filter(Objects::nonNull)
                .toList();
    }
}
