package pl.norbit.survivaltweaks.plugins;

import org.bukkit.plugin.java.JavaPlugin;
import pl.norbit.survivaltweaks.settings.custom.ItemType;

import java.util.EnumMap;

public final class PluginService {

    private static final EnumMap<PluginHook, Boolean> hooks = new EnumMap<>(PluginHook.class);

    private PluginService() {}

    public static void load(JavaPlugin plugin) {
        var manager = plugin.getServer().getPluginManager();

        for (PluginHook hook : PluginHook.values()) {
            boolean enabled = manager.isPluginEnabled(hook.getPluginName());
            hooks.put(hook, enabled);

            if (enabled) {
                plugin.getLogger().info("Hooked into " + hook.getPluginName());
            }
        }
    }

    public static boolean supports(ItemType itemType) {
        PluginHook hook = itemType.getRequiredPlugin();

        if (hook == null) {
            return true;
        }

        return isEnabled(hook);
    }

    public static boolean isEnabled(PluginHook hook) {
        return hooks.getOrDefault(hook, false);
    }
}
