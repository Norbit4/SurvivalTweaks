package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;
import pl.norbit.survivaltweaks.utils.DeathMessagesUtils;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import java.util.Random;

import static org.bukkit.event.EventPriority.MONITOR;

public class PlayerDeadListener implements Listener {
    private static final Random random = new Random();

    @EventHandler
    public void onPlayerDeathHeadDrop(PlayerDeathEvent e) {
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

    @EventHandler(ignoreCancelled = true, priority = MONITOR)
    public void onPlayerDeathCustomMessage(PlayerDeathEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.CUSTOM_DEATH_MESSAGES)){
            return;
        }

        Player p = e.getEntity();
        EntityDamageEvent lastDamageCause = p.getLastDamageCause();

        if(lastDamageCause == null){
            return;
        }

        String message = DeathMessagesUtils.getMessage(p, lastDamageCause);

        e.setDeathMessage(ChatUtils.format(message, p));
    }
}

