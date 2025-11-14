package pl.norbit.survivaltweaks.mechanics.listeners.entity;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;

public class HappyGhastSpawnListener implements Listener {

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if(MechanicsLoader.isDisabled(Mechanic.HAPPY_GHOST_BOOST)){
            return;
        }

        Entity entity = event.getEntity();

        EntityType type = entity.getType();
        double speedMultiplier = Config.getHappyGhostSpeedMultiplier();

        if (type != EntityType.HAPPY_GHAST) {
            return;
        }

        if (!(entity instanceof LivingEntity living)){
            return;
        }

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
