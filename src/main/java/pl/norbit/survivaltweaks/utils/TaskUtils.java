package pl.norbit.survivaltweaks.utils;

import org.bukkit.entity.Entity;
import pl.norbit.survivaltweaks.SurvivalTweaks;

import java.util.concurrent.TimeUnit;

public class TaskUtils {

    private TaskUtils() {}

    public static void syncLater(Entity entity, Runnable runnable, long delay) {
        SurvivalTweaks inst = SurvivalTweaks.getInstance();
        entity.getScheduler().runDelayed(
                inst,
                task -> runnable.run(),
                null,
                delay
        );
    }

    public static void asyncLater(Runnable runnable, long delayTicks) {
        SurvivalTweaks inst = SurvivalTweaks.getInstance();

        inst.getServer().getAsyncScheduler().runDelayed(
                inst,
                task -> runnable.run(),
                delayTicks * 50,
                TimeUnit.MILLISECONDS
        );
    }

    public static void syncTimer(Runnable runnable, long delay, long period) {
        SurvivalTweaks inst = SurvivalTweaks.getInstance();

        inst.getServer().getGlobalRegionScheduler().runAtFixedRate(
                inst,
                task -> runnable.run(),
                delay,
                period
        );
    }
}
