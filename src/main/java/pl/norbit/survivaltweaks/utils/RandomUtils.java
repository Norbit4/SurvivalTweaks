package pl.norbit.survivaltweaks.utils;

import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private RandomUtils() {}

    public static boolean chance(double chance) {
        return ThreadLocalRandom.current().nextDouble() <= chance;
    }
}
