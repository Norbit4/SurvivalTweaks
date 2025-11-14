package pl.norbit.survivaltweaks.mechanics.listeners.elytra;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

public class ElytraMendingPrepare implements Listener {

    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ELYTRA_MENDING_DISABLED)){
            return;
        }

        AnvilInventory inv = e.getInventory();
        ItemStack baseItem = inv.getItem(0);
        ItemStack addItem = inv.getItem(1);
        ItemStack result = e.getResult();

        if(addItem != null && baseItem != null){
            Material type1 = addItem.getType();
            Material type2 = baseItem.getType();

            if(type1 == Material.ELYTRA && type2 == Material.ELYTRA){
                return;
            }
        }

        if (result != null && result.getType() == Material.ELYTRA && (baseItem == null || !baseItem.containsEnchantment(Enchantment.MENDING))
                    && result.containsEnchantment(Enchantment.MENDING)) {
                e.setResult(null);
            }
    }
}
