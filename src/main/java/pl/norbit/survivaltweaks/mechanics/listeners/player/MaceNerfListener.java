package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;

public class MaceNerfListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(MechanicsLoader.isDisabled(Mechanic.MACE_NERF)){
            return;
        }

        Entity damager = event.getDamager();
        String name = damager.getWorld().getName();

        if(Config.isDisabledMaceNerfWorld(name)){
            return;
        }

        if (damager instanceof Projectile proj) {
            Entity shooter = proj.getShooter() instanceof Entity e ? e : null;
            if (shooter != null) damager = shooter;
        }

        if (!(damager instanceof Player player)){
            return;
        }

        double nerfPercent = Config.getMaceNerfPercentage();

        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.MACE){
            return;
        }

        double original = event.getDamage();

        double multiplier = (100.0 - nerfPercent) / 100.0;
        double newDamage = original * multiplier;

        event.setDamage(newDamage);
    }
}
