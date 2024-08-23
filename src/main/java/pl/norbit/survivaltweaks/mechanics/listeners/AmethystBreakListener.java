package pl.norbit.survivaltweaks.mechanics.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.norbit.survivaltweaks.mechanics.MechanicsLoader;
import pl.norbit.survivaltweaks.mechanics.model.Mechanic;

public class AmethystBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (MechanicsLoader.isDisabled(Mechanic.AMETHYST)) {
            return;
        }

        Block b = e.getBlock();

        if (b.getType() != Material.BUDDING_AMETHYST) {
            return;
        }

        Player p = e.getPlayer();
        ItemStack tool = p.getInventory().getItemInMainHand();

        if (!tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
            return;
        }

        Location loc = b.getLocation();
        World w = b.getWorld();

        w.dropItemNaturally(loc, new ItemStack(Material.BUDDING_AMETHYST, 1));
    }
}
