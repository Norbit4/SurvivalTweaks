package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;

import java.util.*;

public class PlayerKeepItemsListener implements Listener {

    private final Map<UUID, List<ItemStack>> savedItemsMap = new HashMap<>();

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.KEEP_ITEMS)){
            return;
        }

        Player p = e.getEntity();

        String worldName = p.getWorld().getName();

        if(Config.isWorldDisabledForKeepItems(worldName)){
            return;
        }

        List<ItemStack> drops = new ArrayList<>(e.getDrops());
        List<ItemStack> savedItems = new ArrayList<>();

        Collections.shuffle(drops);

        for (int i = 0; i < drops.size() / 2; i++) {
            ItemStack item = drops.get(i);
            savedItems.add(item);
        }

        savedItemsMap.put(p.getUniqueId(), savedItems);
        e.getDrops().removeAll(savedItems);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.KEEP_ITEMS)){
            return;
        }

        Player p = e.getPlayer();
        List<ItemStack> savedItems = savedItemsMap.remove(p.getUniqueId());

        if(savedItems == null || savedItems.isEmpty()){
            return;
        }

        for (ItemStack item : savedItems) {
            p.getInventory().addItem(item);
        }

        String title = ChatUtils.format(Config.getRespawnTitle());
        String subtitle = ChatUtils.format(Config.getRespawnSubtitle());

        p.sendTitle(title, subtitle, 10, 70, 20);
    }
}

