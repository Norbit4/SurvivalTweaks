package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.view.AnvilView;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;
import pl.norbit.survivaltweaks.utils.TaskUtils;

import java.util.HashMap;
import java.util.Map;

public class AnvilTooExpensiveListener implements Listener {
    private final Map<AnvilInventory, Integer> realMaxRepairCosts = new HashMap<>();

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAnvilEvent(PrepareAnvilEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ANVIL_TOO_EXPENSIVE)){
            return;
        }

        AnvilInventory inv = e.getInventory();
        AnvilView view = e.getView();

        realMaxRepairCosts.computeIfAbsent(inv, k -> view.getMaximumRepairCost());

        int savedMax = realMaxRepairCosts.get(inv);

        if (view.getRepairCost() < savedMax) {
            view.setMaximumRepairCost(savedMax);
            realMaxRepairCosts.remove(inv);
            return;
        }

        view.setMaximumRepairCost(Integer.MAX_VALUE);
        view.setRepairCost(savedMax - 1);

        ItemStack result = e.getResult();

        if (result  != null) {
            e.setResult(result.clone());
        }

        TaskUtils.syncLater(() ->{
            HumanEntity viewer = inv.getViewers().isEmpty() ? null : inv.getViewers().getFirst();
            if (viewer instanceof Player pView) {
                pView.updateInventory();
            }
        }, 1L);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if(MechanicsLoader.isDisabled(Mechanic.ANVIL_TOO_EXPENSIVE)){
            return;
        }

        if (e.getInventory() instanceof AnvilInventory aInv) {
            realMaxRepairCosts.remove(aInv);
        }
    }
}
