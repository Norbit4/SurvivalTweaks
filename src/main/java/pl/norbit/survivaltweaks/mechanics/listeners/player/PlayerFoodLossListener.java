package pl.norbit.survivaltweaks.mechanics.listeners.player;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

public class PlayerFoodLossListener implements Listener {

    @EventHandler
    public void onFoodLose(FoodLevelChangeEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.SIZE)){
            return;
        }

        if (!(e.getEntity() instanceof Player p))
            return;

        if (e.getFoodLevel() >= p.getFoodLevel())
            return;

        AttributeInstance scaleAttr = p.getAttribute(Attribute.SCALE);

        if (scaleAttr == null) return;

        double scale = scaleAttr.getBaseValue();

        if (scale <= 1.0){
            return;
        }

        int current = p.getFoodLevel();
        int vanillaLoss = current - e.getFoodLevel();

        e.setFoodLevel(Math.max(0, current - (vanillaLoss * 2)));
    }
}
