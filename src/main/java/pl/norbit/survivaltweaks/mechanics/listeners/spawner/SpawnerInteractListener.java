package pl.norbit.survivaltweaks.mechanics.listeners.spawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.utils.ChatUtils;

public class SpawnerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block b = e.getClickedBlock();

        if (b == null) {
            return;
        }

        if (b.getType() != Material.SPAWNER) {
            return;
        }

        if(MechanicsLoader.isDisabled(Mechanic.SPAWNER_EGG_CHANGE)){
            return;
        }

        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();

        if (!inv.getItemInMainHand().getType().toString().endsWith("_SPAWN_EGG")) {
            return;
        }

        if (p.hasPermission("survivaltweaks.spawner-type") || p.isOp()) {
            return;
        }

        e.setCancelled(true);
        p.sendMessage(ChatUtils.format(ConfigManager.getMessagesConfig().getBlockSpawnEggChangeMessage()));
    }
}
