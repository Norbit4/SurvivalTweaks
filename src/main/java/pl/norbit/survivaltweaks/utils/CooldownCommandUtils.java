package pl.norbit.survivaltweaks.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownCommandUtils {

    private CooldownCommandUtils(){}

    private static final Map<String, Map<UUID, Long>> cooldowns = new HashMap<>();

    public static void setCooldown(String key, UUID playerUUID, long seconds) {
        long endTime = System.currentTimeMillis() + (seconds * 1000);

        cooldowns
                .computeIfAbsent(key, k -> new HashMap<>())
                .put(playerUUID, endTime);
    }

    public static boolean isCooldownFinished(String key, UUID uuid) {
        Map<UUID, Long> map = cooldowns.get(key);
        if (map == null) return true;

        Long endTime = map.get(uuid);
        if (endTime == null) return true;

        if (System.currentTimeMillis() >= endTime) {
            map.remove(uuid);
            if (map.isEmpty()) {
                cooldowns.remove(key);
            }
            return true;
        }

        return false;
    }

    public static int getRemainingSeconds(String key, UUID uuid) {
        Map<UUID, Long> map = cooldowns.get(key);
        if (map == null){
            return 0;
        }

        Long endTime = map.get(uuid);
        if (endTime == null) return 0;

        long remainingMillis = endTime - System.currentTimeMillis();
        if (remainingMillis <= 0) {
            map.remove(uuid);
            if (map.isEmpty()) {
                cooldowns.remove(key);
            }
            return 0;
        }

        return (int) remainingMillis / 1000;
    }

    public static void removeCooldown(String key, UUID uuid) {
        Map<UUID, Long> map = cooldowns.get(key);
        if (map == null) return;

        map.remove(uuid);
        if (map.isEmpty()) {
            cooldowns.remove(key);
        }
    }

    public static boolean isOnCooldown(String key, UUID uuid) {
        return !isCooldownFinished(key, uuid);
    }
}