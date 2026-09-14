package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.utils.DurabilityUtils;

import java.util.Random;

public class TurtleHelmetMechanic {
    private static final Random random = new Random();

    private TurtleHelmetMechanic() {}

    protected static void check(Player p, ItemStack itemOnHead) {
        if(MechanicsLoader.isDisabled(Mechanic.TURTLE_HELMET)){
            return;
        }

        if(itemOnHead == null){
            return;
        }

        Material type = itemOnHead.getType();

        if (type != Material.TURTLE_HELMET) {
            return;
        }

        if (!p.isInWater()) {
            return;
        }
        p.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 50, 0));

        if(!ConfigManager.getMechanicsConfig().isTurtleHelmetDurabilityEnabled()){
            return;
        }
        //30% chance to reduce durability
        if (random.nextDouble() < 0.3) {
            ItemStack itemStack = DurabilityUtils.updateDurability(itemOnHead, 1);

            p.getInventory().setHelmet(itemStack);
        }
    }
}
