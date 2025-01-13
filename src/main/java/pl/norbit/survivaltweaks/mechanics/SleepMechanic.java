package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.entity.Player;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;
import pl.norbit.survivaltweaks.utils.PlayerUtils;
import pl.norbit.survivaltweaks.utils.TaskUtils;
import pl.norbit.survivaltweaks.utils.WorldUtils;

import java.util.List;

public class SleepMechanic {
    private SleepMechanic() {
        throw new IllegalStateException("Utility class");
    }

    public static void startTask() {
        TaskUtils.asyncTimer(SleepMechanic::checkSleepingPlayers, 60, 30);
    }

    private static void checkSleepingPlayers() {
        if(MechanicsLoader.isDisabled(Mechanic.SLEEP)){
            return;
        }

        List<Player> sleeping = PlayerUtils.getSleepingPlayers();

        int sleepingPlayersCount = sleeping.size();

        if(sleepingPlayersCount == 0){
            return;
        }

        int needSleepingPlayersCount = PlayerUtils.getNeedSleepingPlayersCount();

        String subtitle = Config.getSleepMechanicSubtitleMessage()
                .replace("{SLEEP}", String.valueOf(sleepingPlayersCount))
                .replace("{NEED}", String.valueOf(needSleepingPlayersCount));

        sleeping.forEach(p -> p.sendTitle(ChatUtils.format(Config.getSleepMechanicTitleMessage()), ChatUtils.format(subtitle), 0, 32, 0));

        if (sleepingPlayersCount >= needSleepingPlayersCount) {
            sleeping.forEach(p -> p.sendTitle(ChatUtils.format(Config.getSleepMechanicTitleMessage()), ChatUtils.format(Config.getSleepMechanicSubtitleSuccessMessage()), 0, 32, 0));
            TaskUtils.sync(() -> WorldUtils.getWorlds().forEach(world -> world.setTime(0)));
        }
    }
}
