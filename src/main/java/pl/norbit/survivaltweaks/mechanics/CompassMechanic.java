package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.DoubleFormatter;
import pl.norbit.survivaltweaks.utils.PlayerUtils;
import pl.norbit.survivaltweaks.utils.WorldUtils;

import java.util.List;

public class CompassMechanic {

    private CompassMechanic() {
        throw new IllegalStateException("Utility class");
    }

    protected static void load(){
        updateGameRules(MechanicsLoader.isEnabled(Mechanic.COMPASS));
    }

    protected static void unload(){
        updateGameRules(false);
    }

    private static void updateGameRules(boolean value){
        List<World> worlds = WorldUtils.getWorlds();
        worlds.forEach(w -> w.setGameRule(GameRule.REDUCED_DEBUG_INFO, value));
    }

    protected static void check(Player p, ItemStack itemInMainHand, ItemStack itemInOffHand){
        if(MechanicsLoader.isDisabled(Mechanic.COMPASS)) {
            return;
        }

        Material mainType = itemInMainHand.getType();
        Material offType = itemInOffHand.getType();

        if(!(mainType == Material.COMPASS || offType == Material.COMPASS)){
            return;
        }
        Location loc = p.getLocation();

        String x = DoubleFormatter.format(loc.getX());
        String y = DoubleFormatter.format(loc.getY());
        String z = DoubleFormatter.format(loc.getZ());

        String message = Config.getCompass()
                .replace("{X}", x)
                .replace("{Y}", y)
                .replace("{Z}", z);

        PlayerUtils.sendActionBar(p, message);
    }
}
