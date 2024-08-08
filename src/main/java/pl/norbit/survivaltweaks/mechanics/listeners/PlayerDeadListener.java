package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import java.util.Random;

public class PlayerDeadListener implements Listener {
    private static final Random random = new Random();

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.PLAYER_HEAD)){
            return;
        }

        double playerHeadDropChance = Config.getPlayerHeadDropChance();

        if (random.nextDouble() > playerHeadDropChance) {
            return;
        }

        Player p = e.getEntity();
        ItemStack customSkull = PlayerUtils.getCustomSkull(p);

        e.getDrops().add(customSkull);
    }
}

