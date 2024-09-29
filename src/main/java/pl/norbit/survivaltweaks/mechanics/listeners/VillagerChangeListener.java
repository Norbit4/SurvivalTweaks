package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class VillagerChangeListener implements Listener {
    private final Map<UUID, Long> villagerCooldowns = new HashMap<>();

    @EventHandler
    public void onVillagerProfessionChange(VillagerCareerChangeEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.VIILAGER_PROFESSION_CHANGE)) {
            return;
        }

        VillagerCareerChangeEvent.ChangeReason reason = e.getReason();

        if (reason != VillagerCareerChangeEvent.ChangeReason.EMPLOYED) {
            return;
        }

        Villager villager = e.getEntity();
        UUID villagerUUID = villager.getUniqueId();
        long currentTime = System.currentTimeMillis();

        if (villagerCooldowns.containsKey(villagerUUID)) {
            long lastChangeTime = villagerCooldowns.get(villagerUUID);
            long timePassed = currentTime - lastChangeTime;

            long cooldown = 5 * 60 * 1000L;
            if (timePassed < cooldown) {
                long timeRemaining = cooldown - timePassed;

                long minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemaining);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(timeRemaining) - TimeUnit.MINUTES.toSeconds(minutes);

                e.setCancelled(true);

                String minutesFormat = minutes + " " + Config.getVillagerProfessionCooldownTimeMinutes();
                String secondsFormat = seconds + " " + Config.getVillagerProfessionCooldownTimeSeconds();
                String time = minutesFormat + " " + secondsFormat;

                String message = Config.getVillagerProfessionCooldownMessage()
                        .replace("{time}", time);

                String finalMessage = ChatUtils.format(message, null);

                List<Player> playersAroundVillager = getPlayersAroundVillager(villager, 6);
                playersAroundVillager.forEach(player -> player.sendMessage(finalMessage));
                return;
            }
        }
        villagerCooldowns.put(villagerUUID, currentTime);
    }
    public List<Player> getPlayersAroundVillager(Villager villager, double radius) {
        List<Entity> nearbyEntities = villager.getNearbyEntities(radius, radius, radius);

        return nearbyEntities.stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .toList();
    }
}
