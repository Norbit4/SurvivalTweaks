package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.settings.ConfigManager;

public class FurnaceNerfListener implements Listener {

    @EventHandler
    public void onBurn(FurnaceBurnEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.FURNACE_FUEL_NERF)){
            return;
        }

        ItemStack fuel = e.getFuel();
        Integer burnTime = ConfigManager.getFurnaceConfig().getBurnTime(fuel.getType());

        if (burnTime != null) {
            e.setBurnTime(burnTime);
        }else {
            if(ConfigManager.getFurnaceConfig().isFuelBlocked(fuel.getType())){
                e.setCancelled(true);
            }
        }
    }
}