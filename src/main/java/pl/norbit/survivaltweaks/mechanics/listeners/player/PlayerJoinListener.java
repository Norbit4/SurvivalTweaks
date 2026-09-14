package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        MechanicsLoader.onJoin(e.getPlayer());
    }
}
