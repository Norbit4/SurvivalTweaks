package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.DurabilityUtils;

import java.util.Random;

import static pl.norbit.survivaltweaks.utils.TaskUtils.sync;

public class TurtleHelmetMechanic {
    private static final Random random = new Random();

    private TurtleHelmetMechanic() {
        throw new IllegalStateException("Utility class");
    }

    protected static void check(Player p, ItemStack itemOnHead) {
        if(MechanicsLoader.isDisabled(Mechanic.TURTLE_HELMET)){
            return;
        }

        Material type = itemOnHead.getType();

        if (type != Material.TURTLE_HELMET) {
            return;
        }

        if (!p.isInWater()) {
            return;
        }

        sync(() -> {
            p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 35, 0));

            if(!Config.isTurtleHelmetDurabilityEnabled()){
                return;
            }
            //30% chance to reduce durability
            if (random.nextDouble() < 0.3) {
                ItemStack itemStack = DurabilityUtils.updateDurability(itemOnHead, 1);

                p.getInventory().setHelmet(itemStack);
            }
        });
    }
}
