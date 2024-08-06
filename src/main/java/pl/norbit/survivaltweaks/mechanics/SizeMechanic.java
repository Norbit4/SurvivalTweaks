package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import pl.norbit.survivaltweaks.mechanics.model.PlayerSize;

public class SizeMechanic {
    private SizeMechanic() {
        throw new IllegalStateException("Utility class");
    }

    public static void setSize(Player p, PlayerSize size) {
        updateAttribute(p, Attribute.GENERIC_SCALE, size.getSize());
        updateAttribute(p, Attribute.GENERIC_JUMP_STRENGTH, size.getJumpStrength());
        updateAttribute(p, Attribute.GENERIC_STEP_HEIGHT, size.getStepHeight());
        updateAttribute(p, Attribute.GENERIC_MAX_HEALTH, size.getHealth());
    }

    private static void updateAttribute(Player p, Attribute attribute, double value) {
        AttributeInstance att = p.getAttribute(attribute);

        if(att == null) {
            return;
        }

        att.setBaseValue(value);
    }
}
