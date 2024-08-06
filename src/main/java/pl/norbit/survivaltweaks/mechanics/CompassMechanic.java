package pl.norbit.survivaltweaks.mechanics;

import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.settings.Config;
import pl.norbit.survivaltweaks.utils.ChatUtils;
import pl.norbit.survivaltweaks.utils.DoubleFormatter;
import pl.norbit.survivaltweaks.utils.WorldUtils;

import java.util.List;

public class CompassMechanic {

    private CompassMechanic() {
        throw new IllegalStateException("Utility class");
    }

    protected static void load(){
        updateGameRules(true);
    }

    protected static void unload(){
        updateGameRules(false);
    }

    private static void updateGameRules(boolean value){
        List<World> worlds = WorldUtils.getWorlds();
        worlds.forEach(w -> w.setGameRule(GameRule.REDUCED_DEBUG_INFO, value));
    }

    protected static void check(Player p, ItemStack itemInMainHand, ItemStack itemInOffHand){
        Material mainType = itemInMainHand.getType();
        Material offType = itemInOffHand.getType();

        if(!(mainType == Material.COMPASS || offType == Material.COMPASS)){
            return;
        }
        String x = DoubleFormatter.format(p.getX());
        String y = DoubleFormatter.format(p.getY());
        String z = DoubleFormatter.format(p.getZ());

        String message = Config.getCompass()
                .replace("{X}", x)
                .replace("{Y}", y)
                .replace("{Z}", z);

        p.sendActionBar(ChatUtils.format(message));
    }
}
