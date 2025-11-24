package pl.norbit.survivaltweaks.mechanics.listeners.entity;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import pl.norbit.survivaltweaks.SurvivalTweaks;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;

public class HappyGhastSpawnListener implements Listener {

    private static final NamespacedKey KEY_SPEED = new NamespacedKey(SurvivalTweaks.getInstance(), "happy_speed_set");

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.HAPPY_GHOST_BOOST)){
            return;
        }

        Entity entity = e.getEntity();

        EntityType type = entity.getType();
        double speedMultiplier = Config.getHappyGhostSpeedMultiplier();

        if (type != EntityType.HAPPY_GHAST) {
            return;
        }

        if (!(entity instanceof LivingEntity living)){
            return;
        }

        PersistentDataContainer data = living.getPersistentDataContainer();

        if (data.has(KEY_SPEED, PersistentDataType.BYTE)) {
            return;
        }

        data.set(KEY_SPEED, PersistentDataType.BYTE, (byte) 1);

        AttributeInstance attrSpeed = living.getAttribute(Attribute.FLYING_SPEED);
        if (attrSpeed != null) {
            double oldSpeed = attrSpeed.getBaseValue();
            double newSpeed = oldSpeed * speedMultiplier;
            attrSpeed.setBaseValue(newSpeed);
        }

        AttributeInstance attrHp = living.getAttribute(Attribute.MAX_HEALTH);
        if (attrHp != null) {
            double hp = Config.getHappyGhostHp();
            attrHp.setBaseValue(hp);
            living.setHealth(hp);
        }

    }
}
