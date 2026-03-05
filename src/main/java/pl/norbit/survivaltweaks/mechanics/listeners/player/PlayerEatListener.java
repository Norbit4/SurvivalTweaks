package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.mechanics.model.PlayerSize;
import pl.norbit.survivaltweaks.mechanics.SizeMechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MechanicsConfig;
import pl.norbit.survivaltweaks.utils.items.ItemsUtils;

public class PlayerEatListener implements Listener {

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
