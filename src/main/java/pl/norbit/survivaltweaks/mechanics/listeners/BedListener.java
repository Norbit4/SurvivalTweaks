package pl.norbit.survivaltweaks.mechanics.listeners;

import io.papermc.paper.event.player.PlayerBedFailEnterEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.world.TimeSkipEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import java.util.List;

public class BedListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerSleep(PlayerBedEnterEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.SLEEP)){
            return;
        }

        PlayerBedEnterEvent.BedEnterResult bedEnterResult = e.getBedEnterResult();

        if (bedEnterResult != PlayerBedEnterEvent.BedEnterResult.OK) {
            return;
        }

        String message = Config.getSleepMechanicActionBarMessage()
                .replace("{SLEEP}", String.valueOf(PlayerUtils.getSleepingPlayersCount()))
                .replace("{NEED}", String.valueOf(PlayerUtils.getNeedSleepingPlayersCount()));

        List<Player> onlinePlayers = PlayerUtils.getOnlinePlayers();
        onlinePlayers.forEach(player -> player.sendActionBar(ChatUtils.format(message)));
    }

    @EventHandler
    public void onTimeSkip(TimeSkipEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.SLEEP)){
            return;
        }

        if (e.getSkipReason() == TimeSkipEvent.SkipReason.NIGHT_SKIP) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerSleep(PlayerBedFailEnterEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.SLEEP)){
            return;
        }

        TextComponent text = Component.text(ChatUtils.format(Config.getSleepMechanicDenyMessage()));
        e.setMessage(text);
    }
}
