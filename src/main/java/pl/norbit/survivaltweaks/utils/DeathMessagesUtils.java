package pl.norbit.survivaltweaks.utils;

import org.bukkit.Statistic;
import org.bukkit.damage.DamageSource;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import pl.norbit.survivaltweaks.SurvivalTweaks;
import pl.norbit.survivaltweaks.settings.ConfigManager;

public class DeathMessagesUtils {

    private DeathMessagesUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String getMessage(Player p, EntityDamageEvent damageCause){
        String message = getMessage(damageCause);
        DamageSource damageSource = damageCause.getDamageSource();
        String name = p.getName();
        Entity causingEntity = damageSource.getCausingEntity();

        String entityName;

        if(causingEntity != null){
            String mobName = null;
            if(SurvivalTweaks.isMythicMobsEnabled()){
                MythicResponse response = MythicUtils.getMobName(causingEntity);

                if(response.getType() == MythicResponseType.WRONG_NAME){
                    message = ConfigManager.getMessagesConfig().getDeathMessageOther();
                }else {
                    mobName = response.getName();
                }
            }

            if(mobName != null){
                entityName = mobName;
            }else {
                entityName = ConfigManager.getMessagesConfig().getMobNameOrDefault(causingEntity.getType(), causingEntity.getName());
            }
        }else {
            entityName = "";
        }

        int deaths = p.getStatistic(Statistic.DEATHS);

        String prefix = ConfigManager.getMessagesConfig().getDeathMessagePrefix();

        return message
                .replace("{PREFIX}", prefix)
                .replace("{PLAYER}", name)
                .replace("{ENTITY}", entityName)
                .replace("{DEATH}", String.valueOf(deaths + 1));
    }

    private static String getMessage(EntityDamageEvent damageCause){
        return switch (damageCause.getCause()) {
            case DROWNING -> ConfigManager.getMessagesConfig().getDeathMessageDrowning();
            case FALL -> ConfigManager.getMessagesConfig().getDeathMessageFall();
            case LAVA -> ConfigManager.getMessagesConfig().getDeathMessageLava();
            case SUFFOCATION -> ConfigManager.getMessagesConfig().getDeathMessageSuffocation();
            case VOID -> ConfigManager.getMessagesConfig().getDeathMessageVoid();
            case FIRE, FIRE_TICK -> ConfigManager.getMessagesConfig().getDeathMessageFire();
            case LIGHTNING -> ConfigManager.getMessagesConfig().getDeathMessageLightning();
            case ENTITY_ATTACK -> ConfigManager.getMessagesConfig().getDeathMessageEntityAttack();
            case ENTITY_EXPLOSION -> ConfigManager.getMessagesConfig().getDeathMessageEntityExplosion();
            case PROJECTILE ->  ConfigManager.getMessagesConfig().getDeathMessageProjectile();
            case MAGIC -> ConfigManager.getMessagesConfig().getDeathMessageMagic();
            case WITHER -> ConfigManager.getMessagesConfig().getDeathMessageWither();
            case STARVATION -> ConfigManager.getMessagesConfig().getDeathMessageStarvation();
            case POISON -> ConfigManager.getMessagesConfig().getDeathMessagePoison();
            case THORNS ->  ConfigManager.getMessagesConfig().getDeathMessageThorns();
            case DRAGON_BREATH -> ConfigManager.getMessagesConfig().getDeathMessageDragon();
            case HOT_FLOOR -> ConfigManager.getMessagesConfig().getDeathMessageHotFloor();
            default -> ConfigManager.getMessagesConfig().getDeathMessageOther();
        };
    }
}
