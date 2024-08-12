package pl.norbit.survivaltweaks.utils;

import fr.skytasul.glowingentities.GlowingEntities;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pl.norbit.survivaltweaks.SurvivalTweaks;

public class GlowUtils {
    private static GlowingEntities glowingEntities;

    private GlowUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void init() {
        glowingEntities = new GlowingEntities(SurvivalTweaks.getInstance());
    }

    public static void setGlowing(Entity entity, Player receiver, ChatColor color) {
        try {
            glow(entity, receiver, color);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    public static void resetGlowing(Entity entity, Player receiver) {
        try {
            unsetGlowing(entity, receiver);
        } catch (ReflectiveOperationException ignored) {
        }
    }

    private static void unsetGlowing(Entity entity, Player receiver) throws ReflectiveOperationException {
        glowingEntities.unsetGlowing(entity, receiver);
    }

    private static void glow(Entity entity, Player receiver, ChatColor color) throws ReflectiveOperationException {
        glowingEntities.setGlowing(entity, receiver, color);
    }
}
