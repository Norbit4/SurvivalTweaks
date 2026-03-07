package pl.norbit.survivaltweaks.mechanics.listeners.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.utils.ChatUtils;
import pl.norbit.survivaltweaks.utils.TaskUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class VillagerChangeListener implements Listener {
    private final Map<UUID, Long> villagerCooldowns = new HashMap<>();

    @EventHandler
    public void onVillagerProfessionChange(VillagerCareerChangeEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.VILLAGER_PROFESSION_CHANGE)) {
            return;
        }

        VillagerCareerChangeEvent.ChangeReason reason = e.getReason();

        if (reason != VillagerCareerChangeEvent.ChangeReason.EMPLOYED) {
            return;
        }

        Villager villager = e.getEntity();
        UUID villagerUUID = villager.getUniqueId();
        long currentTime = System.currentTimeMillis();
        Long lastChangeTime = villagerCooldowns.get(villagerUUID);

        if (lastChangeTime != null) {
            long timePassed = currentTime - lastChangeTime;

            long cooldown = ConfigManager.getMechanicsConfig().getVillagerProfessionCooldown() * 1000L;
            if (timePassed < cooldown) {
                long timeRemaining = cooldown - timePassed;

                long minutes = TimeUnit.MILLISECONDS.toMinutes(timeRemaining);
                long seconds = TimeUnit.MILLISECONDS.toSeconds(timeRemaining) - TimeUnit.MINUTES.toSeconds(minutes);

                e.setCancelled(true);

                String minutesFormat = minutes + " " + ConfigManager.getMessagesConfig().getVillagerProfessionCooldownTimeMinutes();
                String secondsFormat = seconds + " " + ConfigManager.getMessagesConfig().getVillagerProfessionCooldownTimeSeconds();
                String time = minutesFormat + " " + secondsFormat;

                String message = ConfigManager.getMessagesConfig().getVillagerProfessionCooldownMessage()
                        .replace("{TIME}", time);

                String finalMessage = ChatUtils.format(message, null);

                List<Player> playersAroundVillager = getPlayersAroundVillager(villager, 8);
                playersAroundVillager.forEach(player -> player.sendMessage(finalMessage));
                return;
            }
        }
        if (e.getProfession() != Villager.Profession.NONE) {
            TaskUtils.syncLater(() -> villagerCooldowns.put(villagerUUID, currentTime), 30L);
        }
    }
    public List<Player> getPlayersAroundVillager(Villager villager, double radius) {
        List<Entity> nearbyEntities = villager.getNearbyEntities(radius, radius, radius);

        return nearbyEntities.stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .toList();
    }
}
