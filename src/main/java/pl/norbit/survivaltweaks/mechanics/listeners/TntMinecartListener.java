package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;

public class TntMinecartListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.GRIEF_PROTECTION)) {
            return;
        }

        if(!ConfigManager.getMechanicsConfig().isGriefProtectionTntMinecraftEnabled()){
            return;
        }

        if (e.getItem() != null && e.getItem().getType() == Material.TNT_MINECART) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.GRIEF_PROTECTION)) {
            return;
        }

        if(!ConfigManager.getMechanicsConfig().isGriefProtectionTntMinecraftEnabled()){
            return;
        }

        ItemStack item = e.getItem();
        if (item.getType() == Material.TNT_MINECART) {
            e.setCancelled(true);
        }
    }
}
