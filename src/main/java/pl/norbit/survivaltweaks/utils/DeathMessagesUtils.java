package pl.norbit.survivaltweaks.utils;

import org.bukkit.Statistic;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.norbit.survivaltweaks.settings.Config;

public class DeathMessagesUtils {

    private DeathMessagesUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getMessage(Player p, EntityDamageEvent damageCause){
        String message = getMessage(damageCause);
        DamageSource damageSource = damageCause.getDamageSource();
        String name = p.getName();
        Entity causingEntity = damageSource.getCausingEntity();

        int deaths = p.getStatistic(Statistic.DEATHS);

        String prefix = Config.getDeathMessagePrefix();

        return message
                .replace("{PREFIX}", prefix)
                .replace("{PLAYER}", name)
                .replace("{ENTITY}", causingEntity != null ? causingEntity.getName() : "")
                .replace("{DEATH}", String.valueOf(deaths + 1));
    }

    private static String getMessage(EntityDamageEvent damageCause){
        return switch (damageCause.getCause()) {
            case DROWNING -> Config.getDeathMessageDrowning();
            case FALL -> Config.getDeathMessageFall();
            case LAVA -> Config.getDeathMessageLava();
            case SUFFOCATION -> Config.getDeathMessageSuffocation();
            case VOID -> Config.getDeathMessageVoid();
            case FIRE, FIRE_TICK -> Config.getDeathMessageFire();
            case LIGHTNING -> Config.getDeathMessageLightning();
            case ENTITY_ATTACK -> Config.getDeathMessageEntityAttack();
            case ENTITY_EXPLOSION -> Config.getDeathMessageEntityExplosion();
            case PROJECTILE ->  Config.getDeathMessageProjectile();
            case MAGIC -> Config.getDeathMessageMagic();
            case WITHER -> Config.getDeathMessageWither();
            case STARVATION -> Config.getDeathMessageStarvation();
            case POISON -> Config.getDeathMessagePoison();
            case THORNS ->  Config.getDeathMessageThorns();
            case DRAGON_BREATH -> Config.getDeathMessageDragon();
            case HOT_FLOOR -> Config.getDeathMessageHotFloor();
            default -> Config.getDeathMessageOther();
        };
    }
}
