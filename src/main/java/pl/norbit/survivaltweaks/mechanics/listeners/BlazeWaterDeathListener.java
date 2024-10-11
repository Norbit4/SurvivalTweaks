package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

import java.util.Random;

public class BlazeWaterDeathListener implements Listener {
    private final Random random = new Random();

    @EventHandler
    public void onBlazeDeath(EntityDeathEvent e) {
        LivingEntity entity = e.getEntity();

        if (MechanicsLoader.isDisabled(Mechanic.BLAZE_DROP)) {
            return;
        }

        if (entity.getType() != EntityType.BLAZE) {
            return;
        }

        DamageSource damageSource = e.getDamageSource();
        DamageType damageType = damageSource.getDamageType();

        if (damageType != DamageType.DROWN) {
            return;
        }

        double randomDouble = random.nextDouble();

        ItemStack rod = new ItemStack(Material.BLAZE_ROD, 1);

        if (randomDouble < 0.1) {
            e.getDrops().add(rod.clone().add(1));
            return;
        }

        if (randomDouble < 0.50) {
            e.getDrops().add(rod);
        }
    }
}
