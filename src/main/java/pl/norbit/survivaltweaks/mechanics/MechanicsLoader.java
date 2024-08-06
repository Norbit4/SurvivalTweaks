package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import static pl.norbit.survivaltweaks.utils.TaskUtils.asyncTimer;

public class MechanicsLoader {

    private MechanicsLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static void load() {
        CompassMechanic.load();
        SpyGlassMechanic.start();

        asyncTimer(MechanicsLoader::task, 20L, 6L);
    }

    public static void unload() {
        CompassMechanic.unload();
    }

    private static void task(){
        PlayerUtils.getOnlinePlayers().forEach(p -> {
            ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
            ItemStack itemInOffHand = p.getInventory().getItemInOffHand();

            CompassMechanic.check(p, itemInMainHand, itemInOffHand);
            ClockMechanic.check(p, itemInMainHand, itemInOffHand);
            RecoveryCompassMechanic.check(p, itemInMainHand, itemInOffHand);
        });
    }
}
