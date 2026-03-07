package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import pl.norbit.survivaltweaks.log.PluginDebug;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

public class WitherSpawnListener implements Listener {
    @EventHandler
    public void onWitherSpawn(CreatureSpawnEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.NETHER_WITHER)) {
            return;
        }

        if (e.getEntityType() != EntityType.WITHER){
            return;
        }

        if (e.getLocation().getWorld().getEnvironment() != World.Environment.NETHER) {
            e.setCancelled(true);
            PluginDebug.debug("Skipping wither spawn event");
        }
    }
}
