package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.model.DeadAntiAbuse;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.settings.MechanicsConfig;
import pl.norbit.survivaltweaks.utils.ChatUtils;
import pl.norbit.survivaltweaks.utils.DeathMessagesUtils;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import java.util.Random;

import static org.bukkit.event.EventPriority.MONITOR;

public class PlayerDeadListener implements Listener {
    private static final Random random = new Random();
    private static final DeadAntiAbuse deadAntiAbuse = new DeadAntiAbuse();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDead(PlayerDeathEvent e) {
        deadAntiAbuse.registerDeath(e.getEntity().getUniqueId());
    }

    @EventHandler
    public void onPlayerDeathHeadDrop(PlayerDeathEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.PLAYER_HEAD)){
            return;
        }
        Player p = e.getEntity();
        MechanicsConfig mechanicsConfig = ConfigManager.getMechanicsConfig();

        if(mechanicsConfig.isPlayerHeadAntiAbuse() && deadAntiAbuse.isBlocked(p.getUniqueId())){
            return;
        }

        double playerHeadDropChance = mechanicsConfig.getPlayerHeadDropChance();

        if (random.nextDouble() > playerHeadDropChance) {
            return;
        }
        ItemStack customSkull = PlayerUtils.getCustomSkull(p);

        e.getDrops().add(customSkull);
    }

    @EventHandler(ignoreCancelled = true, priority = MONITOR)
    public void onPlayerDeathCustomMessage(PlayerDeathEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.CUSTOM_DEATH_MESSAGES)){
            return;
        }

        Player p = e.getEntity();
        MechanicsConfig mechanicsConfig = ConfigManager.getMechanicsConfig();
        String worldName = p.getWorld().getName();

        if(mechanicsConfig.isCustomDeathMessageAntiAbuse() && deadAntiAbuse.isBlocked(p.getUniqueId())){
            e.setDeathMessage(null);
            return;
        }

        if(mechanicsConfig.isDisabledDeathMessageWorld(worldName)){
            e.setDeathMessage(null);
            return;
        }

        EntityDamageEvent lastDamageCause = p.getLastDamageCause();

        if(lastDamageCause == null){
            return;
        }

        String message = DeathMessagesUtils.getMessage(p, lastDamageCause);

        e.setDeathMessage(ChatUtils.format(message, p));
    }
}

