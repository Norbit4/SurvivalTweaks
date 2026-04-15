package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.log.PluginDebug;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MechanicsConfig;
import pl.norbit.survivaltweaks.utils.TaskUtils;

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

    @EventHandler
    public void onSwing(PlayerAnimationEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.SPEAR_NERF)) {
            return;
        }
        MechanicsConfig mechanicsConfig = ConfigManager.getMechanicsConfig();
        Player p = e.getPlayer();
        String world = p.getWorld().getName();

        if (mechanicsConfig.isDisabledSpearNerfWorld(world)) {
            return;
        }

        ItemStack item = p.getInventory().getItemInMainHand();

        if (!item.getType().name().contains("SPEAR")) return;
        if (!item.containsEnchantment(Enchantment.LUNGE)) return;

        if (p.getCooldown(item.getType()) > 0) {
            TaskUtils.syncLater(()-> p.setVelocity(p.getVelocity().multiply(0)), 1L);
            return;
        }
        p.setCooldown(item.getType(), mechanicsConfig.getSpearCooldown());
    }
}
