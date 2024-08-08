package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;

public class FireballListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.FIREBALL)){
            return;
        }

        ItemStack item = e.getItem();

        if (item == null) {
            return;
        }

        Material type = item.getType();

        if (type != Material.FIRE_CHARGE) {
            return;
        }
        Player p = e.getPlayer();
        Action action = e.getAction();

        if(p.getCooldown(Material.FIRE_CHARGE) > 0){
            return;
        }

        if (action == Action.RIGHT_CLICK_AIR || action  == Action.RIGHT_CLICK_BLOCK) {
            Fireball fireball = p.launchProjectile(Fireball.class);
            fireball.setYield(Config.getFireballYield());

            Vector direction = p.getLocation().getDirection();
            fireball.setVelocity(direction.multiply(1));

            item.setAmount(item.getAmount() - 1);

            int fireballCooldown = Config.getFireballCooldown();
            p.setCooldown(Material.FIRE_CHARGE, 20 * fireballCooldown);
        }
    }
}
