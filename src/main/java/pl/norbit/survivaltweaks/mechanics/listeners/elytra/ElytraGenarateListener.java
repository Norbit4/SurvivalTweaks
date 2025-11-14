package pl.norbit.survivaltweaks.mechanics.listeners.elytra;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkPopulateEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

public class ElytraGenarateListener implements Listener {

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ELYTRA_GENERATE_DISABLED)){
            return;
        }

        if (e.getWorld().getEnvironment() == World.Environment.THE_END) {
            for (Entity entity : e.getChunk().getEntities()) {
                if (entity instanceof ItemFrame frame && frame.getItem().getType() == Material.ELYTRA) {
                    frame.remove();
                }
            }
        }
    }
}
