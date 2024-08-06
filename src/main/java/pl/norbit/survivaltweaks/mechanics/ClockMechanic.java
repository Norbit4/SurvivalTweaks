package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;

public class ClockMechanic {

    private ClockMechanic() {
        throw new IllegalStateException("Utility class");
    }

    protected static void check(Player p, ItemStack itemInMainHand, ItemStack itemInOffHand){
        if(MechanicsLoader.isDisabled(Mechanic.CLOCK)) {
            return;
        }

        Material mainType = itemInMainHand.getType();
        Material offType = itemInOffHand.getType();

        if(!(mainType == Material.CLOCK || offType == Material.CLOCK)){
            return;
        }

        World w = p.getWorld();

        if(w.getEnvironment() != World.Environment.NORMAL){
            return;
        }

        long time = w.getTime();
        String formattedTime = formatMinecraftTime(time);

        String message = Config.getClock()
                .replace("{TIME}", formattedTime);

        p.sendActionBar(ChatUtils.format(message));
    }

    private static String formatMinecraftTime(long ticks) {
        // 1 tick = 1/20 sec
        // minecraft day starts at 6 AM (0 ticks)
        long hours = (ticks / 1000 + 6) % 24;
        int minutes = (int)((ticks % 1000) / 1000.0 * 60);
        return String.format("%02d:%02d", hours, minutes);
    }
}
