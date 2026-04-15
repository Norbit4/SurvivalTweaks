package pl.norbit.survivaltweaks.mechanics.model;

import java.util.*;

public class DeadAntiAbuse {
    private final Map<UUID, List<Long>> deaths = new HashMap<>();
    private final Map<UUID, Long> blockedUntil = new HashMap<>();

    private static final long TIME_WINDOW = 5 * 60 * 1000;
    private static final long BLOCK_TIME = 30 * 60 * 1000;

    public void registerDeath(UUID playerUUID) {
        long now = System.currentTimeMillis();

        if (blockedUntil.containsKey(playerUUID)) {
            return;
        }

        deaths.computeIfAbsent(playerUUID, k -> new ArrayList<>()).add(now);

        List<Long> playerDeaths = deaths.get(playerUUID);
        playerDeaths.removeIf(time -> now - time > TIME_WINDOW);

        if (playerDeaths.size() > 3) {
            blockedUntil.put(playerUUID, now + BLOCK_TIME);
            playerDeaths.clear();
        }
    }

    public boolean isBlocked(UUID playerUUID) {
        long now = System.currentTimeMillis();

        if (!blockedUntil.containsKey(playerUUID)) {
            return false;
        }

        long blockEnd = blockedUntil.get(playerUUID);

        if (now >= blockEnd) {
            blockedUntil.remove(playerUUID);
            return false;
        }

        return true;
    }

    public long getRemainingTime(UUID uuid) {
        if (!blockedUntil.containsKey(uuid)) return 0;

        long remaining = blockedUntil.get(uuid) - System.currentTimeMillis();
        return Math.max(remaining, 0);
    }
}