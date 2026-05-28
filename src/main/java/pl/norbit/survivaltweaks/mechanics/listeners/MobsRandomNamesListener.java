package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MechanicsConfig;
import pl.norbit.survivaltweaks.utils.ChatUtils;

public class MobsRandomNamesListener implements Listener {

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.VILLAGERS_RANDOM_NAMES)) {
            return;
        }

        for (Entity entity : e.getChunk().getEntities()) {
            if (entity instanceof Villager villager) {
                MechanicsConfig mConfig = ConfigManager.getMechanicsConfig();

                setEntityName(
                        villager,
                        mConfig.getVillagersRandomNamesColor(),
                        mConfig.getRandomVillagerName());
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
        MechanicsConfig mConfig = ConfigManager.getMechanicsConfig();

        setEntityName(
                villager,
                mConfig.getVillagersRandomNamesColor(),
                mConfig.getRandomVillagerName());
    }

    @EventHandler
    public void onEntityTame(EntityTameEvent e) {
        Entity entity = e.getEntity();

        MechanicsConfig mConfig = ConfigManager.getMechanicsConfig();

        if (entity instanceof Cat cat) {
            if (MechanicsLoader.isDisabled(Mechanic.CATS_RANDOM_NAMES)) {
                return;
            }
            setEntityName(
                    cat,
                    mConfig.getCatsRandomNamesColor(),
                    mConfig.getRandomCatName()
            );
        }else if (entity instanceof Wolf wolf) {
            if (MechanicsLoader.isDisabled(Mechanic.DOGS_RANDOM_NAMES)) {
                return;
            }
            setEntityName(
                    wolf,
                    mConfig.getDogsRandomNamesColor(),
                    mConfig.getRandomDogName()
            );
        }
    }

    private void setEntityName(Entity entity, String color, String name) {
        entity.setCustomName(ChatUtils.format(color + name));
        entity.setCustomNameVisible(true);
    }
}
