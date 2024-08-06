package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.SpyGlassMechanic;
import pl.norbit.survivaltweaks.utils.ChatUtils;

public class PlayerSkyGlassListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();

        if(item == null){
            return;
        }

        Material type = item.getType();
        Action action = e.getAction();

        if (type != Material.SPYGLASS) {
            return;
        }

        if (!(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        Player p = e.getPlayer();
        Entity targetEntity = p.getTargetEntity(70);

        if (targetEntity == null) {
            return;
        }

        SpyGlassMechanic.addGlowingEntity(targetEntity, p, 25);

        p.sendActionBar(ChatUtils.format("&#dcee9a" + targetEntity.getName()));
    }
}
