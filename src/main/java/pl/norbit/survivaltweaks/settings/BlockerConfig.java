package pl.norbit.survivaltweaks.settings;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public class BlockerConfig extends ConfigFile {
    @Getter
    private boolean armadilloBrushCooldownEnabled;

    @Getter
    private int armadilloBrushCooldown;

    @Getter
    private boolean enderPearlDespawnEnabled;

    @Getter
    private boolean blockBoneMealEnabled;
    @Getter
    private boolean blockBoneMealDispenserEnabled;

    @Getter
    private List<Material> blockedBoneMeal;

    @Getter
    private boolean elytraBlockGenerateEnabled;

    @Getter
    private boolean elytraBlockMendingEnabled;

    @Getter
    private boolean blockLootEnabled;

    @Getter
    private List<Material> blockLootItems;

    public BlockerConfig(JavaPlugin plugin) {
        super(plugin, "blocker.yml");
    }

    public void load(FileConfiguration config) {
        armadilloBrushCooldownEnabled = config.getBoolean("blocker.armadillo-brush-cooldown.enabled", false);
        armadilloBrushCooldown = config.getInt("blocker.armadillo-brush-cooldown.cooldown", 30);

        enderPearlDespawnEnabled = config.getBoolean("blocker.ender-pearl-despawn.enabled", false);

        blockBoneMealEnabled = config.getBoolean("blocker.block-bone-meal.enabled", false);
        blockBoneMealDispenserEnabled = config.getBoolean("blocker.block-bone-meal.block-dispenser", false);

        elytraBlockGenerateEnabled = config.getBoolean("blocker.elytra.block-generate", false);
        elytraBlockMendingEnabled = config.getBoolean("blocker.elytra.block-mending", false);

        blockLootEnabled = config.getBoolean("blocker.block-loot.enabled", false);
        blockLootItems = config.getStringList("blocker.block-loot.items")
                .stream()
                .map(s -> {
                    try {
                        return Material.valueOf(s.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        warn("Invalid material in blocker.block-loot.items: " + s);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        loadBlocked(config);
    }
    public boolean isBlockedBoneMeal(Material mat) {
        return blockedBoneMeal.contains(mat);
    }

    private void loadBlocked(FileConfiguration config) {
        blockedBoneMeal = config.getStringList("block-bone-meal.blocked")
                .stream()
                .map(s -> {
                    try {
                        return Material.valueOf(s.toUpperCase());
                    } catch (IllegalArgumentException e) {
                        warn("Invalid material in blocker.block-bone-meal.items: " + s);
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();
    }


}
