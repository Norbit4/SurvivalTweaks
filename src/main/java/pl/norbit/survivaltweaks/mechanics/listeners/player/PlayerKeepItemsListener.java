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
import pl.norbit.survivaltweaks.settings.ConfigManager;
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

        if(ConfigManager.getMechanicsConfig().isWorldDisabledForKeepItems(worldName)){
            return;
        }

        List<ItemStack> savedItems = new ArrayList<>();

        //armor
        List<ItemStack> armor = new ArrayList<>();
        for (ItemStack item : p.getInventory().getArmorContents()) {
            if (item != null) {
                armor.add(item);
            }
        }

        Collections.shuffle(armor);
        int armorToKeep = armor.size() / 2;

        for (int i = 0; i < armorToKeep; i++) {
            ItemStack piece = armor.get(i);
            savedItems.add(piece);
        }

        //all items
        List<ItemStack> drops = new ArrayList<>(e.getDrops());
        Collections.shuffle(drops);
        drops.removeAll(armor);

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

        String title = ChatUtils.format(ConfigManager.getMessagesConfig().getRespawnTitle());
        String subtitle = ChatUtils.format(ConfigManager.getMessagesConfig().getRespawnSubtitle());

        p.sendTitle(title, subtitle, 10, 70, 20);
    }
}

