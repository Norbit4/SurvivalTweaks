package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;

public class PlayerWaypointListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        if(MechanicsLoader.isDisabled(Mechanic.WAYPOINTS)){
            return;
        }
        Player p = e.getPlayer();
        AttributeInstance receiveRange = p.getAttribute(Attribute.WAYPOINT_RECEIVE_RANGE);
        int waypointsRange = ConfigManager.getMechanicsConfig().getWaypointsRange();

        if (receiveRange != null) {
            receiveRange.setBaseValue(waypointsRange);
        }

        AttributeInstance transmitRange = p.getAttribute(Attribute.WAYPOINT_TRANSMIT_RANGE);

        if (transmitRange != null) {
            transmitRange.setBaseValue(waypointsRange);
        }
    }
}
