package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.utils.ChatUtils;

public class VillagerRandomNamesListener implements Listener {
    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.VILLAGERS_RANDOM_NAMES)) {
            return;
        }

        for (Entity entity : e.getChunk().getEntities()) {
            if (entity instanceof Villager villager) {
                setVillagerName(villager);
            }
        }
    }

    @EventHandler
    public void onVillagerSpawn(EntitySpawnEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.VILLAGERS_RANDOM_NAMES)) {
            return;
        }

        if (!(e.getEntity() instanceof Villager villager)) {
            return;
        }
        setVillagerName(villager);
    }

    private void setVillagerName(Villager villager) {
        villager.setCustomName(ChatUtils.format("&#9c4321" + ConfigManager.getMechanicsConfig().getRandomVillagerName()));
        villager.setCustomNameVisible(true);
    }
}
