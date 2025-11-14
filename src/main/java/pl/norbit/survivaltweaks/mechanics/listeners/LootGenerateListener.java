package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;

import java.util.List;

public class LootGenerateListener implements Listener {

    @EventHandler
    public void onLootGenerate(LootGenerateEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.BLOCK_LOOT)){
            return;
        }

        List<ItemStack> loot = e.getLoot();

        Config.getBlockLootItems().forEach(item -> loot.removeIf(lootItem -> lootItem.getType() == item));
    }
}
