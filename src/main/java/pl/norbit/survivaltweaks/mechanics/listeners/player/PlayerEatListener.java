package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.SurvivalTweaks;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.mechanics.model.PlayerSize;
import pl.norbit.survivaltweaks.mechanics.SizeMechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MechanicsConfig;
import pl.norbit.survivaltweaks.settings.MessagesConfig;
import pl.norbit.survivaltweaks.settings.model.InfinityFood;
import pl.norbit.survivaltweaks.utils.ChatUtils;
import pl.norbit.survivaltweaks.utils.TaskUtils;
import pl.norbit.survivaltweaks.utils.items.ItemsUtils;

public class PlayerEatListener implements Listener {
    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();

        ItemStack item = e.getItem().clone();
        int slot = p.getInventory().getHeldItemSlot();

        String id = ItemsUtils.getId(item);
        NamespacedKey key = new NamespacedKey(SurvivalTweaks.getInstance(), id);

        MechanicsConfig mechanicsConfig = ConfigManager.getMechanicsConfig();
        InfinityFood infinityFood = mechanicsConfig.getInfinityFood(id);

        if(infinityFood == null){
            return;
        }

        int cooldown = p.getCooldown(key);

        if (cooldown > 0) {
            MessagesConfig messagesConfig = ConfigManager.getMessagesConfig();
            int cooldownSeconds = (int) Math.ceil(p.getCooldown(key) / 20.0);

            String infinityFoodCooldownMessage = messagesConfig.getInfinityFoodCooldownMessage()
                    .replace("{cooldown}", String.valueOf(cooldownSeconds));

            p.sendActionBar(ChatUtils.format(infinityFoodCooldownMessage));
            e.setCancelled(true);
            return;
        }
        p.setCooldown(key, 20 * infinityFood.getCooldown());

        TaskUtils.asyncLater(() -> p.getInventory().setItem(slot, item), 1);
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.SIZE)){
            return;
        }

        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        MechanicsConfig mechanicsConfig = ConfigManager.getMechanicsConfig();

        String smallSizeItem = mechanicsConfig.getSmallSizeItem();
        String normalSizeItem = mechanicsConfig.getNormalSizeItem();

        if(ItemsUtils.isValidItem(item, smallSizeItem)){
            SizeMechanic.setSize(p, PlayerSize.SMALL);
        }else if(ItemsUtils.isValidItem(item, normalSizeItem)){
            SizeMechanic.setSize(p, PlayerSize.NORMAL);
        }
    }
}
