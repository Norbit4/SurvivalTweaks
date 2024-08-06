package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;
import pl.norbit.survivaltweaks.utils.DoubleFormatter;

public class RecoveryCompassMechanic {

    private RecoveryCompassMechanic() {
        throw new IllegalStateException("Utility class");
    }

    protected static void check(Player p, ItemStack itemInMainHand, ItemStack itemInOffHand){
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
            p.sendActionBar(ChatUtils.format(Config.getNoDeathLocation()));
            return;
        }

        String x = DoubleFormatter.format(lastDeathLocation.getX());
        String y = DoubleFormatter.format(lastDeathLocation.getY());
        String z = DoubleFormatter.format(lastDeathLocation.getZ());

        String message = Config.getRecoveryCompass()
                .replace("{X}", x)
                .replace("{Y}", y)
                .replace("{Z}", z);

        p.sendActionBar(ChatUtils.format(message));
    }

}
