package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import pl.norbit.survivaltweaks.mechanics.model.GlowEntity;
import pl.norbit.survivaltweaks.utils.GlowUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static pl.norbit.survivaltweaks.utils.TaskUtils.async;
import static pl.norbit.survivaltweaks.utils.TaskUtils.asyncTimer;

public class SpyGlassMechanic {

    private static final List<GlowEntity> glowEntities = new ArrayList<>();

    private SpyGlassMechanic() {
        throw new IllegalStateException("Utility class");
    }

    public static void start() {
        asyncTimer(SpyGlassMechanic::task, 40L, 20L);
    }

    private static Optional<GlowEntity> findByPlayerAndEntity(Player player, Entity entity) {
        return glowEntities
                .stream()
                .filter(glowEntity -> glowEntity.getPlayer().equals(player) && glowEntity.getEntity().equals(entity))
                .findFirst();
    }

    public static void addGlowingEntity(Entity entity, Player player, int duration) {
        async(() -> findByPlayerAndEntity(player, entity)
                .ifPresentOrElse(glowEntity -> glowEntity.setDuration(duration),
                    () ->{
                        GlowUtils.setGlowing(entity, player, ChatColor.YELLOW);
                        glowEntities.add(new GlowEntity(player, entity, duration));
                    }
                ));
    }

    private static void task(){
        Iterator<GlowEntity> iterator = glowEntities.iterator();

        while (iterator.hasNext()) {
            GlowEntity glowEntity = iterator.next();
            Entity entity = glowEntity.getEntity();
            Player player = glowEntity.getPlayer();

            if (entity == null || player == null) {
                iterator.remove();
                continue;
            }

            int duration = glowEntity.getDuration() - 1;

            if (duration <= 0) {
                GlowUtils.resetGlowing(entity, player);

                iterator.remove();
                continue;
            }
            glowEntity.setDuration(glowEntity.getDuration() - 1);
        }
    }
}
