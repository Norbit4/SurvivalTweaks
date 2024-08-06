package pl.norbit.survivaltweaks.utils;

import pl.norbit.survivaltweaks.SurvivalTweaks;

public class TaskUtils {

    private TaskUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void sync(Runnable runnable){
        SurvivalTweaks inst = SurvivalTweaks.getInstance();
        inst.getServer().getScheduler().runTask(inst, runnable);
    }

    public static void async(Runnable runnable){
        SurvivalTweaks inst = SurvivalTweaks.getInstance();
        inst.getServer().getScheduler().runTaskAsynchronously(inst, runnable);
    }

    public static void asyncTimer(Runnable runnable, long delay, long period){
        SurvivalTweaks inst = SurvivalTweaks.getInstance();
        inst.getServer().getScheduler().runTaskTimerAsynchronously(inst, runnable, delay, period);
    }
}
