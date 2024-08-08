package pl.norbit.survivaltweaks.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class DurabilityUtils {

    private DurabilityUtils() {
        throw new IllegalStateException("This class cannot be instantiated");
    }

    public static ItemStack updateDurability(ItemStack item, int dmg){
        ItemMeta meta = item.getItemMeta();

        if (meta instanceof Damageable damageable){
            int maxDurability = item.getType().getMaxDurability();

            if(damageable.getDamage() + dmg >= maxDurability){
                return null;
            }
            damageable.setDamage((damageable.getDamage() + dmg));
        }

        item.setItemMeta(meta);
        return item;
    }
}
