package pl.norbit.survivaltweaks.plugins;

import lombok.Getter;

@Getter
public enum PluginHook {

    ITEMS_ADDER("ItemsAdder"),
    NEXO("Nexo"),
    ORAXEN("Oraxen"),
    MMO_ITEMS("MMOItems"),
    CRAFT_ENGINE("CraftEngine"),
    PLACEHOLDER_API("PlaceholderAPI"),
    EXECUTABLE_ITEMS("ExecutableItems"),
    MYTHIC_MOBS("MythicMobs");

    private final String pluginName;

    PluginHook(String pluginName) {
        this.pluginName = pluginName;
    }

}
