package pl.norbit.survivaltweaks.mechanics.listeners.spawner;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

public class SpawnerMobDeath implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.SPAWNER_MOBS_BLOCKED_EQ_DROPS)){
            return;
        }

        LivingEntity entity = e.getEntity();

        if (entity.getEntitySpawnReason()!= CreatureSpawnEvent.SpawnReason.SPAWNER) {
            return;
        }

        if (entity instanceof Skeleton) {
            e.getDrops().removeIf(item ->
                    item.getType() == Material.BOW
            );
        }

        e.getDrops().removeIf(item -> isArmor(item.getType()));
    }

    private boolean isArmor(Material material) {
        String name = material.name();

        return name.endsWith("_HELMET")
                || name.endsWith("_CHESTPLATE")
                || name.endsWith("_LEGGINGS")
                || name.endsWith("_BOOTS");
    }
}
