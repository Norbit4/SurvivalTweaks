package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

import static pl.norbit.survivaltweaks.utils.TaskUtils.asyncTimer;

public class MechanicsLoader {

    private MechanicsLoader() {
        throw new IllegalStateException("Utility class");
    }

    public static void load(boolean reload) {
        Config.load(reload);
        CompassMechanic.load();
        if(!reload) {
            SpyGlassMechanic.start();

            asyncTimer(MechanicsLoader::task, 20L, 6L);
        }
    }

    public static void unload() {
        CompassMechanic.unload();
    }

    public static boolean isDisabled(Mechanic mechanic) {
        return !isEnabled(mechanic);
    }

    public static boolean isEnabled(Mechanic mechanic) {
        return switch (mechanic) {
            case COMPASS -> Config.isCompassEnabled();
            case RECOVERY_COMPASS -> Config.isRecoveryCompassEnabled();
            case CLOCK -> Config.isClockEnabled();
            case SPYGLASS -> Config.isSpyglassEnabled();
            case SIZE -> Config.isSizeEnabled();
        };
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
