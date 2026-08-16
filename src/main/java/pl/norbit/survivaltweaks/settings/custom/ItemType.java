package pl.norbit.survivaltweaks.settings.custom;

import lombok.Getter;
import pl.norbit.survivaltweaks.plugins.PluginHook;

@Getter
public enum ItemType {
    MATERIAL(null),
    ITEMS_ADDER(PluginHook.ITEMS_ADDER),
    NEXO(PluginHook.NEXO),
    MMO_ITEMS(PluginHook.MMO_ITEMS),
    ORAXEN(PluginHook.ORAXEN),
    CRAFT_ENGINE(PluginHook.CRAFT_ENGINE),
    MYTHIC_MOBS(PluginHook.MYTHIC_MOBS);

    private final PluginHook requiredPlugin;

    ItemType(PluginHook requiredPlugin) {
        this.requiredPlugin = requiredPlugin;
    }

}
