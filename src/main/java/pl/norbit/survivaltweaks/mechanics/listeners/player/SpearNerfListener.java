package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.log.PluginDebug;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MechanicsConfig;

public class SpearNerfListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {

        if (MechanicsLoader.isDisabled(Mechanic.SPEAR_NERF)) {
            return;
        }

        Entity damager = e.getDamager();
        String world = damager.getWorld().getName();

        MechanicsConfig mechanicsConfig = ConfigManager.getMechanicsConfig();

        if (mechanicsConfig.isDisabledSpearNerfWorld(world)) {
            return;
        }

        if (!(damager instanceof Player player)) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (!item.getType().name().contains("SPEAR")) {
            return;
        }

        double nerfPercent = mechanicsConfig.getSpearNerfPercentage();

        double original = e.getDamage();
        double multiplier = (100.0 - nerfPercent) / 100.0;
        double newDamage = original * multiplier;

        double spearMaxDamage = mechanicsConfig.getSpearMaxDamage();

        if(newDamage > spearMaxDamage){
            newDamage = spearMaxDamage;
        }

        PluginDebug.debug("Spear nerf: " + original + " -> " + newDamage);

        e.setDamage(newDamage);
    }
}
