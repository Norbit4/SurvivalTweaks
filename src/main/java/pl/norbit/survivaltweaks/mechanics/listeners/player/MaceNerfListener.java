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
import pl.norbit.survivaltweaks.log.PluginDebug;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;

public class MaceNerfListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.MACE_NERF)){
            return;
        }

        Entity damager = e.getDamager();
        String name = damager.getWorld().getName();

        if(ConfigManager.getMechanicsConfig().isDisabledMaceNerfWorld(name)){
            return;
        }

        if (damager instanceof Projectile proj) {
            Entity shooter = proj.getShooter() instanceof Entity entity ? entity : null;
            if (shooter != null) damager = shooter;
        }

        if (!(damager instanceof Player player)){
            return;
        }

        double nerfPercent = ConfigManager.getMechanicsConfig().getMaceNerfPercentage();

        ItemStack item = player.getInventory().getItemInMainHand();

        if(item.getType() != Material.MACE){
            return;
        }

        double original = e.getDamage();

        double multiplier = (100.0 - nerfPercent) / 100.0;
        double newDamage = original * multiplier;

        PluginDebug.debug("Mace nerf: " + original + " -> " + newDamage);

        e.setDamage(newDamage);
    }
}
