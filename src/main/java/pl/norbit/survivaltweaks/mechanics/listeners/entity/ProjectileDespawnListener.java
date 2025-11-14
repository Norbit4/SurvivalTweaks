package pl.norbit.survivaltweaks.mechanics.listeners.entity;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.utils.TaskUtils;

public class ProjectileDespawnListener implements Listener {

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ENDER_PEARL_DESPAWN)){
            return;
        }

        Projectile projectile = e.getEntity();

        if (projectile instanceof EnderPearl) {
            TaskUtils.syncLater(() -> {
                if (!projectile.isDead() && projectile.isValid()) {
                    projectile.remove();
                }
            }, 20L * 20);
        }
    }
}
