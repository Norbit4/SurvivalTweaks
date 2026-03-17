package pl.norbit.survivaltweaks.mechanics.listeners.spawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.utils.ChatUtils;

public class SpawnerPlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Block b = e.getBlock();

        if (b.getType() != Material.SPAWNER) {
            return;
        }

        if(MechanicsLoader.isDisabled(Mechanic.SPAWNER_NEAR_SPAWNER)){
            return;
        }

        int radius = ConfigManager.getBlockerConfig().getBlockSpawnerNearSpawnerDistance();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Block nearbyBlock = b.getRelative(x, y, z);

                    if(nearbyBlock.equals(b)) {
                        continue;
                    }

                    if (nearbyBlock.getType() == Material.SPAWNER) {
                        e.setCancelled(true);
                        Player p = e.getPlayer();
                        p.sendMessage(ChatUtils.format(ConfigManager.getMessagesConfig().getBlockSpawnNearSpawnerMessage()));
                        return;
                    }
                }
            }
        }
    }
}
