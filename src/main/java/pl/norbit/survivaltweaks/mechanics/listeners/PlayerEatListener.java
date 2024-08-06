package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.mechanics.model.PlayerSize;
import pl.norbit.survivaltweaks.mechanics.SizeMechanic;

public class PlayerEatListener implements Listener {

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.SIZE)){
            return;
        }

        Player p = e.getPlayer();
        ItemStack item = e.getItem();

        Material type = item.getType();

        if(type == Material.GLOW_BERRIES){
            SizeMechanic.setSize(p, PlayerSize.SMALL);
        }else if(type == Material.GOLDEN_CARROT) {
            SizeMechanic.setSize(p, PlayerSize.NORMAL);
        }
    }
}
