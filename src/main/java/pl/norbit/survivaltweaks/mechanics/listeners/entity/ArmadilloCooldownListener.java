package pl.norbit.survivaltweaks.mechanics.listeners.entity;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.utils.CooldownUtils;

public class ArmadilloCooldownListener implements Listener {

    @EventHandler
    public void onMobInteract(PlayerInteractEntityEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ARMADILLO_BRUSH_COOLDOWN)){
            return;
        }

        Entity entity = e.getRightClicked();
        Player p = e.getPlayer();

        if (entity.getType() != EntityType.ARMADILLO) {
            return;
        }

        ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
        Material type = itemInMainHand.getType();

        if (type != Material.BRUSH) {
            return;
        }

        if(CooldownUtils.isOnCooldown(p, Material.BRUSH)){
            e.setCancelled(true);
            return;
        }

        CooldownUtils.setCooldown(p, Material.BRUSH, 30);
    }
}
