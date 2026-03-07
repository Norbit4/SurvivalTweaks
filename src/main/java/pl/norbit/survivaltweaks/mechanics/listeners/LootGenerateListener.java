package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.log.PluginDebug;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;

import java.util.List;

public class LootGenerateListener implements Listener {

    @EventHandler
    public void onLootGenerate(LootGenerateEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.BLOCK_LOOT)){
            return;
        }

        List<ItemStack> loot = e.getLoot();

        ConfigManager.getBlockerConfig()
                .getBlockLootItems().forEach(item -> {
                    boolean removed = loot.removeIf(lootItem -> lootItem.getType() == item);

                    if(removed){
                        PluginDebug.debug("Block loot item removed " + item);
                    }
                });
    }
}
