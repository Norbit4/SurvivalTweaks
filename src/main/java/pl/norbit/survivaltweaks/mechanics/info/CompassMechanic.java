package pl.norbit.survivaltweaks.mechanics.info;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;
import pl.norbit.survivaltweaks.utils.DoubleFormatter;
import pl.norbit.survivaltweaks.utils.PlayerUtils;

public class CompassMechanic {

    private CompassMechanic() {}

    public static void check(Player p, ItemStack itemInMainHand, ItemStack itemInOffHand){
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

        World w = loc.getWorld();
        Block b = loc.getBlock();

        NamespacedKey biomeKey = b.getBiome().getKey();

        String message = ConfigManager.getMessagesConfig().getCompass()
                .replace("{X}", x)
                .replace("{Y}", y)
                .replace("{Z}", z)
                .replace("{WORLD}", w.getName())
                .replace("{BIOME}", biomeKey.getNamespace() + ":" + biomeKey.value());

        PlayerUtils.sendActionBar(p, message);
    }
}
