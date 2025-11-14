package pl.norbit.survivaltweaks.mechanics.info;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.DoubleFormatter;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

public class RecoveryCompassMechanic {

    private RecoveryCompassMechanic() {
        throw new IllegalStateException("Utility class");
    }

    public static void check(Player p, ItemStack itemInMainHand, ItemStack itemInOffHand){
        if(MechanicsLoader.isDisabled(Mechanic.RECOVERY_COMPASS)) {
            return;
        }

        Material mainType = itemInMainHand.getType();
        Material offType = itemInOffHand.getType();

        if(!(mainType == Material.RECOVERY_COMPASS || offType == Material.RECOVERY_COMPASS)){
            return;
        }
        Location lastDeathLocation = p.getLastDeathLocation();

        if(lastDeathLocation == null){
            PlayerUtils.sendActionBar(p, Config.getNoDeathLocation());
            return;
        }

        String x = DoubleFormatter.format(lastDeathLocation.getX());
        String y = DoubleFormatter.format(lastDeathLocation.getY());
        String z = DoubleFormatter.format(lastDeathLocation.getZ());
        World w = lastDeathLocation.getWorld();

        String message = Config.getRecoveryCompass()
                .replace("{X}", x)
                .replace("{Y}", y)
                .replace("{Z}", z)
                .replace("{WORLD}", w.getName());

        PlayerUtils.sendActionBar(p, message);
    }
}
